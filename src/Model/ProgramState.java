package Model;

import Model.ADTs.StackInterface;
import Model.ADTs.DictInterface;
import Model.ADTs.ListInterface;
import Model.Statements.StatementInterface;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;

import java.io.BufferedReader;

public class ProgramState {
    StackInterface<StatementInterface> executionStack;
    DictInterface<String, ValueInterface> symbolTable;
    ListInterface<ValueInterface> out;
    StatementInterface originalProgram;
    DictInterface<StringValue, BufferedReader> fileTable;

    public ProgramState(StackInterface<StatementInterface> exe, DictInterface<String, ValueInterface> sym,
            ListInterface<ValueInterface> out, StatementInterface org){
        this.executionStack = exe;
        this.symbolTable = sym;
        this.out = out;
        this.originalProgram = org; //TODO: deepcopy ??

        this.executionStack.push(this.originalProgram);
    }

    public ProgramState(StackInterface<StatementInterface> exe, DictInterface<String, ValueInterface> sym,
                        ListInterface<ValueInterface> out, StatementInterface org, DictInterface<StringValue, BufferedReader> file){
        this.executionStack = exe;
        this.symbolTable = sym;
        this.out = out;
        this.originalProgram = org;
        this.fileTable = file;

        this.executionStack.push(this.originalProgram);
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

    @Override
    public String toString(){
        String msg;
        msg = " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
        msg += "* * * * * * * * Execution Stack * * * * * * * * * * * * * * * *\n";
        msg += this.executionStack.toString() + "\n";
        msg += "* * * * * * * * Symbol Table * * * * * * * * * * * * * * * * * \n";
        msg += this.symbolTable.toString() + "\n";
        msg += "* * * * * * * * Output List * * * * * * * * * * * * * * * * * *\n";
        msg += this.out.toString() + "\n";
        msg += "* * * * * * * * File Table * * * * * * * * * * * * * * * * * *\n";
        msg += this.fileTable.toString() + "\n";
        msg += " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";

        return msg;
    }
}
