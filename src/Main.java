import controller.Controller;
import controller.IController;
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
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.View;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Main {
    public static void main(String[] args) {
//        IRepository repository = new Repository("file.txt");
//        IController controller = new Controller(repository, true);
//        View view = new View(controller);
//        view.run();

        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        PrgState prg1 = new PrgState(ex1);
        IRepository repository1 = new Repository(prg1,"log1.txt");
        Controller controller1 = new Controller(repository1, true);

        IStatement ex2 = new CompStatement(new VarDeclStatement("a",new IntType()),
                new CompStatement(new VarDeclStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new AritmeticalExpression(new ValueExpression(new IntValue(2)), AritmeticalOperator.ADD,new
                                AritmeticalExpression(new ValueExpression(new IntValue(3)),AritmeticalOperator.MULTIPLY,new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b",new AritmeticalExpression(new VariableExpression("a"), AritmeticalOperator.ADD,new ValueExpression(new
                                        IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        PrgState prg2 = new PrgState(ex2);
        IRepository repository2 = new Repository(prg2,"log2.txt");
        Controller controller2 = new Controller(repository2, true);

        IStatement ex3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3))),new VariableExpression("a")), new PrintStatement(new
                                        VariableExpression("v"))))));
        PrgState prg3 = new PrgState(ex3);
        IRepository repository3 = new Repository(prg3,"log3.txt");
        Controller controller3 = new Controller(repository3, true);

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
        PrgState prg4 = new PrgState(ex4);
        IRepository repository4 = new Repository(prg4,"log4.txt");
        Controller controller4 = new Controller(repository4, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));

        menu.show();
    }
}