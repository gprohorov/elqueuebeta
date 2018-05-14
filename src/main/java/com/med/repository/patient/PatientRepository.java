package com.med.repository.patient;

import com.med.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface PatientRepository extends MongoRepository<Patient, String>{
  //  List<Patient> findByLastName(String lastName);

}
