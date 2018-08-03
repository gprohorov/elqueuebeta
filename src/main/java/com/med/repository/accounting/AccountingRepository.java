package com.med.repository.accounting;

import com.med.model.balance.Accounting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface AccountingRepository extends MongoRepository<Accounting, String>{


	List<Accounting> findByPatientId(String patientId);
	List<Accounting> findByDate(LocalDate date);
	List<Accounting> findByPatientIdAndDateBetween(String patientId, LocalDate start, LocalDate finish);


	
}
