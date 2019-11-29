package com.med.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.med.model.Procedure;
import com.med.repository.ProcedureRepository;

@Service
public class ProcedureService {

    @Autowired
    ProcedureRepository repository;

    @Autowired
    TailService tailService;

    public List<Procedure> getAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "_id"));
    }

    public Procedure saveProcedure(Procedure procedure) {
        if (procedure.getId() == 0) {
            if (repository.findAll().size() == 0) {
                procedure.setId(1);
            } else {
                procedure.setId(
            		repository.findAll().stream().mapToInt(Procedure::getId).max().getAsInt() + 1);
            }
        }
        Procedure procedureSaved = repository.save(procedure);
        tailService.refreshProcedures();

        return procedureSaved;
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
}