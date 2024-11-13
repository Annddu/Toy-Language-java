package model.value;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    // The value of the string
    private String value;

    // Constructor
    public StringValue(String val){ value = val; }

    @Override
    // Method to get the type of the value
    public IType getType() {
        return new StringType();
    }

    // Method to get the value
    public String getValue() {
        return value;
    }

    // Method to set the value
    public void setValue(String value) {
        this.value = value;
    }

    // Method to check if the value is equal to another value
    public boolean equals(IValue val){
        return val.getType().equals(new StringType()) && ((StringValue) val).getValue().equals(this.value) ;
    }

    // Method to deep copy the value
    public String toString(){
        return value;
    }
}
