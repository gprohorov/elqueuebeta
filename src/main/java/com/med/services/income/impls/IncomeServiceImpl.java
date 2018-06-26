package com.med.services.income.impls;

import com.med.model.balance.Income;
import com.med.model.Patient;
import com.med.repository.income.IncomeRepository;
import com.med.services.income.interfaces.IIncomeService;
import com.med.services.patient.Impls.PatientServiceImpl;
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
    PatientServiceImpl patientService;

    @Autowired
    IncomeRepository repository;



    @Override
    public Income createIncome(Income income) {
        repository.save(income);
        System.out.println("--------------------------------------------------");
        System.out.println(income);
        Patient patient = patientService.getPatient(income.getPatientId());

        patient.setBalance(patient.getBalance() + income.getSum());
        patientService.savePatient(patient);

        return income ;
    }

    @Override
    public Income getIncome(String incomeId) {
        return repository.findById(incomeId).orElse(null);
    }

    @Override
    public List<Income> getAll() {
        return repository.findAll();
    }



    // TODO: Human query
    @Override
    public List<Income> getAllIncomesForPatienetFromTo(
              String patientId
            , LocalDate start
            , LocalDate finish) {

        List<Income> incomes = this.getAll().stream()
                .filter(income -> income.getPatientId().equals(patientId))
               .filter(income -> income.getDateTime().toLocalDate().isAfter(start.minusDays(0)))
               .filter(income -> income.getDateTime().toLocalDate().isBefore(finish.plusDays(0)))
                .collect(Collectors.toList());

        return incomes;

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
                .filter(income -> income.getPatientId().equals(patientId))
                .filter(income -> income.getDateTime().toLocalDate().isAfter(date))
                .mapToInt(Income::getSum).sum();
    }

    public Integer getSumlForPatient(String patientId){
        return this.getAll().stream()
                .filter(income -> income.getPatientId().equals(patientId))
                .mapToInt(Income::getSum).sum();
    }










}





















