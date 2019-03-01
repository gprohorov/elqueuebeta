package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.med.model.Activity;
import com.med.model.Talon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.model.workday.WorkDay;
import com.med.repository.WorkDayRepository;

@Service
public class WorkDayService  {
	
    @Autowired
    WorkDayRepository repository;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    TalonService talonService;

    @Autowired
    PatientService patientService;

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
    @Scheduled(cron = "0 0 7 * * *")
    public void initWorkDay(){
        WorkDay workDay = new WorkDay(LocalDate.now());
        workDay.setSumAtStart(cashBoxService.getCashBox());
        int assigned = (int) talonService.getTalonsForToday().stream()
                .map(Talon::getPatientId).distinct().count();
        workDay.setAssignedPatients(assigned);
        this.create(workDay);
    }
    @Scheduled(cron = "0 0 10 * * *")
    public void setWorkDayStart(){
        WorkDay workDay = this.getWorkDay(LocalDate.now());
        LocalDateTime start = talonService.getTalonsForToday().stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .map(Talon::getStart).findFirst().orElse(null);
        workDay.setStart(start);
        this.update(workDay);
    }
}