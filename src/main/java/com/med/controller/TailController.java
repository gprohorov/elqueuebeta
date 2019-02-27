package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.Tail;
import com.med.services.TailService;

@RequestMapping("/api/tail")
@RestController
@CrossOrigin("*")
public class TailController {

    @Autowired
    TailService service;

    @RequestMapping("/list")
    public List<Tail> showTails(){
        return service.getTails();
    }
}