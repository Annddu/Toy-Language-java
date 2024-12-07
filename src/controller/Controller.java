package controller;

import exceptions.*;
import exceptions.EmptyStackException;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.expresions.IExpression;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

// Class for the controller, used to control the execution of the program
public class Controller implements IController {
    IRepository repository; // The repository
    boolean flag; // The flag used to determine if the program is printing the output
    ExecutorService executor; // The executor service

    // Constructor for the controller
    public Controller(IRepository repo, boolean flag) {
        this.repository = repo;
        this.flag = flag;
    }

    public void oneStepAllPrg(List<PrgState> prgStatess) throws EmptyStackException {
        List<PrgState> prgStates = removeCompletedPrg(prgStatess);

        if(prgStates.isEmpty()) {
            throw new EmptyStackException("No program states");
        }

        prgStates.forEach(prgState -> {
            try {
                repository.logPrgStateExec(prgState);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callableList = prgStates.stream()
                .filter(p -> !p.getExeStack().isEmpty())
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .toList();

        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            System.out.println("Error executing thread: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        for (PrgState newState : newPrgList) {
            if (!prgStates.contains(newState)){
                prgStates.add((newState));
            }
        }

        prgStates.forEach(prgState -> {
            try {
                repository.logPrgStateExec(prgState);
            } catch (RepoException e) {
                throw new RuntimeException(e);
            }
        });

        repository.setPrgList(prgStates);
    }

    @Override
    // Method to execute all the steps of the program
    public void allStep() throws StatementException, ADTException, IOException, ExpressionException, RepoException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programsList = removeCompletedPrg(repository.getStates());
        if (programsList.isEmpty()){
            System.out.println("No program states");
            executor.shutdown();
            return;
        }

        while (!programsList.isEmpty()) {
            conservativeGarbageCollector(programsList);
            oneStepAllPrg(programsList);
            programsList.forEach(System.out::println);
            programsList = removeCompletedPrg(repository.getStates());
        }

        executor.shutdown();
        repository.setPrgList(programsList);
    }

    private void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddr = programStates.stream()
                .flatMap(p -> getAddrFromSymTable(p.getSymTable().getContent().values()).stream())
                .collect(Collectors.toList());

        programStates.forEach(p -> {
            Map<Integer, IValue> newHeap = safeGarbageCollector(symTableAddr, p.getHeap());
            p.getHeap().setContent(newHeap);
        });
    }

    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap){
        // it keeps only the addresses that are in symTableAddr si returneaza un map nou
        return heap.getMap().entrySet().stream().
                filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap){
        //tine cont de nested ref, merge in adancime in heap si verifica daca adresa e in symTableAddr si o pastreaza
        synchronized (heap) {
            List<Integer> adresses = new ArrayList<>(symTableAddr); // list of adresses
            boolean found = true; // flag to determine if an adress was found
            while (found) { // while an adress was found
                found = false; // set the flag to false
                List<IValue> referenced = new ArrayList<>(); // list of referenced values
                for (Integer adress : adresses) { // for each adress
                    IValue value = heap.getValue(adress); // get the value
                    if (value != null) { // if the value is not null
                        referenced.add(value); // add the value to the list of referenced values
                    }
                }
                List<Integer> newAdresses = getAddrFromSymTable(referenced); // get the new adresses
                for (Integer adress : newAdresses) { // for each new adress
                    if (!adresses.contains(adress)) { // if the adress is not in the list of adresses
                        adresses.add(adress); // add the adress to the list of adresses
                        found = true;
                    }
                }
            }

            Map<Integer, IValue> result = new HashMap<>(); // map of adresses and values
            for (Map.Entry<Integer, IValue> entry : heap.getMap().entrySet()) { // for each entry in the heap
                if (adresses.contains(entry.getKey())) { // if the adress is in the list of adresses
                    result.put(entry.getKey(), entry.getValue()); // add the entry to the result
                }
            }
            return result;
        }
    }

    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        //returneaza o lista cu adresele din simbol table
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> prgStates){
        return prgStates.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }


}
