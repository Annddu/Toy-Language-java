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
            throw new StatementException("The types do not match");
        }

        return typeEnv;
    }

    @Override
    public String toString(){
        return "writeHeap(" + variable + ", " + expression.toString() + ")";
    }
}
