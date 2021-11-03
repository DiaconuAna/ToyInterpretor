import Controller.Controller;
import Exceptions.*;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;
import Repository.RepositoryInterface;
import Repository.Repository;
import Model.ProgramState;
import Model.ADTs.*;
import View.Command.ExitCommand;
import View.Command.RunCommand;
import View.TextMenu;

import java.io.BufferedReader;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        StatementInterface statement1 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        StatementInterface statement2 = new CompoundStatement(new VarDeclStatement("a", new IntType()),
                new CompoundStatement(new VarDeclStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"),
                                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        StatementInterface statement3 = new CompoundStatement(new VarDeclStatement("a", new BoolType()),
                new CompoundStatement(new VarDeclStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        StatementInterface statement4 = new CompoundStatement(new VarDeclStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseReadFile(new VariableExpression("varf")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );


        RepositoryInterface repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1, true);
        StackInterface<StatementInterface> executionStack1 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable1 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out1 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file1 = new Dictionary<StringValue, BufferedReader>();
        ProgramState myProgramState1 = new ProgramState(executionStack1, symbolTable1, out1, statement1, file1);
        controller1.addProgram(myProgramState1);

        RepositoryInterface repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2, true);
        StackInterface<StatementInterface> executionStack2 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable2 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out2 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file2 = new Dictionary<StringValue, BufferedReader>();
        ProgramState myProgramState2 = new ProgramState(executionStack2, symbolTable2, out2, statement2, file2);
        controller2.addProgram(myProgramState2);


        RepositoryInterface repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3, true);
        StackInterface<StatementInterface> executionStack3 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable3 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out3 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file3 = new Dictionary<StringValue, BufferedReader>();
        ProgramState myProgramState3 = new ProgramState(executionStack3, symbolTable3, out3, statement3, file3);
        controller3.addProgram(myProgramState3);

        RepositoryInterface repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4, true);
        StackInterface<StatementInterface> executionStack4 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable4 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out4 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file4 = new Dictionary<StringValue, BufferedReader>();
        ProgramState myProgramState4 = new ProgramState(executionStack4, symbolTable4, out4, statement4, file4);
        controller4.addProgram(myProgramState4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.addCommand(new RunCommand("1", statement1.toString(), controller1));
        menu.addCommand(new RunCommand("2", statement2.toString(), controller2));
        menu.addCommand(new RunCommand("3", statement3.toString(), controller3));
        menu.addCommand(new RunCommand("4", statement4.toString(), controller4));
        menu.show();
    }
}

