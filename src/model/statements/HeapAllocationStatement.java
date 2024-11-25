package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expresions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement{
    private IExpression expression;
    private String variable;

    public HeapAllocationStatement(String variable, IExpression expression){
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException{
        if(!state.getSymTable().contains(variable)){
            throw new StatementException("Variable not found in the symbol table");
        }

        if(state.getSymTable().getValue(variable) instanceof RefType){
            throw new StatementException("Variable must be of RefType");
        }

        IValue value = expression.eval(state.getSymTable(), state.getHeap());

        IType locationType = ((RefValue)state.getSymTable().getValue(variable)).getLocationType();

        if(!locationType.equals(value.getType())){
            throw new StatementException("Types do not match");
        }
        int adress = state.getHeap().add(value);
        System.out.println(adress);
        state.getSymTable().insert(variable, new RefValue(adress, value.getType()));
        return state;
    }

    @Override
    public IStatement deepCopy(){
        return new HeapAllocationStatement(variable, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + variable + ", " + expression.toString() + ")";
    }
}
