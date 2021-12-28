package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ProgramState;
import Model.Types.TypeInterface;

public class NOPStatement implements StatementInterface{

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NOPStatement();
    }

    //-----------------
    //G|- nop:void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
