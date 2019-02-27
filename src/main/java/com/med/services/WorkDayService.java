package com.med.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.workday.WorkDay;
import com.med.repository.WorkDayRepository;

@Service
public class WorkDayService  {
	
    @Autowired
    WorkDayRepository repository;

    public WorkDay create(WorkDay workDay) {
        return repository.save(workDay);
    }
    
    public WorkDay create(LocalDate date) {
        return repository.save(new WorkDay(date));
    }
    
    public WorkDay getWorkDay(String id) {
        return repository.findById(id).orElse(new WorkDay());
    }
    
    public WorkDay getWorkDay(LocalDate date) {
        return repository.findByDate(date).orElse(new WorkDay(date));
    }
    
    public WorkDay update(WorkDay workDay) {
        return repository.save(workDay);
    }
}