package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.Tail;
import com.med.services.tail.Impls.TailServiceImpl;

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