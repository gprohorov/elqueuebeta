package com.med.services.income.impls;

import com.med.model.Income;
import com.med.repository.income.IncomeRepository;
import com.med.services.income.interfaces.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 20.05.18.
 */
@Service
public class IncomeServiceImpl implements IIncomeService {

    @Autowired
    IncomeRepository repository;



    @Override
    public Income createIncome(Income income) {
        return repository.save(income);
    }

    @Override
    public Income getIncome(String incomeId) {
        return repository.findById(incomeId).orElse(null);
    }

    @Override
    public List<Income> getAll() {
        return repository.findAll();
    }

      public Integer getASum() {
        return repository.findAll().stream().mapToInt(Income::getSum).sum();
    }




    public List<Income> getAllForDate(LocalDate date){
        return this.getAll().stream()
                .filter(income -> income.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }


    public Integer getSumForDate(LocalDate date){
        return this.getAll().stream()
                .filter(income -> income.getDateTime().toLocalDate().equals(date))
                .mapToInt(Income::getSum).sum();
    }

    public List<Income> getAllFrom(LocalDate date){
        return this.getAll().stream()
                .filter(income -> income.getDateTime().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public Integer getSumlFrom(LocalDate date){
        return this.getAll().stream()
                .filter(income -> income.getDateTime().toLocalDate().isAfter(date))
                .mapToInt(Income::getSum).sum();
    }

    public Integer getSumlForPatientFrom(String patientId, LocalDate date){
        return this.getAll().stream()
                .filter(income -> income.getPatient().equals(patientId))
                .filter(income -> income.getDateTime().toLocalDate().isAfter(date))
                .mapToInt(Income::getSum).sum();
    }

    public Integer getSumlForPatient(String patientId){
        return this.getAll().stream()
                .filter(income -> income.getPatient().equals(patientId))
                .mapToInt(Income::getSum).sum();
    }










}





















