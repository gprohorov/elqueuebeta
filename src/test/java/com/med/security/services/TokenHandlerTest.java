package com.med.security.services;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Created by george on 02.05.18.
 */
public class TokenHandlerTest {

    @Autowired
    UserService userService;
    @Test
    public void generateToken() throws Exception {

            // this id corresponds to admin in the collection "user"

        ObjectId adminId = new ObjectId("5aea0df7f363364c64b20f91");

            TokenHandler tokenHandler = new TokenHandler();
            String token = tokenHandler.generateAccessToken(adminId, LocalDateTime.now().plusDays(14));
            System.out.println(token);

           ObjectId id = tokenHandler.extractUserId(token).get();
           System.out.println(id.toString());


    }
    }

