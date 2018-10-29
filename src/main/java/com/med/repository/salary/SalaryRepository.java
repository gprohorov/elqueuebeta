package com.med.repository.salary;

import com.med.model.Salary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 28.10.18.
 */
@Repository
public interface SalaryRepository extends MongoRepository<Salary, String> {
}
