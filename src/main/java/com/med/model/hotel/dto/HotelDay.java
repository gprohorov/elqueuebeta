package com.med.model.hotel.dto;

import com.med.model.hotel.State;

import java.time.LocalDate;

public class HotelDay {

    private LocalDate date;
    private State state;

    public HotelDay() {
    }

    public HotelDay(LocalDate date, State state) {
        this.date = date;
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "HotelDay{" +
                "date=" + date +
                ", state=" + state +
                '}';
    }
}
