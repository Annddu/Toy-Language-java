package model.statements;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.types.IType;

import java.io.IOException;

public interface IStatement {
    PrgState execute(PrgState prgState) throws StatementException, ADTException, ExpressionException, IOException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException;

}
