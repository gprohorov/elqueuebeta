package com.med.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@RequestMapping("/api/tail")
@RestController
@CrossOrigin("*")
public class TailController {
/*

    @Autowired
    TailServiceImpl service;


    @RequestMapping("/list")
    public List<Tail> showTails(){
        return service.getAll();
    }

    @RequestMapping("/first/{procid}")
    public Patient showFirstActiveAndOnProcedure(@PathVariable(value = "procid") int procedureId){
        return service.getFirstForProcedure(procedureId);
    }

    @RequestMapping("/onprocedure/{procid}")
    public Patient getPatientThatIsCurrentlyOnProcedure(@PathVariable(value = "procid") int procedureId){
        return service.getTail(procedureId).getPatientOnProcedure();

    }
*/

}
