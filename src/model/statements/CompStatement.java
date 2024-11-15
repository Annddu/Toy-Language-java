package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.state.PrgState;

public class CompStatement implements IStatement{
    // The two statements to be executed
    private IStatement statement1;
    private IStatement statement2;

    // Constructor
    public CompStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    // Method to execute the statement
    public PrgState execute(PrgState prgState) throws StatementException, ADTException {
        prgState.getExeStack().push(statement2); // push the second statement on the execution stack
        prgState.getExeStack().push(statement1); // push the first statement on the execution stack

        return prgState; // return the program state
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy() {
        return new CompStatement(this.statement1, this.statement2);
    }

    @Override
    // Method to String
    public String toString() {
        return statement1.toString() + ";" + statement2.toString();
    }

}
