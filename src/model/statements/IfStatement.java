package model.statements;


import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expresions.IExpression;
import model.expresions.VariableExpression;
import model.state.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStatement implements IStatement {
    // The then statement
    private IStatement statementThan;
    // The else statement
    private IStatement statementElse;
    // The expression
    private IExpression expression;

    // Constructor
    public IfStatement(IStatement statementThan, IStatement statementElse, IExpression expression) {
        this.statementThan = statementThan;
        this.statementElse = statementElse;
        this.expression = expression;
    }

    //@Override
    // Method to execute the statement
    public PrgState execute(PrgState state) throws StatementException, ADTException, ExpressionException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap()); // evaluate the expression
        if(!value.getType().equals(new BoolType())){ // check if the value is a boolean
            throw new StatementException("Expression is not boolean"); // throw an exception if it is not
        }
        if(((BoolValue) value).getValue()){ // check if the value is true
            state.getExeStack().push(statementThan); // push the then statement on the execution stack
        }
        else {
            state.getExeStack().push(statementElse); // push the else statement on the execution stack
        }

        return null; // return the program state
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy() {
        // recursively deep copy the then and else statements
        return new IfStatement(this.statementThan.deepCopy(),  this.statementElse.deepCopy(),  this.expression.deepCopy());
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
            throw new StatementException("The expression must be a boolean");
        }
        statementThan.typeCheck(typeEnv.deepCopy());
        statementElse.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    // Method to String
    public String toString() {
        return "if(" + expression + "){" + statementThan + "}else{" + statementElse + "}";
    }

}
