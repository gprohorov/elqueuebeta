package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.med.model.User;
import com.med.services.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/list")
    public List<User> listUser() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public User getOne(@PathVariable(value = "id") String id) {
        return service.findById(id).get();
    }
    
    @GetMapping("/generatepass/{pass}")
    public String generatePass(@PathVariable(value = "pass") String pass) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	return encoder.encode(pass);
    }
}