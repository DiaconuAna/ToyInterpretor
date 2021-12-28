package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.ProgramState;
import Exceptions.StatementException;
import Model.Types.TypeInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException;
    StatementInterface deepCopy();
    DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions;
}
