package Repository;

import Exceptions.RepositoryException;
import Model.ProgramState;

import java.io.IOException;

public interface RepositoryInterface {
    ProgramState getCurrentProgram() throws RepositoryException;
    ProgramState addProgram(ProgramState p);
    void logProgramStateExec() throws RepositoryException, IOException;
}
