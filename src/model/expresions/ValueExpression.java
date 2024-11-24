package model.expresions;


import model.adt.MyIDictionary;
import model.adt.MyIHeap;
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

    // Method to String
    public String toString(){
        return value.toString();
    }
}
