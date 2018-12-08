package com.med.repository.hotel;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;

@Repository
public interface RecordRepository extends MongoRepository<Record, String> {
    List<Record> findByPatientId(String patientId);
    List<Record> findByKoika(Koika koika);
    List<Record> findByStartGreaterThan(LocalDateTime dateTime);
    List<Record> findByFinishGreaterThan(LocalDateTime dateTime);
    List<Record> findByKoikaAndStartAfter(Koika koika, LocalDateTime start);
}