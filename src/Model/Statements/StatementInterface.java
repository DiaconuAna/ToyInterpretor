package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Model.ProgramState;
import Exceptions.StatementException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException;
}
