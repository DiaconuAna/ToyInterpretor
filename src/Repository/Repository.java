package Repository;


import Model.ProgramState;
import Model.ADTs.ListInterface;
import Exceptions.RepositoryException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;


public class Repository implements RepositoryInterface {

    List<ProgramState> program_list;
    String logFilePath;

    public Repository(){
        this.program_list = new LinkedList<ProgramState>();
    }

    public Repository(String logFP){
        this.program_list = new LinkedList<ProgramState>();
        this.logFilePath = logFP;
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

    @Override
    public void logProgramStateExec() throws RepositoryException, IOException {
        PrintWriter logFile;
        try {
           logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        }
        catch(IOException exc){
            throw new RepositoryException(exc.getMessage());
        }

        for(ProgramState state: this.program_list){
            logFile.write(getCurrentProgram().toString());
        }
        logFile.close();
    }
}
