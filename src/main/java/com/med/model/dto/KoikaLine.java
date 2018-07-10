package com.med.model.dto;

import com.med.model.hotel.Koika;

import java.util.ArrayList;
import java.util.List;

public class KoikaLine {

    Koika koika;
    List<HotelDay> line = new ArrayList<>();

    public KoikaLine() {
    }

    public KoikaLine(Koika koika, List<HotelDay> line) {
        this.koika = koika;
        this.line = line;
    }

    public Koika getKoika() {
        return koika;
    }

    public void setKoika(Koika koika) {
        this.koika = koika;
    }

    public List<HotelDay> getLine() {
        return line;
    }

    public void setLine(List<HotelDay> line) {
        this.line = line;
    }
}
