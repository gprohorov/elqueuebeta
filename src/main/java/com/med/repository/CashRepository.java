package com.med.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.CashBox;
import com.med.model.CashType;

@Repository
public interface CashRepository extends MongoRepository<CashBox, String> {
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#_supported_query_keywords
	List<CashBox> findByItemId(String id);
	List<CashBox> findAllByDateTimeIsBetweenAndTypeIsNot(LocalDate from, LocalDate to, CashType type);
	List<CashBox> findAllByDateTimeAfter(LocalDateTime dateTime);

}