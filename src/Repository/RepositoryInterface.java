package Repository;

import Exceptions.RepositoryException;
import Model.ADTs.ListInterface;
import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface {
    //ProgramState getCurrentProgram() throws RepositoryException;
    ProgramState addProgram(ProgramState p);
    void logProgramStateExec(ProgramState ps) throws RepositoryException, IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> l);
}
