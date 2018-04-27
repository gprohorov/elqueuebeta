package com.med.model;

import java.time.LocalDateTime;

/**
 * Created by george on 27.04.18.
 */
public class User {
    private int id;
    private String username;
    private  String password;
    private Role  role;
    private boolean active  = true;
    private LocalDateTime  createDateTime;




}
