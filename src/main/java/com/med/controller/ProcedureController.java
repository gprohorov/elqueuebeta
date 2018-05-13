package com.med.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProcedureController {

/*

    @Autowired
    ProcedureServiceImpl service;

    @RequestMapping("/procedure/list/")
    public List<Procedure> showProcedures() {
        return service.getAll();
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

*/

}

