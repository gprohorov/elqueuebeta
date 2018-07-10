package com.med.model;

/**
 * Created by george on 3/9/18.
 */
public enum Status {

    FOREIGN(7),
    VIP(6),
    BUSINESS(5),
    ALL_INCLUSIVE(4),
    SOCIAL(3),
    NULL(0);

    private final int level;

    private Status(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
