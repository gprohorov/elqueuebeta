package com.med.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class User implements UserDetails {
	
    @Id
    private String id;

    private ArrayList<Role> authorities;

    @JsonIgnore
    private String password;

    @Indexed(unique = true)
    private String username;

    private boolean enabled;

    @Transient
    private String token;

    @Transient
    @Nullable
    private Doctor info;

    public User() {}

    public User(ArrayList<Role> authorities, String password, String username, boolean enabled,
    		String token, Doctor info) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.token = token;
        this.info = info;
    }

    public User(ArrayList<Role> authorities, String password, String username) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.enabled = true;
        this.token = null;
        this.info = null;
    }

    public ArrayList<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<Role> roles) {
        this.authorities = roles;
    }

    public String getId() {
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
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
                "}";
    }
}