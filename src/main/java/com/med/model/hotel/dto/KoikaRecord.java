package com.med.model.hotel.dto;

import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;

import java.util.List;

/**
 * Created by george on 27.07.18.
 */
public class KoikaRecord {

   private Koika koika;
   List<Record> records;

    public KoikaRecord() {
    }

    public KoikaRecord(Koika koika, List<Record> records) {
        this.koika = koika;
        this.records = records;
    }

    public Koika getKoika() {
        return koika;
    }

    public void setKoika(Koika koika) {
        this.koika = koika;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
