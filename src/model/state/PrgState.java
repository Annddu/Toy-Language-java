package model.state;
import java.io.BufferedReader;

import model.adt.*;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

// Class for the program state, used to store the program's state( symbol table, execution stack, output, file table) at a certain point in time(runtime)
public class PrgState {

    // The symbol table
    private MyIDictionary<String, IValue> symTable;
    // Getters and setters for the symbol table
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    // The execution stack
    private MyIStack<IStatement> exeStack;
    // Getters and setters for the execution stack
    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }
    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    // The output, the list of strings that the program has outputted
    private MyIList<String> output;

    // Initial state of the program, used to reset the program to its initial state
    private IStatement initialState;

    // The file table, used to store the files that the program has opened
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    //TODO: add heap interface
    private MyIHeap heap;

    // Constructor for the program state
    public PrgState(MyIDictionary<String, IValue> symTable ,
                    MyIStack<IStatement> exeStack,
                    MyIList<String> output ,
                    IStatement initialState,
                    MyIDictionary<StringValue,
                    BufferedReader> fileTable,
                    MyIHeap heap) {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.output = output;
        this.initialState = initialState.deepCopy();
        this.exeStack.push(this.initialState);
        this.fileTable = fileTable;
        this.heap = heap;
    }

    // Constructor for the program state
    public PrgState(IStatement initialState) {
        this.symTable = new MyDictionary<>();
        this.exeStack = new MyStack<>();
        this.output = new MyList<>();
        this.exeStack.push(initialState);
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap();
    }

    public MyIHeap getHeap() {
        return this.heap;
    }

    // Method to get the file table
    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    // Method to set the file table to a string
    public String fileTableToString() {
        StringBuilder text = new StringBuilder();
        text.append("FileTable:\n");
        if(this.fileTable != null) {
            for(StringValue key : this.fileTable.getKeys()) {
                text.append("   ");
                text.append(key).append("\n");
            }
        }
        return text.toString();
    }

    // Method to get the output, the list of strings that the program has outputted
    public MyIList<String> getOutput() {
        return output;
    }

    // Method to set the output, the list of strings that the program has outputted
    public void setOutput(MyIList<String> output) {
        this.output = output;
    }

    // Method to string
    public String toString(){
        return exeStack.toString() + symTable.toString() + output.toString()  + fileTableToString() + heap.toString();
    }
}
