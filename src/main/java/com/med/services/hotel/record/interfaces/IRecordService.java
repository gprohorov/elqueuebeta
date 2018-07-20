package com.med.services.hotel.record.interfaces;

import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IRecordService {

    Record createRecord(Record record);
    Record updateRecord(Record record);
    Record getRecord(String recordId);
    Record findByKoikaAndDate(Koika koika, LocalDateTime localDateTime);

    List<Record> getAll();
    List<Record> saveAll(List<Record> records);
    List<Record> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish);
    List<Record> getAllForKoikaFromTo(Koika koika, LocalDate start, LocalDate finish);
    List<KoikaLine> getLines(int days);
}
