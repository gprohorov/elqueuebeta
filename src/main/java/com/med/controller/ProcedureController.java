package com.med.controller;

import com.med.model.Procedure;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProcedureController {

    @Autowired
    ProcedureServiceImpl service;


    @RequestMapping("/procedure/list")
   public List<Procedure> showProceduresDefault(){
        return service.getAll();
    }


    @RequestMapping("/procedure/list/")
   public List<Procedure> showProcedures(){
        return service.getAll();
    }


 ////////////////////////////// CRUD ////////////////////////////////////

    // CREATE a new Procedure
    @PostMapping("/procedure/create")
    public Procedure createProcedure(@Valid @RequestBody Procedure procedure) {
        return service.createProcedure(procedure);
    }


    // READ the Procedure by id
    @GetMapping("/procedure/get/{id}")
    public Procedure showOneProcedure(@PathVariable(value = "id")  int procedureId) {

        return service.getProcedure(procedureId);
    }

    // UPDATE the procedure by id
    @PostMapping("/procedure/update/")
    public Procedure updateProcedure(@Valid @RequestBody Procedure updates)  {
      //  updates.setId(procedureId);
//
        return service.updateProcedure(updates);

    }

    // DELETE the procedure by id
    @PostMapping("/procedure/delete/{id}")
    public Procedure delProcedure(@PathVariable(value = "id")  int procedureId)  {

        return service.deleteProcedure(procedureId);

    }


}

