package com.med.controller;

import com.med.model.Tail;
import com.med.services.tail.Impls.TailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
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


}
