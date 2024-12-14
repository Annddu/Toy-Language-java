package model.adt;

import exceptions.EmptyStackException;

import java.util.Stack;

// Stack is used for the exution stack
public class MyStack<T> implements MyIStack<T>{
    // The stack is implemented using a Stack
    private Stack<T> stack;

    // Constructor for MyStack
    public MyStack()
    {
        this.stack = new Stack<>();
    }

    // Method to get the stack
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    // Method to add a new element to the stack
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    // Method to get the top element of the stack
    public T pop() throws EmptyStackException {
        if(this.stack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        return this.stack.pop();
    }

    @Override
    // Method to get the size of the stack
    public int size() {
        return this.stack.size();
    }

    @Override
    // Method to verify if the stack is empty
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public T peek() throws EmptyStackException {
        if(this.stack.isEmpty())
            throw new EmptyStackException("Stack is empty");
        return this.stack.peek();
    }

    @Override
    // Method to String
    public String toString(){
        StringBuilder str = new StringBuilder();
//        for(T element: this.stack){
//            str.append(element).append("\n");
//        }
        for(int i = this.stack.size() - 1; i >=0; i--){
            str.append("   ");
            str.append(this.stack.get(i)).append("\n");
        }
//        if(str.length() != 0){
//            str.deleteCharAt(str.length()-1);
//        }
        return "ExeStack:\n" + str;
    }
}
