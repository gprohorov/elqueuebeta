package com.med.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Therapy;

@Repository
public interface TherapyRepository extends MongoRepository<Therapy, String> {
    public List<Therapy> findByPatientId(String patientId);
    public List<Therapy> findAllByStartBefore(LocalDateTime localDateTime);
}