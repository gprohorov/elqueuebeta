package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Accrual;

@Repository
public interface AccrualRepository extends MongoRepository<Accrual, String> {}