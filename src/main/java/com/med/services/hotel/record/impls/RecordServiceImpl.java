package com.med.services.hotel.record.impls;

import com.med.model.hotel.Record;
import com.med.repository.hotel.RecordRepository;
import com.med.services.hotel.record.interfaces.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 01.06.18.
 */
@Service
public class RecordServiceImpl implements IRecordService {

    private  List<Record> records;

    @Autowired
    RecordRepository repository;

    @Override
    public Record createRecord(Record record) {
        return repository.save(record);
    }

    @Override
    public Record updateRecord(Record record) {
       return repository.save(record);
    }


    public List<Record> saveAll(List<Record> records) {

        return repository.saveAll(records);
    }
    @Override
    public List<Record> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Record> getAllForPatientFromTo(String patientId, LocalDate start, LocalDate finish) {

        return repository.findByPatientId(patientId).stream()
                .filter(record->record.getFinish() != null)
                .filter(record->record.getStart().toLocalDate().isAfter(start.minusDays(1)))
                .filter(record->record.getFinish().toLocalDate().isBefore(finish.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public Record getRecord(String recordId) {
        return repository.findById(recordId).orElse(null);
    }
}
