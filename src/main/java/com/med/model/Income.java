package com.med.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * Created by george on 12.05.18.
 */
public class Income {
    private ObjectId id;
    private int personId;
    private LocalDateTime dateTime;
    private Integer sum;

}
