package com.med.services.accounting.impls;

import com.med.model.Patient;
import com.med.model.balance.Accounting;
import com.med.repository.accounting.AccountingRepository;
import com.med.services.accounting.interfaces.IAccountingService;
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
public class AccountingServiceImpl implements IAccountingService {

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    AccountingRepository repository;



    @Override
    public Accounting createAccounting(Accounting accounting) {
        repository.save(accounting);

        Patient patient = patientService.getPatient(accounting.getPatientId());
        patient.setBalance(patient.getBalance() + accounting.getSum());

        patientService.savePatient(patient);

        return accounting ;
    }

    @Override
    public Accounting getAccounting(String accountingId) {
        return repository.findById(accountingId).orElse(null);
    }

    @Override
    public List<Accounting> getAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }



    // TODO: Human query
    @Override
    public List<Accounting> getAllIncomesForPatienetFromTo(
              String patientId
            , LocalDate start
            , LocalDate finish) {

        List<Accounting> accountings = this.getAll().stream()
                .filter(accounting -> accounting.getPatientId().equals(patientId))
               .filter(accounting -> accounting.getDateTime().toLocalDate().isAfter(start.minusDays(0)))
               .filter(accounting -> accounting.getDateTime().toLocalDate().isBefore(finish.plusDays(0)))
                .collect(Collectors.toList());

        return accountings;

    }

    public Integer getASum() {
        return repository.findAll().stream().mapToInt(Accounting::getSum).sum();
    }


    public List<Accounting> getAllForDate(LocalDate date){
        return this.getAll().stream()
                .filter(accounting -> accounting.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }


    public Integer getSumForDate(LocalDate date){
        return this.getAll().stream()
                .filter(accounting -> accounting.getDateTime().toLocalDate().equals(date))
                .mapToInt(Accounting::getSum).sum();
    }

    public List<Accounting> getAllFrom(LocalDate date){
        return this.getAll().stream()
                .filter(accounting -> accounting.getDateTime().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public Integer getSumlFrom(LocalDate date){
        return this.getAll().stream()
                .filter(accounting -> accounting.getDateTime().toLocalDate().isAfter(date))
                .mapToInt(Accounting::getSum).sum();
    }

    public Integer getSumlForPatientFrom(String patientId, LocalDate date){
        return this.getAll().stream()
                .filter(accounting -> accounting.getPatientId().equals(patientId))
                .filter(accounting -> accounting.getDateTime().toLocalDate().isAfter(date))
                .mapToInt(Accounting::getSum).sum();
    }

    public Integer getSumlForPatient(String patientId){
        return this.getAll().stream()
                .filter(accounting -> accounting.getPatientId().equals(patientId))
                .mapToInt(Accounting::getSum).sum();
    }










}





















