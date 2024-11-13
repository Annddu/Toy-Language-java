package model.types;

import model.value.IValue;
import model.value.StringValue;

public class StringType implements IType{
    // Constructor
    public StringType(){

    }

    // Method to String
    public String toString(){
        return "String";
    }

    @Override
    // Method to check if the type is string
    public boolean equals(IType obj) {
        return obj instanceof StringType;
    }

    @Override
    // Default value
    public IValue defaultValue() {
        return new StringValue("");
    }
}
