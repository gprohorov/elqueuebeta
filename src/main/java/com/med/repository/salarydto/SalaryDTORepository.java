package com.med.repository.salarydto;

import com.med.model.Salary;
import com.med.model.SalaryDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 28.10.18.
 */
@Repository
public interface SalaryDTORepository extends MongoRepository<SalaryDTO, String> {
}
