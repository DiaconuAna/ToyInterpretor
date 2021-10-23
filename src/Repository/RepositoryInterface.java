package Repository;

import Exceptions.RepositoryException;
import Model.ProgramState;

public interface RepositoryInterface {
    ProgramState getCurrentProgram() throws RepositoryException;
    ProgramState addProgram(ProgramState p);
}
