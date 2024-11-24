package model.expresions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.IValue;
import model.value.IntValue;
import model.value.RefValue;

public class HeapReadExpression implements IExpression{
    private IExpression expression;

    public HeapReadExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symbolTable, MyIHeap heap) throws ExpressionException, ADTException {
        IValue value = this.expression.eval(symbolTable, heap);
        if(!(value instanceof RefValue)){
            throw new ExpressionException("The expression must be an integer");
        }
        RefValue refValue = (RefValue) value;
        return heap.getValue(refValue.getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadExpression(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "readHeap(" + this.expression.toString() + ")";
    }
}
