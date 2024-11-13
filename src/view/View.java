package view;

import controller.IController;
import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.RepoException;
import exceptions.StatementException;
import model.expresions.AritmeticalExpression;
import model.expresions.AritmeticalOperator;
import model.expresions.ValueExpression;
import model.expresions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.IOException;

public class View {
    IController controller;

    public View(IController controller) {
        this.controller = controller;
    }

    public void run(){
        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));


        IStatement ex2 = new CompStatement(new VarDeclStatement("a",new IntType()),
                new CompStatement(new VarDeclStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new AritmeticalExpression(new ValueExpression(new IntValue(2)), AritmeticalOperator.ADD,new
                                AritmeticalExpression(new ValueExpression(new IntValue(3)),AritmeticalOperator.MULTIPLY,new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b",new AritmeticalExpression(new VariableExpression("a"), AritmeticalOperator.ADD,new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));


        IStatement ex3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))),new VariableExpression("a")), new PrintStatement(new
                                        VariableExpression("v"))))));

        IStatement ex4 = new CompStatement(new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf", new ValueExpression(
                        new StringValue("test.in"))),
                        new CompStatement(new OpenFileStatement(new VariableExpression("varf")),
                                new CompStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(new ReadFileStatement(
                                                new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFileStatement(
                                                                new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(
                                                                                new VariableExpression("varc")),
                                                                        new CloseFileStatement(
                                                                                new VariableExpression("varf"))))))))));

        this.controller.add(ex4);
        try{
            this.controller.allStep();
        }
        catch (ADTException | ExpressionException | StatementException | IOException | RepoException e){
            System.out.println(e.getMessage());
        }

    }

}
