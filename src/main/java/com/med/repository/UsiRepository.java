package com.med.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Usi;

@Repository
public interface UsiRepository extends MongoRepository<Usi, String> {
    List<Usi> findByPatientId(String patientId);
}