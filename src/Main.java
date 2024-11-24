import controller.Controller;
import controller.IController;
import model.expresions.*;
import model.state.PrgState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
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

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
        PrgState prg5 = new PrgState(ex5);
        IRepository repository5 = new Repository(prg5,"log5.txt");
        Controller controller5 = new Controller(repository5, true);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new AritmeticalExpression(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))), AritmeticalOperator.ADD, new ValueExpression(new IntValue(5)))))))));
        PrgState prg6 = new PrgState(ex6);
        IRepository repository6 = new Repository(prg6,"log6.txt");
        Controller controller6 = new Controller(repository6, true);


        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new AritmeticalExpression(new HeapReadExpression(new VariableExpression("v")), AritmeticalOperator.ADD, new ValueExpression(new IntValue(5))))
                                ))));

        PrgState prg7 = new PrgState(ex7);
        IRepository repository7= new Repository(prg7,"log7.txt");
        Controller controller7 = new Controller(repository7, true);


        //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));

        PrgState prg8 = new PrgState(ex8);
        IRepository repository8 = new Repository(prg8,"log8.txt");
        Controller controller8 = new Controller(repository8, true);

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), RelationalOperator.GREATER_THAN, new ValueExpression(new IntValue(0))),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new AritmeticalExpression(new VariableExpression("v"), AritmeticalOperator.SUBTRACT, new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        PrgState prg9 = new PrgState(ex9);
        IRepository repository9 = new Repository(prg9,"log9.txt");
        Controller controller9 = new Controller(repository9, true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));

        menu.show();
    }
}