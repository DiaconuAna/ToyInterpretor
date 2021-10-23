package Repository;


import Model.ProgramState;
import Model.ADTs.ListInterface;
import Exceptions.RepositoryException;

import java.util.LinkedList;
import java.util.List;


public class Repository implements RepositoryInterface {

    List<ProgramState> program_list;

    public Repository(){
        this.program_list = new LinkedList<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgram() throws RepositoryException {
        if(program_list.isEmpty())
            throw new RepositoryException("Program list is empty");
        return program_list.get(program_list.size()-1);
    }

    @Override
    public ProgramState addProgram(ProgramState p) {
        this.program_list.add(p);
        return p;
    }
}
