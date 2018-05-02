package com.med.services.procedure.impls;

import com.med.model.Procedure;
import com.med.model.Tail;
import com.med.repository.procedure.ProcedureRepository;
import com.med.services.procedure.interfaces.IProcedureService;
import com.med.services.tail.Impls.TailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class ProcedureServiceImpl implements IProcedureService {


  //  private List<Procedure> procedures = new ArrayList<>();

    @Autowired
    ProcedureRepository repository;

    @Autowired
    TailServiceImpl tailService;


/*


    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
        procedures = dataStorage.getProcedures();
        repository.saveAll(procedures);
    }


*/


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
        return procedure;
    }

    @Override
    public List<Procedure> getAll() {
        return repository.findAll();
    }
/*

    @Override
    public Procedure getProcedureListByName(String lastName) {

        return repository.findAll().stream()
                .filter(procedure -> procedure.getLastName().equals(lastName))
                .findFirst().get();
    }
*/


}
