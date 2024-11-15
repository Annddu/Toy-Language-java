package model.expresions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.types.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression{

    private IExpression left;
    private IExpression right;
    private RelationalOperator operator;

    public RelationalExpression(IExpression left, RelationalOperator operator, IExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTbl) throws ADTException, ExpressionException {
        IValue evaluatedExpressionLeft = left.eval(symTbl);
        IValue evaluatedExpressionRight = right.eval(symTbl);
        if (evaluatedExpressionLeft.getType().equals(new IntType())) {
            throw new ExpressionException("The expressions are of type int");
        }
        if(evaluatedExpressionRight.getType().equals(new IntType())) {
            throw new ExpressionException("The expressions are of type int");
        }
        switch (operator) {
            case LESS_THAN:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() < ((IntValue) evaluatedExpressionRight).getValue());
            case LESS_THAN_OR_EQUAL:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() <= ((IntValue) evaluatedExpressionRight).getValue());
            case EQUAL:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() == ((IntValue) evaluatedExpressionRight).getValue());
            case NOT_EQUAL:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() != ((IntValue) evaluatedExpressionRight).getValue());
            case GREATER_THAN:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() > ((IntValue) evaluatedExpressionRight).getValue());
            case GREATER_THAN_OR_EQUAL:
                return new BoolValue(((IntValue) evaluatedExpressionLeft).getValue() >= ((IntValue) evaluatedExpressionRight).getValue());
            default:
                throw new ExpressionException("Unknown operator");
        }
    }

    @Override
    public String toString() {
        String sign = "";
        if (operator == RelationalOperator.LESS_THAN) {
            return left.toString() + " < " + right.toString();
        }
        if (operator == RelationalOperator.LESS_THAN_OR_EQUAL) {
            return left.toString() + " <= " + right.toString();
        }
        if (operator == RelationalOperator.EQUAL) {
            return left.toString() + " == " + right.toString();
        }
        if (operator == RelationalOperator.NOT_EQUAL) {
            return left.toString() + " != " + right.toString();
        }
        if (operator == RelationalOperator.GREATER_THAN) {
            return left.toString() + " > " + right.toString();
        }
        if (operator == RelationalOperator.GREATER_THAN_OR_EQUAL) {
            return left.toString() + " >= " + right.toString();
        }

        return left.toString() + " " + sign + " " + right.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), operator, right.deepCopy());
    }
}
