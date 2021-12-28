import Controller.Controller;
import Exceptions.*;
import Model.Expression.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Value.*;
import Repository.RepositoryInterface;
import Repository.Repository;
import Model.ProgramState;
import Model.ADTs.*;
import View.Command.ExitCommand;
import View.Command.RunCommand;
import View.TextMenu;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.sql.Ref;
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

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)

        StatementInterface statement5 = new CompoundStatement(new VarDeclStatement("v", new ReferenceType((new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VarDeclStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                         new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                             new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

        StatementInterface statement6 = new CompoundStatement(new VarDeclStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VarDeclStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                        new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                     new PrintStatement((new ArithmeticExpression('+', new HeapReadingExpression(
                                             new HeapReadingExpression(new VariableExpression("a"))),
                                             new ValueExpression(new IntValue(5))))))))));

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        StatementInterface statement7 = new CompoundStatement(new VarDeclStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                            new CompoundStatement(new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression
                                        ('+',new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        StatementInterface statement8 = new CompoundStatement(new VarDeclStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VarDeclStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        StatementInterface statement9 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v",
                                        new ArithmeticExpression('-',new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        //int v; Ref int a; v=10;new(a,22);
        //fork(wH(a,30);v=32;print(v);print(rH(a)));
        //print(v);print(rH(a))
        StatementInterface statement10 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new VarDeclStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));

        RepositoryInterface repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1, true);
        StackInterface<StatementInterface> executionStack1 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable1 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out1 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file1 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap1 = new MyHeap<ValueInterface>();
        ProgramState myProgramState1 = new ProgramState(executionStack1, symbolTable1, out1, statement1, file1, heap1);
        controller1.addProgram(myProgramState1);

        RepositoryInterface repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2, true);
        StackInterface<StatementInterface> executionStack2 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable2 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out2 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file2 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap2 = new MyHeap<ValueInterface>();
        ProgramState myProgramState2 = new ProgramState(executionStack2, symbolTable2, out2, statement2, file2, heap2);
        controller2.addProgram(myProgramState2);


        RepositoryInterface repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3, true);
        StackInterface<StatementInterface> executionStack3 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable3 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out3 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file3 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap3 = new MyHeap<ValueInterface>();
        ProgramState myProgramState3 = new ProgramState(executionStack3, symbolTable3, out3, statement3, file3, heap3);
        controller3.addProgram(myProgramState3);

        RepositoryInterface repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4, true);
        StackInterface<StatementInterface> executionStack4 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable4 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out4 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file4 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap4 = new MyHeap<ValueInterface>();
        ProgramState myProgramState4 = new ProgramState(executionStack4, symbolTable4, out4, statement4, file4, heap4);
        controller4.addProgram(myProgramState4);

        RepositoryInterface repository5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repository5, true);
        StackInterface<StatementInterface> executionStack5 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable5 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out5 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file5 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap5 = new MyHeap<ValueInterface>();
        ProgramState myProgramState5 = new ProgramState(executionStack5, symbolTable5, out5, statement5, file5, heap5);
        controller5.addProgram(myProgramState5);

        RepositoryInterface repository6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repository6, true);
        StackInterface<StatementInterface> executionStack6 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable6 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out6 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file6 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap6 = new MyHeap<ValueInterface>();
        ProgramState myProgramState6 = new ProgramState(executionStack6, symbolTable6, out6, statement6, file6, heap6);
        controller6.addProgram(myProgramState6);

        RepositoryInterface repository7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repository7, true);
        StackInterface<StatementInterface> executionStack7 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable7 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out7 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file7 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap7 = new MyHeap<ValueInterface>();
        ProgramState myProgramState7 = new ProgramState(executionStack7, symbolTable7, out7, statement7, file7, heap7);
        controller7.addProgram(myProgramState7);

        RepositoryInterface repository8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repository8, true);
        StackInterface<StatementInterface> executionStack8 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable8 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out8 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file8 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap8 = new MyHeap<ValueInterface>();
        ProgramState myProgramState8 = new ProgramState(executionStack8, symbolTable8, out8, statement8, file8, heap8);
        controller8.addProgram(myProgramState8);

        RepositoryInterface repository9 = new Repository("log9.txt");
        Controller controller9 = new Controller(repository9, true);
        StackInterface<StatementInterface> executionStack9 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable9 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out9 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file9 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap9 = new MyHeap<ValueInterface>();
        ProgramState myProgramState9 = new ProgramState(executionStack9, symbolTable9, out9, statement9, file9, heap9);
        controller9.addProgram(myProgramState9);

        RepositoryInterface repository10 = new Repository("log10.txt");
        Controller controller10 = new Controller(repository10, true);
        StackInterface<StatementInterface> executionStack10 = new MyStack<StatementInterface>();
        DictInterface<String, ValueInterface> symbolTable10 = new Dictionary<String, ValueInterface>();
        ListInterface<ValueInterface> out10 = new MyList<ValueInterface>();
        DictInterface<StringValue, BufferedReader> file10 = new Dictionary<StringValue, BufferedReader>();
        HeapInterface<ValueInterface> heap10 = new MyHeap<ValueInterface>();
        ProgramState myProgramState10 = new ProgramState(executionStack10, symbolTable10, out10, statement10, file10, heap10);
        controller10.addProgram(myProgramState10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit."));
        menu.addCommand(new RunCommand("1", statement1.toString(), controller1));
        menu.addCommand(new RunCommand("2", statement2.toString(), controller2));
        menu.addCommand(new RunCommand("3", statement3.toString(), controller3));
        menu.addCommand(new RunCommand("4", statement4.toString(), controller4));
        menu.addCommand(new RunCommand("5", statement5.toString(), controller5));
        menu.addCommand(new RunCommand("6", statement6.toString(), controller6));
        menu.addCommand(new RunCommand("7", statement7.toString(), controller7));
        menu.addCommand(new RunCommand("8", statement8.toString(), controller8));
        menu.addCommand(new RunCommand("9", statement9.toString(), controller9));
        menu.addCommand(new RunCommand("10", statement10.toString(), controller10));






        menu.show();
    }
}

