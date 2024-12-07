package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.expresions.IExpression;
import model.state.PrgState;
import model.value.IValue;
import model.value.IntValue;
import model.value.RefValue;

public class HeapWriteStatement implements IStatement{
    private String variable;
    private IExpression expression;

    public HeapWriteStatement(String variable, IExpression expression){
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException {
        if(!state.getSymTable().contains(variable)){
            throw new RuntimeException("Variable not found in the symbol table");
        }
        if(!(state.getSymTable().getValue(variable) instanceof RefValue)){
            throw new RuntimeException("The variable and the expression are not of the same type");
        }

        RefValue referenceValue  = (RefValue) state.getSymTable().getValue(variable);
        IValue evaluated = expression.eval(state.getSymTable(), state.getHeap());
        if(!evaluated.getType().equals(referenceValue.getLocationType())){
            throw new RuntimeException("The variable and the expression are not of the same type");
        }
        state.getHeap().update(referenceValue.getAddress(), evaluated);
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new HeapWriteStatement(variable, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "writeHeap(" + variable + ", " + expression.toString() + ")";
    }
}
