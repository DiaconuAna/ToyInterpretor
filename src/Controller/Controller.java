package Controller;

import Exceptions.*;
import Model.ADTs.StackInterface;
import Model.ProgramState;
import Model.Statements.StatementInterface;
import Repository.RepositoryInterface;

import java.io.IOException;

public class Controller {
    private RepositoryInterface repository;
    boolean displayFlag;

    public Controller(RepositoryInterface repo){
        this.repository = repo;
        this.displayFlag = false;
    }

    public Controller(RepositoryInterface repo, boolean displayFlag){
        this.repository = repo;
        this.displayFlag = displayFlag;
    }

    public ProgramState addProgram(ProgramState state){
        this.repository.addProgram(state);
        return state;
    }

    public ProgramState oneStep(ProgramState state) throws ControllerException, ADTsExceptions, StatementException, ExpressionException, IOException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();

        if(stack.isEmpty())
            throw new ControllerException("Execution stack is empty!");
        StatementInterface currentStatement = stack.pop();

        return currentStatement.execute(state);
    }

    public void allStep() throws RepositoryException, ControllerException, StatementException, ADTsExceptions, ExpressionException, IOException {
        ProgramState program = this.repository.getCurrentProgram();
        this.repository.logProgramStateExec();
        if(getDisplayFlag())
            displayState(program);

        while(!program.getExecutionStack().isEmpty()){
            oneStep(program);
            this.repository.logProgramStateExec();

            if(getDisplayFlag())
                displayState(program);
        }
    }

    public void setDisplayFlag(boolean state){
        this.displayFlag = state;
    }

    public boolean getDisplayFlag(){
        return this.displayFlag;
    }

    public void displayState(ProgramState program){
        if(this.displayFlag){
            System.out.println(program.toString());
        }
    }
}
