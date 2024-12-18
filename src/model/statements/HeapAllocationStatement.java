package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
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
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new HeapAllocationStatement(variable, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeVar;
        IType typeExp;
        try{
            typeVar = typeEnv.getValue(variable);
        }
        catch (KeyNotFoundException e){
            throw new StatementException("The variable " + variable + " is not defined");
        }

        try{
            typeExp = expression.typeCheck(typeEnv);
        }
        catch (ExpressionException e){
            throw new StatementException(e.getMessage());
        }
        if(!typeVar.equals(new RefType(typeExp))){
            throw new StatementException("The variable " + variable + " and the expression " + expression.toString() + " do not have the same type");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return "new(" + variable + ", " + expression.toString() + ")";
    }
}
