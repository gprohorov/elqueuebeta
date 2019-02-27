package com.med.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @GetMapping("/list-with-doctors")
    public List<User> findAllWithDoctors() {
    	return service.findAllWithDoctors();
    }
    
    @GetMapping("/get/{id}")
    public User getOne(@PathVariable(value = "id") String id) {
        return service.findById(id).get();
    }
    
    @GetMapping("/get-with-doctor/{id}")
    public User getWithDoctor(@PathVariable(value = "id") String id) {
    	return service.findByIdWithDoctor(id);
    }
    
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable(value = "id") String id) {
    	service.deleteById(id);
    }
    
    @PostMapping("/update/")
    public void update(@Valid @RequestBody User model) {
    	service.update(model);
    }
    
    @GetMapping("/generatepass/{pass}")
    public String generatePass(@PathVariable(value = "pass") String pass) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	return encoder.encode(pass);
    }
}