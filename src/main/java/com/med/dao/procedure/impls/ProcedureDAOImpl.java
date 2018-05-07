package com.med.dao.procedure.impls;

import com.med.DataStorage;
import com.med.dao.procedure.interfaces.IProcedureDAO;
import com.med.model.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Component
public class ProcedureDAOImpl implements IProcedureDAO {


    private List<Procedure> procedures = new ArrayList<>();

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
        procedures = dataStorage.getProcedures();
    }


    @Override
    public Procedure createProcedure(Procedure procedure) {

        procedures.add(procedure);

        return procedure;
    }


    @Override
    public Procedure getProcedure(int id) {
        return procedures.stream().filter(procedure -> procedure.getId() == id).findFirst().get();
    }

    @Override
    public Procedure updateProcedure(Procedure procedure) {

        if (procedure.getId()==0){
            int doctord = this.getAll().stream().mapToInt(el->el.getId())
                    .max().getAsInt() + 1;
            procedure.setId(doctord);
            this.createProcedure(procedure);
        }
        else {
            Procedure oldValues = this.getProcedure(procedure.getId());
            int index = this.getAll().indexOf(oldValues);
            this.getAll().set(index,procedure);
        }
        return procedure;
    }

    @Override
    public Procedure deleteProcedure(int id) {

        return procedures.remove(id);
    }


    @Override
    public List<Procedure> getAll() {
        return procedures;
    }
}
