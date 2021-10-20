package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Model.ProgramState;
import Exceptions.StatementException;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions;
}
