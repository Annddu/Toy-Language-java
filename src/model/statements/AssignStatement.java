package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expresions.IExpression;
import model.expresions.ValueExpression;
import model.state.PrgState;
import model.types.IType;
import model.value.IValue;

public class AssignStatement implements IStatement{
    // The name of the variable
    private String variableName;
    // The expression to be assigned
    private IExpression expression;

    // Constructor
    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    // Method to execute the statement
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException {
        if (!prgState.getSymTable().contains(this.variableName)) { // check if the variable is in the symbol table
            throw new StatementException("Variable was not found");
        }
        IValue value = prgState.getSymTable().getValue(this.variableName); // get the value of the variable
        IValue evalValue = this.expression.eval(prgState.getSymTable(), prgState.getHeap()); // evaluate the expression
        if (!value.getType().equals(evalValue.getType())){ // check if the value type matches the expression type
            throw new StatementException("Value type mismatch");
        }
        prgState.getSymTable().insert(this.variableName, evalValue); // insert the new value in the symbol table
        return null; // return the program state
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy() {
        return new AssignStatement( new String(this.variableName),  this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeVar;
        IType typeExp;
        try{
            typeVar = typeEnv.getValue(this.variableName);
        }
        catch (ADTException e){
            throw new StatementException("The variable " + this.variableName + " is not defined");
        }

        try{
            typeExp = expression.typeCheck(typeEnv);
        }
        catch (ExpressionException e){
            throw new StatementException("The expression " + this.expression.toString() + " is not defined");
        }
        if(!typeVar.equals(typeExp)){
            throw new StatementException("The variable " + this.variableName + " and the expression " + this.expression.toString() + " do not have the same type");
        }
        return typeEnv;
    }

    @Override
    // Method to String
    public String toString(){
        return this.variableName + " = " + this.expression.toString();
    }


}