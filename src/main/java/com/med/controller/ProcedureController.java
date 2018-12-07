package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.Procedure;
import com.med.services.procedure.impls.ProcedureServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProcedureController {

	@Autowired
    ProcedureServiceImpl service;

    @RequestMapping("/procedure/list/")
    public List<Procedure> showProcedures() {
        return service.getAll();
    }

    @GetMapping("/procedure/get/{id}")
    public Procedure showOneProcedure(@PathVariable(value = "id") int procedureId) {
        return service.getProcedure(procedureId);
    }

    @PostMapping("/procedure/save")
    public Procedure saveProcedure(@RequestBody Procedure procedure) {
        return service.saveProcedure(procedure);
    }

    @PostMapping("/procedure/delete/{id}")
    public Procedure delProcedure(@PathVariable(value = "id") int procedureId) {
        return null;
        //service.deleteProcedure(procedureId);
    }
}