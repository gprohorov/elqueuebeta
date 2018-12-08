package com.med.repository.patient;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.med.model.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
	List<Patient> findByBalanceLessThan(long balance);
    @Query(value="{ 'person.fullName': { $regex: ?0, $options: 'i' } }")
    List<Patient> findByThePersonFullName(String fullName);
}