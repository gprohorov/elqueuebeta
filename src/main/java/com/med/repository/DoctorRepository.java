package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Doctor;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, Integer> {}