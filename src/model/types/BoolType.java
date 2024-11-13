package model.types;

import model.value.BoolValue;
import model.value.IValue;

public class BoolType implements IType{
    @Override
    //Default value
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    //Method to check if the type is boolean
    public boolean equals(IType obj) {
        return obj instanceof BoolType;
    }

    //Method to String
    public String toString() {
        return "bool";
    }
}
