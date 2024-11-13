package model.expresions;

import exceptions.ADTException;
import model.adt.MyIDictionary;
import model.value.IValue;

public class VariableExpression implements IExpression {
    // The name of the variable
    private String variable;

    // Constructor
    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    // Method to evaluate the expression
    public IValue eval(MyIDictionary<String, IValue> symTbl) throws ADTException {
        return symTbl.getValue(variable);
    }

    @Override
    // Method to deep copy the expression
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    // Method to String
    public String toString(){
        return variable;
    }
}
