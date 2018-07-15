package com.med.services.procedure.impls;

import com.med.model.Procedure;
import com.med.repository.procedure.ProcedureRepository;
import com.med.services.procedure.interfaces.IProcedureService;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class ProcedureServiceImpl implements IProcedureService {

    @Autowired
    ProcedureRepository repository;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    TalonServiceImpl talonService;

    @Override
    public List<Procedure> getAll() {
        return repository.findAll().stream().sorted(Comparator.comparing(Procedure::getId)).collect(Collectors.toList());
    }

    @Override
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

    @Override
    public Procedure getProcedure(int procedureId) {
        return repository.findById(procedureId).orElse(null);
    }
    @Override
    public void deleteProcedure(int procedureId) {
        repository.deleteById(procedureId);
    }

    @Override
    public Procedure updateProcedure(Procedure procedure) {
        return repository.save(procedure);
    }


    public List<Integer> getFreeProcedures() {
        return this.getAll().stream().filter(procedure -> procedure.getCard().isAnytime())
                .mapToInt(Procedure::getId).boxed().collect(Collectors.toList());
    }

   // public List<Integer>




}
