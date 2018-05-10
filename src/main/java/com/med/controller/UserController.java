package com.med.controller;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.med.model.User;
import com.med.services.user.UserService;

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
    public User getOne(@PathVariable(value = "id") ObjectId id){
        return service.findById(id).get();
    }

}
