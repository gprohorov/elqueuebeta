package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Procedure;

@Repository
public interface ProcedureRepository extends MongoRepository<Procedure, Integer> {}