package controller;

import exceptions.*;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.expresions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

// Class for the controller, used to control the execution of the program
public class Controller implements IController {
    IRepository repository; // The repository
    boolean flag; // The flag used to determine if the program is printing the output

    // Constructor for the controller
    public Controller(IRepository repo, boolean flag) {
        this.repository = repo;
        this.flag = flag;
    }



    @Override
    // Method to execute one step of the program
    public PrgState oneStep(PrgState state) throws ADTException, StatementException, IOException, ExpressionException {
        MyIStack<IStatement> stk = state.getExeStack(); // Get the execution stack
        if (stk.isEmpty()) { // Check if the stack is empty
            throw new EmptyStackException("Stack is empty"); // Throw an exception if it is
        }
        IStatement currentStatement = stk.pop(); // Get the current statement

        return currentStatement.execute(state); // Execute the current statement
    }

    @Override
    // Method to execute all the steps of the program
    public void allStep() throws StatementException, ADTException, IOException, ExpressionException, RepoException {
        PrgState program = this.repository.getCurrentPrg(); // Get the current program state

        while(!program.getExeStack().isEmpty()){ // While the execution stack is not empty
            oneStep(program); // Execute one step of the program
            if(this.flag){ // Check if the flag is true
                System.out.println(program); // Print the program state
                repository.logPrgStateExec();
            }
            program.getHeap().setContent(unsafeGarbageCollector(
                    getAddrFromSymTable(program.getSymTable().getContent().values()),
                    program.getHeap().getContent()
            ));
            }
        this.repository.removePrgState(program); // Remove the program state
    }

    @Override
    // Method to add a statement to the program
    public void add(IStatement statement){
        this.repository.addPrgState(new PrgState(statement));
    }

    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap){
        return heap.getMap().entrySet().stream().
                filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
}
