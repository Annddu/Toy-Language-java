package model.expresions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IntType;
import model.value.IValue;
import model.value.IntValue;

public class AritmeticalExpression implements IExpression {
    // The left and right expressions
    private IExpression left;
    private IExpression right;
    // The operator
    private AritmeticalOperator operator;

    // Constructor for AritmeticalExpression
    public AritmeticalExpression(IExpression left, AritmeticalOperator operator, IExpression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    // Method to evaluate the expression
    public IValue eval(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ADTException, ExpressionException {
        IValue value1 = left.eval(symTbl, heap);   // recursively evaluate the left and right expressions
        IValue value2 = right.eval(symTbl, heap);
        if (!value1.getType().equals(new IntType())) { // check if the first operand is an integer
            throw new ExpressionException("First value is not int"); // throw an exception if it is not
        }
        if (!value2.getType().equals(new IntType())) { // check if the second operand is an integer
            throw new ExpressionException("Second value is not int"); // throw an exception if it is not
        }

        IntValue int1 = (IntValue) value1; // cast the values to IntValue
        IntValue int2 = (IntValue) value2; // cast the values to IntValue

        switch (operator) { // evaluate the expression based on the operator
            case ADD:
                return new IntValue(int1.getValue() + int2.getValue());
            case SUBTRACT:
                return new IntValue(int1.getValue() - int2.getValue());
            case MULTIPLY:
                return new IntValue(int1.getValue() * int2.getValue());
            case DIVIDE:
            {
                if(int2.getValue() == 0) // check if the second operand is 0
                    throw new ExpressionException("Divide by zero");
                return new IntValue(int1.getValue() / int2.getValue());
            }
            default: // throw an exception if the operator is unknown
                throw new ExpressionException("Unknown operator");

        }

    }

    @Override
    // Method to deep copy the expression
    public IExpression deepCopy() {
        // recursively deep copy the left and right expressions
        return new AritmeticalExpression(this.left.deepCopy(),  this.operator,  this.right).deepCopy();
    }

    @Override
    // Method to String
    public String toString() {
        if(operator == AritmeticalOperator.ADD)
            return left.toString() + "+" + right.toString();
        if(operator == AritmeticalOperator.SUBTRACT)
            return left.toString() + "-" + right.toString();
        if(operator == AritmeticalOperator.MULTIPLY)
            return left.toString() + "*" + right.toString();
        if(operator == AritmeticalOperator.DIVIDE)
            return left.toString() + "/" + right.toString();
        return null;
    }
}
