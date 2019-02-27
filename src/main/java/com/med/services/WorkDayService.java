package com.med.services;

import com.med.model.workday.WorkDay;
import com.med.repository.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * Created by george on 27.02.19.
 */
public class WorkDayService  {
    @Autowired
    WorkDayRepository repository;

    public WorkDay create(WorkDay workDay){
        return repository.save(workDay);
    }
    public WorkDay create(LocalDate date){
        return repository.save(new WorkDay(date));
    }
        public WorkDay getWorkDay(String id){
        return repository.findById(id).orElse(new WorkDay());
    }
    public WorkDay getWorkDay(LocalDate date){
        return repository.findByDate(date).orElse(new WorkDay(date));
    }
    public WorkDay update(WorkDay workDay){
        return repository.save(workDay);
    }





}
