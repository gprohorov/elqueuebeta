package com.med.repository.outcometree;

import com.med.model.CashBox;
import com.med.model.OutcomeTree;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeTreeRepository extends MongoRepository<OutcomeTree, String> {
	List<OutcomeTree> findByCatID(String id);
}
