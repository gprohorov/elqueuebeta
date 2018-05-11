package com.med.controller;

import com.med.model.Patient;
import com.med.model.Tail;
import com.med.services.tail.Impls.TailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
        return service.getAll();
    }

    @RequestMapping("/first/{procid}")
    public Patient showFirstActiveAndOnProcedure(@PathVariable(value = "procid") int procedureId){
        return service.getFirstForProcedure(procedureId);
    }

}
