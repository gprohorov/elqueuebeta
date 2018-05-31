package com.med.model;

import java.time.LocalDateTime;

/**
 * Created by george on 30.05.18.
 */
public class Hotel {
    private String id;
    private String patientId;
    private String desc;
    private HotelPlace place;
    private LocalDateTime start;
    private LocalDateTime finish;
    private HotelState state;
}
