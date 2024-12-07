package model.statements;

import exceptions.ADTException;
import exceptions.StatementException;
import model.state.PrgState;
import model.types.*;
import model.value.*;

public class VarDeclStatement implements IStatement{
    // The variable name
    private String varName;
    // The type of the variable
    private IType type;

    // Constructor
    public VarDeclStatement(String v, IType t)
    {
        this.varName = v;
        this.type = t;
    }

    @Override
    // Method to execute the statement
    public PrgState execute(PrgState state) throws StatementException, ADTException
    {
        if(state.getSymTable().contains(varName)) // check if the variable already exists
            throw new StatementException("Variable" + this.varName + " already exists."); // throw an exception if it does

        state.getSymTable().insert(varName, type.defaultValue()); // insert the variable in the symbol table
        return null; // return the program state

//        IValue defaultValue; // create a default value
//        if (type.equals(new IntType())) // check the type of the variable
//            defaultValue = new IntValue(0); // set the default value to 0
//        else if(type.equals(new BoolType()))
//            defaultValue = new BoolValue(false); // set the default value to false
//        else if(type.equals(new StringType()))
//            defaultValue = new StringValue(""); // set the default value to ""
//        else if(type.equals(new RefType(new IntType()))){
//            RefType refType = (RefType) type;
//            defaultValue = new RefValue(0, refType.getInner());
//        }
//        else
//            throw new StatementException("Invalid type"); // throw an exception if the type is invalid
//        state.getSymTable().insert(varName, defaultValue); // insert the variable in the symbol table
//        return state; // return the program state
    }

    @Override
    // Method to deep copy the statement
    public IStatement deepCopy() {
        return new VarDeclStatement(new String(this.varName), this.type);
    }

    // Method to String
    public String toString()
    {
        return this.varName + " = " + this.type.toString();
    }
}