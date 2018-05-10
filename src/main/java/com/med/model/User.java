package com.med.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.ArrayList;


/**
 * Created by george on 27.04.18.
 */
@Document
public class User {
    @JsonIgnore
    @Id
    private ObjectId id;

    private ArrayList<String> authorities;

    @JsonIgnore
    private String password;

    @Indexed(unique = true)
    private String username;

    @JsonIgnore
    private boolean enabled;

    @Transient
    private String token;

    @Transient
    @Nullable
    private Doctor info;

    public User() { }

    public User(ArrayList<String> authorities,
                String password,
                String username,
                boolean enabled,
                String token,
                Doctor info) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.token = token;
        this.info = info;
    }

    public User(ArrayList<String> authorities, String password, String username) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.enabled = true;
        this.token = null;
        this.info = null;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> roles) {
        this.authorities = roles;
    }

    public ObjectId getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Doctor getInfo() {
        return info;
    }

    public void setInfo(Doctor info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", authorities=" + authorities +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", token='" + token + '\'' +
                ", info=" + info +
                '}';
    }
}
