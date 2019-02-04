package com.med.repository;

import com.med.model.Salary;
import com.med.model.SalaryDaily;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDailyRepository extends MongoRepository<SalaryDaily, String> {}