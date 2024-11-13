package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.state.PrgState;
import model.expresions.IExpression;
import model.value.IValue;

public class PrintStatement implements IStatement{
    // The expression to be printed
    private IExpression expression;

    // Constructor
    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    // Method to execute the statement
    public PrgState execute(PrgState prgState) throws ADTException, ExpressionException {
        IValue result = this.expression.eval(prgState.getSymTable()); // evaluate the expression
        prgState.getOutput().add(result.toString()); // add the result to the output
        return prgState; // return the program state
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy() {
        return new PrintStatement(this.expression);
    }

    // Method to String
    public String toString(){
        return "print(" + expression.toString() + ")";
    }
}
