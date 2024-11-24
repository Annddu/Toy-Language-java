package model.expresions;


import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicalExpression implements IExpression {
    private IExpression left; // The left and right expressions
    private IExpression right;
    private LogicalOperator operator; // The operator

    // Constructor for LogicalExpression
    public LogicalExpression(IExpression left, LogicalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    // Method to evaluate the expression
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = left.eval(symTbl, heap); // recursively evaluate the left and right expressions
        IValue evaluatedExpressionRight = right.eval(symTbl, heap);
        if (!evaluatedExpressionLeft.getType().equals(new BoolType())) { //check if the left expression is a boolean
            throw new ExpressionException("Left expression is not of type BoolType"); // throw an exception if it is not
        }
        if (!evaluatedExpressionRight.getType().equals(new BoolType())) { //check if the right expression is a boolean
            throw new ExpressionException("Right expression is not of type BoolType"); // throw an exception if it is not
        }
        switch (operator) { // evaluate the expression based on the operator
            case AND:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() && // cast the values to BoolValue
                        ((BoolValue) evaluatedExpressionRight).getValue());
            case OR:
                return new BoolValue(((BoolValue) evaluatedExpressionLeft).getValue() || // cast the values to BoolValue
                        ((BoolValue) evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator"); // throw an exception if the operator is unknown
        }
    }

    @Override
    // Method to deep copy the expression
    public IExpression deepCopy() {
        // recursively deep copy the left and right expressions
        return new LogicalExpression(left.deepCopy(), operator, right.deepCopy());
    }

    @Override
    // Method to String
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
