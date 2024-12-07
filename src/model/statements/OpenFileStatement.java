package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expresions.IExpression;
import model.state.PrgState;
import model.types.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements IStatement{
    IExpression expression;

    public OpenFileStatement(IExpression expression){
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpressionException, StatementException, FileNotFoundException {
        IValue value = this.expression.eval(state.getSymTable(), state.getHeap());
        if(!(value.getType().equals(new StringType()))){
            throw new StatementException("The value is not a string");
        }
        if(state.getFileTable().contains((StringValue)value)){
            throw new StatementException("The file is already open");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(((StringValue) value).getValue()));
            state.getFileTable().insert((StringValue) value, reader);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file does not exist");
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public String toString(){
        return "OpenFileStatement(" + expression.toString() + ")";
    }
}
