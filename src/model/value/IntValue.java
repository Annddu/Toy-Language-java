package model.value;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue{
    // The value of the integer
    private int value;

    // Constructor
    public IntValue(int value) {
        this.value = value;
    }

    @Override
    // Method to get the type of the value
    public IType getType() {
        return new IntType();
    }

    // Method to check if the value is equal to another value
    public boolean equals(IValue other) {
        return other instanceof IntValue && ((IntValue) other).value == this.value;
    }

    // Method to get the value
    public int getValue(){
        return value;
    }

    @Override
    // Method to deep copy the value
    public String toString(){
        return Integer.toString(value);
    }
}
