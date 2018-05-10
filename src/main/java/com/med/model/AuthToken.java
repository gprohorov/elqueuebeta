package com.med.model;

/**
 * Created by george on 04.05.18.
 */
public class AuthToken {
    private String token;

    public AuthToken() { }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
