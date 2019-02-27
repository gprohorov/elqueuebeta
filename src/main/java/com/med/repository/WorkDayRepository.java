package com.med.repository;

import com.med.model.workday.WorkDay;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by george on 27.02.19.
 */
public interface WorkDayRepository extends MongoRepository<WorkDay, String> {
    Optional<WorkDay> findByDate(LocalDate date);
}
