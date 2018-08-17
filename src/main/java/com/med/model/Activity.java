package com.med.model;

/**
 * Created by george on 3/22/18.
 */
public enum Activity {
    ON_PROCEDURE(8),
    INVITED(7),
    ACTIVE(6),
    GAMEOVER(5),
    STUCK(5),
    TEMPORARY_NA(5),
    NON_ACTIVE(4),
    EXECUTED(3),
    CANCELED(2),
    EXPIRED(1),
    NULL(0);

    private final int level;

    private Activity(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
