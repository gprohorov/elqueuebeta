package com.med.repository.accrual;

import com.med.model.Accrual;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 18.10.18.
 */
@Repository
public interface AccrualRepository extends MongoRepository<Accrual, String> {
}
