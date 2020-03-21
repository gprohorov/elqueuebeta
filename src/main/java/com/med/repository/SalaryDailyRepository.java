package com.med.repository;

import com.med.model.SalaryDaily;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryDailyRepository extends MongoRepository<SalaryDaily, String> {
    List<SalaryDaily> findByDateBetweenAndDoctorId(LocalDate from, LocalDate to, int doctorId);
    List<SalaryDaily> findByDateBetween(LocalDate from, LocalDate to);
    List<SalaryDaily> findByDate(LocalDate date);
}
