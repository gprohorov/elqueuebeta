package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.Talon;
import com.med.services.TalonService;
import com.med.services.UserService;

@RequestMapping("/api/talon")
@RestController
@CrossOrigin("*")
public class TalonController {

    @Autowired
    TalonService service;
    
    @Autowired
	UserService userService;

    @RequestMapping("/list")
    public List<Talon> showTails() {
        return service.getAll();
    }
    
    @GetMapping("/execute/{talonId}")
    public Talon execute(@PathVariable(value = "talonId") String talonId) {
        return service.quickExecute(talonId);
    }
}