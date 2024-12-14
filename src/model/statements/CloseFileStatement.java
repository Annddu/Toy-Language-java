package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expresions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.IOException;

public class CloseFileStatement implements IStatement{

    private IExpression expression;

    public CloseFileStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException {
        IValue value = this.expression.eval(prgState.getSymTable(), prgState.getHeap());
        if(!(value.getType().equals(new StringType()))){
            throw new StatementException("The value is not a string");
        }
        if(!(prgState.getFileTable().contains((StringValue)value))){
            throw new StatementException("The file is already closed");
        }

        prgState.getFileTable().getValue((StringValue)value).close();
        prgState.getFileTable().remove((StringValue)value);

        return null;
    }

    @Override
    public String toString(){
        return "CloseFileStatement(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try{
            if (!expression.typeCheck(typeEnv).equals(new StringType())) {
                throw new StatementException("The expression must be a string");
            }
        }
        catch (ExpressionException e){
            throw new StatementException(e.getMessage());
        }
        return typeEnv;
    }
}
