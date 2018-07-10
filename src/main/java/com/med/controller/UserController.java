package com.med.controller;

import com.med.model.User;
import com.med.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/user/list")
    public List<User> listUser(){
        return service.findAll();
    }
    
    @GetMapping("/users/{id}")
    public User getOne(@PathVariable(value = "id") String id){
        return service.findById(id).get();
    }
    
    @GetMapping("/user/generatepass/{pass}")
    public String generatePass(@PathVariable(value = "pass") String pass){
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	return encoder.encode(pass);
    }

}
