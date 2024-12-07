package controller;

import exceptions.*;
import model.state.PrgState;
import model.statements.IStatement;

import java.io.IOException;

public interface IController {
    //PrgState oneStep(PrgState state) throws ADTException, StatementException, IOException, ExpressionException;
    void allStep() throws StatementException, ADTException, IOException, ExpressionException, RepoException;
   // void add(IStatement state);

}
