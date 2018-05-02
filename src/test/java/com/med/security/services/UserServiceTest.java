package com.med.security.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by george on 02.05.18.
 */
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void loadUserByUsername() throws Exception {
        UserDetails user = userService.loadUserByUsername("root");
        System.out.println(user);
    }

    @Test
    public void findById() throws Exception {

    }

}