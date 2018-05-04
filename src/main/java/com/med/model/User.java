package com.med.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by george on 27.04.18.
 */
@Document
public class User {
    @Id
    private ObjectId id;

    /* private List<Role> authorities; */

    private String password;

    @Indexed(unique = true)
    private String username;

    private boolean enabled;

    public User() { }

    public User( String password, String username) {
        /* this.authorities = new ArrayList<Role>(); */
        this.password = password;
        this.username = username;
        this.enabled = true;
    }

    /*
    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> roles) {
        this.authorities = roles;
    }
    */

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

}
