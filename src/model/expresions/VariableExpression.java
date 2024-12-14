package model.expresions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
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
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException {
        return symTbl.getValue(variable);
    }

    @Override
    // Method to deep copy the expression
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        try {
            return typeEnv.getValue(variable);
        } catch (ADTException e) {
            throw new ExpressionException("The variable " + variable + " is not defined");
        }
    }

    // Method to String
    public String toString(){
        return variable;
    }
}
