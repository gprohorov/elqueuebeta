package com.med.repository.patient;

import com.med.model.Patient;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface PatientRepository extends MongoRepository<Patient, String>{

	List<Patient> findByBalanceLessThan(long balance);
    
    @Query(value="{ 'person.fullName': { $regex: ?0, $options: 'i' } }")
    List<Patient> findByThePersonFullName(String fullName);

}
