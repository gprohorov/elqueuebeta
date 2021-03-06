package com.med.repository;

import java.time.LocalDate;
import java.util.List;

import com.med.model.balance.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.balance.Accounting;

@Repository
public interface AccountingRepository extends MongoRepository<Accounting, String> {
	List<Accounting> findByPatientId(String patientId);
	List<Accounting> findByDate(LocalDate date);
	List<Accounting> findByPatientIdAndDateBetween(String patientId, LocalDate start, LocalDate finish);
	List<Accounting> findByDateBetween(LocalDate start, LocalDate finish);
	List<Accounting> findByDateAndPayment(LocalDate date, PaymentType type);
}