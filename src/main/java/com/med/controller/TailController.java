package com.med.controller;

import com.med.model.Tail;
import com.med.services.tail.Impls.TailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@RequestMapping("/api/tail")
@RestController
@CrossOrigin("*")
public class TailController {


    @Autowired
    TailServiceImpl service;


    @RequestMapping("/list")
    public List<Tail> showTails(){
        return service.getTails();
    }
/*
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
