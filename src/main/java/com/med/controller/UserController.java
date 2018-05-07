package com.med.controller;

import com.med.model.User;
import com.med.services.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService service;

    // Login User
    @PostMapping("/user/login")
    public User loginUser(@Valid @RequestBody String username, String password) {
        return service.findOne(username);
    }

    @GetMapping("/user/list")
    public List<User> listUser(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable(value = "id") ObjectId id){
        return service.findById(id).get();
    }

}
