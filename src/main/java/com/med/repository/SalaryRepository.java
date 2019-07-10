package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Salary;

import java.util.List;

@Repository
public interface SalaryRepository extends MongoRepository<Salary, String> {
    List<Salary> findByDoctorId(int id);

}