package com.med.repository.cashbox;

import com.med.model.CashBox;
import com.med.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends MongoRepository<CashBox, String>{

}
