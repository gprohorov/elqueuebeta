package com.med.repository;

import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
import com.med.model.workday.WorkDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface WorkWeekRepository extends MongoRepository<GeneralStatisticsDTOWeekly, String> {

}
