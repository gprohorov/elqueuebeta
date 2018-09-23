package com.med.repository.hotel;

import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface RecordRepository extends MongoRepository<Record, String>{
    List<Record> findByPatientId(String patientId);
    List<Record> findByKoika(Koika koika);
    List<Record> findByStartGreaterThan(LocalDateTime dateTime);
    List<Record> findByFinishGreaterThan(LocalDateTime dateTime);
    
    List<Record> findByKoikaAndStartAfter(Koika koika, LocalDateTime start);
}
