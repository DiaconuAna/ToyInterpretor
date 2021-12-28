package Controller;

import Exceptions.*;
import Model.ADTs.StackInterface;
import Model.ProgramState;
import Model.Statements.StatementInterface;
import Model.Value.ReferenceValue;
import Model.Value.ValueInterface;
import Repository.RepositoryInterface;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private RepositoryInterface repository;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(RepositoryInterface repo){
        this.repository = repo;
        this.displayFlag = false;
    }

    public Controller(RepositoryInterface repo, boolean displayFlag){
        this.repository = repo;
        this.displayFlag = displayFlag;
    }


    Map<Integer, ValueInterface> garbageCollector(List<Integer> symbolTableAddress, Map<Integer, ValueInterface> heap){
        return heap.entrySet().stream()
                .filter(e->symbolTableAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void conservativeGarbageCollector(List<ProgramState> programs){

        //put all addresses from all symbol tables in a list
        List<Integer> allAddresses = Objects.requireNonNull(programs.stream().filter(Objects::nonNull).
                map(p -> getUsedAddresses(p.getSymbolTable().getContent().values(), p.getHeapTable().getContent().values())).
                map(Collection::stream).reduce(Stream::concat).orElse(null)).collect(Collectors.toList());

        //set the heap for each program in the list using the old garbage collector
        //the heap is shared by multiple program states now => main heap: programs.get(0).getHeapTable
        programs.forEach(
                program -> {
                    program.getHeapTable().setContent(garbageCollector(allAddresses, programs.get(0).getHeapTable().getContent()));
                }
        );
    }


    List<Integer> getAddressFromSymbolTable(Collection<ValueInterface> symbolTableValues){
        return symbolTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    List<Integer> getUsedAddresses(Collection<ValueInterface> symbolTable, Collection<ValueInterface> heapTable){
        List<Integer> symbolTableValues = symbolTable.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());

        List<Integer> heapTableValues = heapTable.stream().filter(v -> v instanceof ReferenceValue)
                 .map(value -> {ReferenceValue value2 = (ReferenceValue) value;
                 return value2.getAddress();}).collect(Collectors.toList());

        symbolTableValues.addAll(heapTableValues);

        return symbolTableValues;
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> list){
        return list.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public ProgramState addProgram(ProgramState state){
        this.repository.addProgram(state);
        return state;
    }

    public void oneStepOverall(List<ProgramState> list) throws InterruptedException {

        //print the PrgStateList into the log file
        list.forEach(program ->{
            try {
                this.repository.logProgramStateExec(program);
                displayState(program);
            } catch (RepositoryException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //prepare the list of callables
        List<Callable<ProgramState>> callList = list.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(()-> {return p.oneStep();}))
                .collect(Collectors.toList());

        //start the execution of the callables
        List<ProgramState> threads = executor.invokeAll(callList).stream().
                map(future -> {
                    try{
                        return future.get();
                    } catch (ExecutionException e) {
                        System.out.println("One step failed" + e.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        list.addAll(threads);

        list.forEach(program -> {
            try{
                repository.logProgramStateExec(program);
                displayState(program);

            } catch (RepositoryException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //executor.shutdownNow();
        repository.setProgramList(list);

    }

    public void allStep() throws RepositoryException, ControllerException, StatementException, ADTsExceptions, ExpressionException, IOException, InterruptedException {
       executor = Executors.newFixedThreadPool(2);

       //remove the completed program
       // List<ProgramState> init_list = this.repository.getProgramList().stream().collect(Collectors.toList());
        List<ProgramState> new_list = removeCompletedPrograms(this.repository.getProgramList());
       // List<ProgramState> new_list = removeCompletedPrograms(init_list);

        while(new_list.size() > 0){
            conservativeGarbageCollector(new_list);
            oneStepOverall(new_list);
           // init_list = this.repository.getProgramList().stream().collect(Collectors.toList());
            new_list = removeCompletedPrograms(this.repository.getProgramList());
            //new_list = removeCompletedPrograms(init_list);
        }

        executor.shutdownNow();
        repository.setProgramList(new_list);
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

    public void typecheck() throws StatementException, ADTsExceptions, ExpressionException {
        this.repository.getProgramList().get(0).typecheck();
    }
}
