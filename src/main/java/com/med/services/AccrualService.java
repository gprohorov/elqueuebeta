package com.med.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Accrual;
import com.med.repository.AccrualRepository;

@Service
public class AccrualService {

    @Autowired
    AccrualRepository repository;

    public Accrual createAccrual(Accrual accrual) {
        return repository.save(accrual);
    }

    public Accrual getAccrual(String accrualId) {
        return repository.findById(accrualId).orElse(null);
    }

    public List<Accrual> getAll() {
        return repository.findAll();
    }
}