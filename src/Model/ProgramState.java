package Model;

import Exceptions.ADTsExceptions;
import Exceptions.ControllerException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.*;
import Model.Statements.StatementInterface;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    StackInterface<StatementInterface> executionStack;
    DictInterface<String, ValueInterface> symbolTable;
    ListInterface<ValueInterface> out;
    StatementInterface originalProgram;
    DictInterface<StringValue, BufferedReader> fileTable;
    HeapInterface<ValueInterface> heapTable;
    private int stateID;
    public static int lastID=0;

    public synchronized int getNewProgramStateID(){
        ++lastID;
        return lastID;
    }

    public ProgramState(StackInterface<StatementInterface> exe, DictInterface<String, ValueInterface> sym,
            ListInterface<ValueInterface> out, StatementInterface org){
        this.executionStack = exe;
        this.symbolTable = sym;
        this.out = out;
        this.originalProgram = org.deepCopy();
        this.stateID = getNewProgramStateID();
        this.executionStack.push(this.originalProgram);
    }

    public ProgramState(StackInterface<StatementInterface> exe, DictInterface<String, ValueInterface> sym,
                        ListInterface<ValueInterface> out, StatementInterface org, DictInterface<StringValue, BufferedReader> file){
        this.executionStack = exe;
        this.symbolTable = sym;
        this.out = out;
        this.originalProgram = org.deepCopy();
        this.fileTable = file;
        this.stateID = getNewProgramStateID();
        this.executionStack.push(this.originalProgram);
    }

    public ProgramState(StackInterface<StatementInterface> exe, DictInterface<String, ValueInterface> sym,
                        ListInterface<ValueInterface> out, StatementInterface org, DictInterface<StringValue, BufferedReader> file, HeapInterface<ValueInterface> h){
        this.executionStack = exe;
        this.symbolTable = sym;
        this.out = out;
        this.originalProgram = org.deepCopy();
        this.fileTable = file;
        this.heapTable = h;
        this.stateID = getNewProgramStateID();
        this.executionStack.push(this.originalProgram);
    }

    public HeapInterface<ValueInterface> getHeapTable(){
        return this.heapTable;
    }

    void setHeapTable(HeapInterface<ValueInterface> newHeap){
        this.heapTable = newHeap;
    }

    public StackInterface<StatementInterface> getExecutionStack(){
        return this.executionStack;
    }

    public void setExecutionStack(StackInterface<StatementInterface> stack){
        this.executionStack = stack;
    }

    public DictInterface<String, ValueInterface> getSymbolTable(){
        return this.symbolTable;
    }

    public void setSymbolTable(DictInterface<String, ValueInterface> sym){
        this.symbolTable = sym;
    }

    public ListInterface<ValueInterface> getOut(){
        return this.out;
    }

    public void setOut(ListInterface<ValueInterface> l){
        this.out = l;
    }

    public StatementInterface getOriginalProgram(){
        return this.originalProgram;
    }

    public void setOriginalProgram(StatementInterface org){
        this.originalProgram = org;
    }

    public DictInterface<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    public void setFileTable(DictInterface<StringValue, BufferedReader> ft){
        this.fileTable = ft;
    }

    public Boolean isNotCompleted(){
        return !this.executionStack.isEmpty();
    }

    public ProgramState oneStep() throws ControllerException, ADTsExceptions, StatementException, ExpressionException, IOException {
        if(this.executionStack.isEmpty())
            throw new ADTsExceptions("Empty execution stack!");
        StatementInterface currentStatement = this.executionStack.pop();

        return currentStatement.execute(this);
    }

    public void typecheck() throws StatementException, ADTsExceptions, ExpressionException {
        originalProgram.typecheck(new Dictionary<>());
    }

        @Override
    public String toString(){
        String msg;
        msg =  " * * * * * * * PROGRAM STATE * * * * * * * * * * * * * * * * * \n";
        msg+=  " * * * * * * * ID: " + stateID + "* * * * * * * * * * * * * * * \n";
        msg += " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
        msg += "* * * * * * * * Execution Stack * * * * * * * * * * * * * * * *\n";
        msg += this.executionStack.toString() + "\n";
        msg += "* * * * * * * * Symbol Table * * * * * * * * * * * * * * * * * \n";
        msg += this.symbolTable.toString() + "\n";
        msg += "* * * * * * * * Output List * * * * * * * * * * * * * * * * * *\n";
        msg += this.out.toString() + "\n";
        msg += "* * * * * * * * File Table * * * * * * * * * * * * * * * * * * *\n";
        msg += this.fileTable.toString() + "\n";
        msg += " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
        msg+= "* * * * * * * * Heap * * * * * * * * * * * * * * * * * *\n";
        msg += this.heapTable.toString();
        msg += "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";

        return msg;
    }
}
