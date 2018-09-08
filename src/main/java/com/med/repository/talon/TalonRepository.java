package com.med.repository.talon;

import com.med.model.Activity;
import com.med.model.Talon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface TalonRepository extends MongoRepository<Talon, String>{

    List<Talon> findByActivity(Activity activity);
    List<Talon> findByPatientId(String patientId);
    List<Talon> findByDate(LocalDate date);
    List<Talon> findByDateAndPatientId(LocalDate date, String patientId);
    List<Talon> findByDateBetween(LocalDate start, LocalDate finish);
    List<Talon> findByPatientIdAndDateGreaterThan(String patientId, LocalDate start);


}
