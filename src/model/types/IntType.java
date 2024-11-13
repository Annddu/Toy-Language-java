package model.types;

import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType{
    @Override
    //Default value
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    //Method to check if the type is integer
    public boolean equals(IType obj) {
        return obj instanceof IntType;
    }

    //Method to String
    public String toString(){
        return "int";
    }
}
