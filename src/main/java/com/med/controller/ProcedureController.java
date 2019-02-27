package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.Procedure;
import com.med.services.ProcedureService;

@RestController
@RequestMapping("/api/procedure")
@CrossOrigin("*")
public class ProcedureController {

	@Autowired
    ProcedureService service;

    @RequestMapping("/list/")
    public List<Procedure> showProcedures() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public Procedure showOneProcedure(@PathVariable(value = "id") int procedureId) {
        return service.getProcedure(procedureId);
    }

    @PostMapping("/save")
    public Procedure saveProcedure(@RequestBody Procedure procedure) {
        return service.saveProcedure(procedure);
    }

    @PostMapping("/delete/{id}")
    public Procedure delProcedure(@PathVariable(value = "id") int procedureId) {
        return null;
        //service.deleteProcedure(procedureId);
    }
}