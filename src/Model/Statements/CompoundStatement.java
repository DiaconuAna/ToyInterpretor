package Model.Statements;

import Model.ProgramState;
import Model.Statements.StatementInterface;
import Exceptions.StatementException;
import Model.ADTs.StackInterface;

public class CompoundStatement implements StatementInterface{
    StatementInterface first, second;

    public CompoundStatement(StatementInterface f, StatementInterface s){
        this.first = f;
        this.second = s;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        StackInterface<StatementInterface> stmtstack = state.getExecutionStack();
        stmtstack.push(this.second);
        stmtstack.push(this.first);
        return state;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }
}
