package com.med.model.hotel;

import java.time.LocalDate;

/**
 * Created by george on 05.07.18.
 */
public class KoikaDay {
    private Koika koika;
    private  LocalDate date;
    private State state;

    public KoikaDay() {
    }

    public KoikaDay(Koika koika, LocalDate date, State state) {
        this.koika = koika;
        this.date = date;
        this.state = state;
    }

    public Koika getKoika() {
        return koika;
    }

    public void setKoika(Koika koika) {
        this.koika = koika;
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
        return "KoikaDay{" +
                "koika=" + koika +
                ", date=" + date +
                ", state=" + state +
                '}';
    }
}
