package com.med.repository.hotel;

import com.med.model.hotel.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface RecordRepository extends MongoRepository<Record, String>{
    List<Record> findByPatientId(String patientId);

}
