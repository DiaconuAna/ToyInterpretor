package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.ProgramState;
import Model.Statements.StatementInterface;
import Exceptions.StatementException;
import Model.ADTs.StackInterface;
import Model.Types.TypeInterface;

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
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        DictInterface<String, TypeInterface> typeEnv1 = first.typecheck(typeEnv);
        DictInterface<String, TypeInterface> typeEnv2 = second.typecheck(typeEnv1);

        return typeEnv2;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }
}
