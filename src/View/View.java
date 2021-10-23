package View;

import Controller.Controller;
import Exceptions.*;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.ValueInterface;
import Repository.RepositoryInterface;
import Repository.Repository;
import Model.ProgramState;
import Model.ADTs.*;

import java.util.Scanner;


public class View {


    public static void menu(){
        System.out.println("0. Exit\n");
        System.out.println("1. int v; v=2;Print(v)\n");
        System.out.println("2. int a;int b; a=2+3*5;b=a+1;Print(b)\n");
        System.out.println("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)\n");

    }

    public static void main(String[] args) throws ControllerException, StatementException, ADTsExceptions, RepositoryException, ExpressionException {

        StatementInterface statement1 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        StatementInterface statement2 = new CompoundStatement(new VarDeclStatement("a",new IntType()),
                new CompoundStatement(new VarDeclStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),
                        new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        StatementInterface statement3 = new CompoundStatement(new VarDeclStatement("a",new BoolType()),
                new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        boolean over = false;

        while(!over){
            menu();

            int choice;
            Scanner scan = new Scanner(System.in);
            System.out.println("Your choice: ");
            choice = scan.nextInt();

            try {
                switch (choice) {
                    case 0:
                        over = true;
                        System.out.println("Goodbye \n");
                        break;
                    case 1:
                        RepositoryInterface repository1 = new Repository();
                        Controller controller1 = new Controller(repository1, true);
                        StackInterface<StatementInterface> executionStack1 = new MyStack<StatementInterface>();
                        DictInterface<String, ValueInterface> symbolTable1 = new Dictionary<String, ValueInterface>();
                        ListInterface<ValueInterface> out1 = new MyList<ValueInterface>();
                        ProgramState myProgramState1 = new ProgramState(executionStack1, symbolTable1, out1, statement1);
                        controller1.addProgram(myProgramState1);
                        controller1.allStep();
                        break;
                    case 2:
                        RepositoryInterface repository2 = new Repository();
                        Controller controller2 = new Controller(repository2, true);
                        StackInterface<StatementInterface> executionStack2 = new MyStack<StatementInterface>();
                        DictInterface<String, ValueInterface> symbolTable2 = new Dictionary<String, ValueInterface>();
                        ListInterface<ValueInterface> out2 = new MyList<ValueInterface>();
                        ProgramState myProgramState2 = new ProgramState(executionStack2, symbolTable2, out2, statement2);
                        controller2.addProgram(myProgramState2);
                        controller2.allStep();
                        break;
                    case 3:
                        RepositoryInterface repository3 = new Repository();
                        Controller controller3 = new Controller(repository3, true);
                        StackInterface<StatementInterface> executionStack3 = new MyStack<StatementInterface>();
                        DictInterface<String, ValueInterface> symbolTable3 = new Dictionary<String, ValueInterface>();
                        ListInterface<ValueInterface> out3 = new MyList<ValueInterface>();
                        ProgramState myProgramState3 = new ProgramState(executionStack3, symbolTable3, out3, statement3);
                        controller3.addProgram(myProgramState3);
                        controller3.allStep();
                        break;
                    default:
                        throw new MyException("Invalid choice. Please try again!");
                }
            }
            catch (Exception exp){
                System.out.println(exp);
            }
        }

    }
}
