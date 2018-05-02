package com.med.security.model;

/**
 * Created by george on 02.05.18.
 */
public enum UserField {
    USER_NAME("username");

    private final String field;

    UserField(String field) {
        this.field = field;
    }

    public String field() {
        return field;
    }
}
