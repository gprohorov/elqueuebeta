package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.SalaryDTO;

@Repository
public interface SalaryDTORepository extends MongoRepository<SalaryDTO, String> {}