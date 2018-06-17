package com.med.repository.income;

import com.med.model.balance.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface IncomeRepository extends MongoRepository<Income, String>{

}
