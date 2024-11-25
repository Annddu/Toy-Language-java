package controller;

import exceptions.*;
import exceptions.EmptyStackException;
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
import java.util.*;
import java.util.stream.Collectors;

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
        // it keeps only the addresses that are in symTableAddr si returneaza un map nou
        return heap.getMap().entrySet().stream().
                filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap){
        //tine cont de nested ref, merge in adancime in heap si verifica daca adresa e in symTableAddr si o pastreaza
        List<Integer> adresses = new ArrayList<>(symTableAddr); // list of adresses
        boolean found = true; // flag to determine if an adress was found
        while(found){ // while an adress was found
            found = false; // set the flag to false
            List<IValue> referenced = new ArrayList<>(); // list of referenced values
            for(Integer adress : adresses){ // for each adress
                IValue value = heap.getValue(adress); // get the value
                if(value != null){ // if the value is not null
                    referenced.add(value); // add the value to the list of referenced values
                }
            }
            List<Integer> newAdresses = getAddrFromSymTable(referenced); // get the new adresses
            for(Integer adress : newAdresses){ // for each new adress
                if(!adresses.contains(adress)){ // if the adress is not in the list of adresses
                    adresses.add(adress); // add the adress to the list of adresses
                    found = true;
                }
            }
        }

        Map<Integer, IValue> result = new HashMap<>(); // map of adresses and values
        for(Map.Entry<Integer, IValue> entry : heap.getMap().entrySet()){ // for each entry in the heap
            if(adresses.contains(entry.getKey())){ // if the adress is in the list of adresses
                result.put(entry.getKey(), entry.getValue()); // add the entry to the result
            }
        }
        return result;
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
}
