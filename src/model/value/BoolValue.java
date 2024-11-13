package model.value;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{
    // The value of the boolean
    private boolean value;

    // Constructor
    public BoolValue(boolean value) {
        this.value = value;
    }

    // Method to check if the value is equal to another value
    public boolean equals(IValue other){
        return other instanceof BoolValue && ((BoolValue)other).value == this.value;
    }

    @Override
    // Method to get the type of the value
    public IType getType() {
        return new BoolType();
    }

    // Method to get the value
    public boolean getValue(){
        return value;
    }

    @Override
    // Method to deep copy the value
    public String toString(){
        return String.valueOf(this.value);
    }
}
