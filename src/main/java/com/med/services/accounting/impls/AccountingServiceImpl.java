package com.med.services.accounting.impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.repository.accounting.AccountingRepository;
import com.med.services.accounting.interfaces.IAccountingService;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
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
    TalonServiceImpl talonService;

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

    public List<Accounting> saveAll(List<Accounting> accountings){
        return repository.saveAll(accountings);
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

    @Override
    public List<Accounting> getAllIncomesForPatientFromTo(String patientId, LocalDate start
                                                            , LocalDate finish)
    { return repository.findByPatientIdAndDateBetween(patientId,start,finish); }



    public Long getASum() {
        return repository.findAll().stream().mapToLong(Accounting::getSum).sum();
    }


    public List<Accounting> getAllForDate(LocalDate date){
        return repository.findByDate(date);
    }

    public Long getSumForDateTotal(LocalDate date){
        return this.getAllForDate(date).stream()
                .filter(accounting -> accounting.getSum()>0)
                .filter(accounting -> !accounting.getPayment().equals(PaymentType.DISCOUNT))
                .mapToLong(Accounting::getSum)
                .sum();
    }


    public Long getSumForDateCash(LocalDate date){
        return this.getAllForDate(date).stream()
                .filter(accounting -> accounting.getSum()>0)
                .filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
                .mapToLong(Accounting::getSum)
                .sum();
    }

    public AvailableexecutedPart getCurrentReport(){

        List<Accounting> today = this.getAllForDate(LocalDate.now());

        AvailableexecutedPart report = new AvailableexecutedPart();

        Long available = today.stream()
                .filter(accounting -> accounting.getSum()>0)
                .filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
                .mapToLong(Accounting::getSum)
                .sum();
        report.setAvailable(available);

        Long executed = today.stream()
                .filter(accounting -> accounting.getSum()<0)
                .mapToLong(Accounting::getSum)
                .sum();
        report.setExecuted(executed);

        List<Talon> talonsforToday = talonService.getTalonsForDate(LocalDate.now());
        int assigned = talonsforToday.size();
        int done = (int) talonsforToday.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .count();
        int part =  (100* done)/assigned;

        report.setPercentage(part);


        return report;
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





















