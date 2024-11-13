package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getStates();
    PrgState getCurrentPrg() throws RepoException;
    void addPrgState(PrgState state);
    void removePrgState(PrgState state);
    void logPrgStateExec() throws RepoException;
}
