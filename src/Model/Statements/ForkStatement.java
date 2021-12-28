package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.MyStack;
import Model.ADTs.StackInterface;
import Model.ProgramState;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;

import java.io.IOException;

public class ForkStatement implements StatementInterface{

    StatementInterface fork_statement;

    public ForkStatement(StatementInterface fs){
        this.fork_statement = fs;
    }

    public StatementInterface getFork_statement(){
        return this.fork_statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException {

        DictInterface<String, ValueInterface> SymTable2 = state.getSymbolTable().clone();
        StackInterface<StatementInterface> ExeStack2 = new MyStack<>();

        //add the fork statement in it
        //ExeStack2.push(this.fork_statement);

        //create a new program state with a new symbol table, a new exe stack and the others
        ProgramState newProgram = new ProgramState(ExeStack2, SymTable2, state.getOut(), this.fork_statement, state.getFileTable(), state.getHeapTable());

        return newProgram;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ForkStatement(fork_statement);
    }


    //G|- stmt:void,G1
    //------------------------------------------
    //G|- fork(stmt):void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        this.fork_statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString(){
        return "fork( " + this.fork_statement.toString() + ")";
    }
}
