package controller;

import exceptions.*;
import model.adt.MyIStack;
import model.expresions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import repository.IRepository;

import java.io.IOException;

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
//        if(!stk.isEmpty()){ // Check if the stack is not empty or the current statement is final
//            state.getTree().insert(currentStatement.toString()); // Insert the current statement in the execution tree
//        }
        state.getTree().insert(currentStatement.toString()); // Insert the current statement in the execution tree
        return currentStatement.execute(state); // Execute the current statement
    }

    @Override
    // Method to execute all the steps of the program
    public void allStep() throws StatementException, ADTException, IOException, ExpressionException, RepoException {
        PrgState program = this.repository.getCurrentPrg(); // Get the current program state
        if(this.flag){ // Check if the flag is true
            System.out.println(program); // Print the program state
            repository.logPrgStateExec();
        }
        while(!program.getExeStack().isEmpty()){ // While the execution stack is not empty
            oneStep(program); // Execute one step of the program
            if(this.flag){ // Check if the flag is true
                System.out.println(program); // Print the program state
                repository.logPrgStateExec();
            }
        }
        this.repository.removePrgState(program); // Remove the program state
    }

    @Override
    // Method to add a statement to the program
    public void add(IStatement statement){
        this.repository.addPrgState(new PrgState(statement));
    }
}
