package model.statements;

import model.expresions.IExpression;
import model.state.PrgState;

public class NopStatement implements IStatement {

    @Override
    // Method to execute the statement
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    // Method to String
    public String toString() {
        return "NopStatement";
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy(){
        return new NopStatement();
    }
}
