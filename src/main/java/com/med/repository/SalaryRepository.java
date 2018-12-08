package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Salary;

@Repository
public interface SalaryRepository extends MongoRepository<Salary, String> {}