package model.expresions;


import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.value.IValue;

public class ValueExpression implements IExpression {
    // The value of the expression
    private IValue value;

    // Constructor
    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    // Method to evaluate the expression
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) {
        return value;
    }

    @Override
    // Method to deep copy the expression
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        return value.getType();
    }

    // Method to String
    public String toString(){
        return value.toString();
    }
}
