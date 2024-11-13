package model.adt;


import java.util.ArrayList;
import java.util.List;

//stores the results of the program and the output
public class MyList<T> implements MyIList<T>{
    // The list is implemented using an ArrayList
    private List<T> list;

    // Constructor for MyList
    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    // Method to add a new element to the list
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    // Method to get the list
    public List<T> getAll() {
        return this.list;
    }


    @Override
    // Method to String
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(T element: this.list){
            str.append("   ");
            str.append(element).append("\n");
        }
//        if(str.length() > 0){
//            str.deleteCharAt(str.length()-1);
//        }
        return "Out:\n" + str;
    }
}
