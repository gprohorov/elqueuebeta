package com.med.repository.doctor;

import com.med.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface IncomeRepository extends MongoRepository<Income, String>{

}
