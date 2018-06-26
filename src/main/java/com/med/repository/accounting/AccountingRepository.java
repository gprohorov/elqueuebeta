package com.med.repository.accounting;

import com.med.model.balance.Accounting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface AccountingRepository extends MongoRepository<Accounting, String>{

}
