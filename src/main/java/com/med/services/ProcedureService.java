package com.med.services;

import com.med.model.Procedure;
import com.med.repository.ProcedureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class ProcedureService {

    @Autowired
    ProcedureRepository repository;

    @Autowired
    TailService tailService;

    public List<Procedure> getAll() {
        return repository.findAll().stream().sorted(Comparator.comparing(Procedure::getId)).collect(Collectors.toList());
    }

    public Procedure saveProcedure(Procedure procedure) {
        if (procedure.getId() == 0) {
            if (repository.findAll().size()==0){
                procedure.setId(1);
            } else {
                procedure.setId(repository.findAll()
                        .stream().mapToInt(Procedure::getId).max().getAsInt() + 1 );
                //tailService.
            }
        }
        return repository.save(procedure);
    }

    public Procedure getProcedure(int procedureId) {
        return repository.findById(procedureId).orElse(null);
    }

    public void deleteProcedure(int procedureId) {
        repository.deleteById(procedureId);
    }

    public Procedure updateProcedure(Procedure procedure) {
        return repository.save(procedure);
    }


    public List<Integer> getFreeProcedures() {
        return this.getAll().stream().filter(procedure -> procedure.getCard().isAnytime())
                .mapToInt(Procedure::getId).boxed().collect(Collectors.toList());
    }

   // public List<Integer>





/*

  //  private List<Procedure> procedures = new ArrayList<>();

    @Autowired
    ProcedureRepository repository;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
        procedures = dataStorage.getProcedures();
        repository.saveAll(procedures);
    }


*//*



    @Override
    public Procedure createProcedure(Procedure procedure) {

        if (procedure.getId()==0) {
            int id = this.getAll().stream().mapToInt(Procedure::getId).max().getAsInt() + 1;
            procedure.setId(id);
        }
        tailService.getAll().add(new Tail(procedure.getId(), procedure.getName(),0));

        return repository.insert(procedure);
    }

    @Override
    public Procedure updateProcedure(Procedure procedure) {
      if (procedure.getId()==0) {
            int id = this.getAll().stream().mapToInt(Procedure::getId).max().getAsInt() + 1;
            procedure.setId(id);
          tailService.getAll().add(new Tail(procedure.getId(), procedure.getName(),1));
        }else {
          Tail tail = tailService.getAll()
                  .stream().filter(tail1 -> tail1.getProcedureId() == procedure.getId()).findFirst().get();

          int indx = tailService.getAll().indexOf(tail);
          tail.setProcedureName(procedure.getName());
          tailService.getAll().set(indx,tail);
      }

        return repository.save(procedure);
    }



    @Override
    public Procedure getProcedure(int id) {
        return
                repository.findById(id).get();
    }

    @Override
    public Procedure deleteProcedure(int id) {
        Procedure procedure = this.getProcedure(id);
        repository.deleteById(id);
        Tail tail = tailService.getAll().stream()
                .filter(t->t.getProcedureId()==procedure.getId()).findFirst().get();
        tailService.getAll().remove(tail);
        return procedure;
    }

    @Override
    public List<Procedure> getAll() {
        return repository.findAll();
    }
*/
/*

    @Override
    public Procedure getProcedureListByName(String lastName) {

        return repository.findAll().stream()
                .filter(procedure -> procedure.getLastName().equals(lastName))
                .findFirst().get();
    }
*//*


*/

}
