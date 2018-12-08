package com.med.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.OutcomeTree;

@Repository
public interface OutcomeTreeRepository extends MongoRepository<OutcomeTree, String> {
	List<OutcomeTree> findByCatID(String id);
}