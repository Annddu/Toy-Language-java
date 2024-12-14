package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.adt.MyStack;
import model.state.PrgState;
import model.types.IType;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) {
        MyIStack<IStatement> newExecutionStack = new MyStack<>();
        return new PrgState(prgState.getSymTable().deepCopy(),
                newExecutionStack,
                prgState.getOutput(),
                this.statement,
                prgState.getFileTable(),
                prgState.getHeap());
    }

    @Override
    public IStatement deepCopy(){
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork(" + this.statement.toString() + ")";
    }
}
