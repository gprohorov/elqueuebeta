package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.SalaryDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalaryDTORepository extends MongoRepository<SalaryDTO, String> {
    List<SalaryDTO> findByClosed(LocalDateTime dateTime);


}