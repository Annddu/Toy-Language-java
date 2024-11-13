package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Class for the repository, used to store the program states, evry line of code that is executed is stored in the repository
public class Repository implements IRepository {
    private List<PrgState> prgStateList; // The list of program states
    private String filename; // The name of the file where the log is stored
    private int currentIndex; // The index of the current program state

    // Constructor for the repository
    public Repository(String filename) {
        this.filename = filename;
        this.prgStateList = new ArrayList<PrgState>();
        this.currentIndex = 0;
    }

    public Repository(PrgState state, String filename) {
        this.filename = filename;
        this.prgStateList = new ArrayList<PrgState>();
        this.addPrgState(state);
        this.currentIndex = 0;
    }

    @Override
    // Method to get the list of program states
    public List<PrgState> getStates() {
        return this.prgStateList;
    }

    @Override
    // Method to get the current program state
    public PrgState getCurrentPrg() throws RepoException {
        if (this.prgStateList.isEmpty()) { // check if the list is empty
            throw new RepoException("No loaded program"); // throw an exception if it is
        }
        return this.prgStateList.get(this.currentIndex); // return the current program state
    }

    @Override
    // Method to set the current program state
    public void addPrgState(PrgState state) {
        this.prgStateList.add(state);
    }

    @Override
    // Method to remove a program state
    public void removePrgState(PrgState state) {
        this.prgStateList.remove(state);
    }

    @Override
    //Method to print the program state to the log file
    public void logPrgStateExec() throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true)));
            logFile.println(this.getCurrentPrg().toString());
            logFile.flush();
            logFile.close();
        } catch(IOException err) {
            throw new RepoException("File not exists");
        }
    }
}
