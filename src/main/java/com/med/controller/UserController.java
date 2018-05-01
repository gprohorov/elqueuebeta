package com.med.controller;

import com.med.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by george on 3/9/18.
 */

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService service;




    // Login User
    @PostMapping("/user/login")
    public UserDetails loginUser(@Valid @RequestBody String username, String password) {

        return service.loadUserByUsername(username);
    }



}
