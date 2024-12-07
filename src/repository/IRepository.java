package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepository {
    List<PrgState> getStates();
    void addPrgState(PrgState state);
    void removePrgState(PrgState state);
    void logPrgStateExec(PrgState state) throws RepoException;
    void setPrgList(List<PrgState> prgList);
}
