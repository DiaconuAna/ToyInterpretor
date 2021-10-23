package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState;

public class NOPStatement implements StatementInterface{

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        return state;
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
