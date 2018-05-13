package com.med.repository.patient;

import com.med.model.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface PatientRepository extends MongoRepository<Patient, ObjectId>{
  //  List<Patient> findByLastName(String lastName);

}
