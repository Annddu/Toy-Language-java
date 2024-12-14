package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expresions.IExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

import java.io.IOException;

public class WhileStatement implements IStatement {
    // The expression to be evaluated
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public String toString(){
        return "while(" + this.expression.toString() + "){" + this.statement.toString() + "}";
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        IValue value = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        if(!value.getType().equals(new BoolType())){
            throw new StatementException("The expression while must be of type bool");
        }
        BoolValue boolValue = (BoolValue) value;
        if(boolValue.getValue()){
            prgState.getExeStack().push(this);
            prgState.getExeStack().push(this.statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typeExp;
        try{
            typeExp = expression.typeCheck(typeEnv);
        }
        catch (ExpressionException e){
            throw new StatementException(e.getMessage());
        }
        if(!typeExp.equals(new BoolType())){
            throw new StatementException("The expression while must be of type bool");
        }
        statement.typeCheck(typeEnv);
        return typeEnv;
    }

}
