package com.med.services.hotel.record.interfaces;

import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IRecordService {

    Record createRecord(Record record);
    Record updateRecord(Record record);
    Record getRecord(String recordId);

    List<Record> getAll();
    List<Record> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish);
    List<Record> getAllForKoikaFromTo(Koika koika, LocalDate start, LocalDate finish);

}
