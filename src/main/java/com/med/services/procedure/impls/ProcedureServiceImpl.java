package com.med.services.procedure.impls;

import com.med.model.Procedure;
import com.med.repository.procedure.ProcedureRepository;
import com.med.services.procedure.interfaces.IProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class ProcedureServiceImpl implements IProcedureService {


    private List<Procedure> procedures = new ArrayList<>();

    @Autowired
    ProcedureRepository repository;


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
        return repository.insert(procedure);
    }

    @Override
    public Procedure updateProcedure(Procedure procedure) {


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
