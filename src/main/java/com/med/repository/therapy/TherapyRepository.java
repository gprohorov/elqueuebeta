package com.med.repository.therapy;

import com.med.model.Therapy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface TherapyRepository extends MongoRepository<Therapy, String>{
    public List<Therapy> findByPatientId(String patientId);
}
