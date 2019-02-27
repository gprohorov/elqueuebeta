package com.med.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.med.model.workday.WorkDay;

public interface WorkDayRepository extends MongoRepository<WorkDay, String> {
    Optional<WorkDay> findByDate(LocalDate date);
}
