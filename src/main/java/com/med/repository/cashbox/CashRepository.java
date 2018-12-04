package com.med.repository.cashbox;

import com.med.model.CashBox;
import com.med.model.Doctor;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends MongoRepository<CashBox, String> {
	List<CashBox> findByItemId(String id);
}
