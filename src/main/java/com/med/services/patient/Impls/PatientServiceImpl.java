package com.med.services.patient.Impls;

import com.med.model.Patient;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.balance.Balance;
import com.med.model.balance.PaymentType;
import com.med.repository.patient.PatientRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.hotel.impls.HotelServiceImpl;
import com.med.services.patient.interfaces.IPatientService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service 
public class PatientServiceImpl implements IPatientService {

    @Autowired
    PatientRepository repository;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    AccountingServiceImpl accountingService;

    @Autowired
    HotelServiceImpl hotelService;

/*
    @Autowired
    TailServiceImpl tailService;

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init() {
        patients = dataStorage.getPatients();
        repository.saveAll(patients);
    }
*/

    @Override
    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Patient getPatient(String id) {
        Patient patient = repository.findById(id).orElse(null);
        return  patient;
    }

    public Patient getPatientWithTalons(String id) {
        List<Talon> talons = new ArrayList<>();
        Patient patient = repository.findById(id).orElse(null);
        talonService.getTalonsForToday().stream().filter(talon -> talon.getPatientId()
                .equals(id)).forEach(talon -> patient.getTalons().add(talon));
        return  patient;
    }

    @Override
    public Patient deletePatient(String id) {
        Patient patient = this.getPatient(id);
        repository.deleteById(id);
        return patient;
    }

    @Override
    public List<Patient> getAll(String fullName) {

        List<Patient> patients;

        if (fullName.equals("") || fullName == null) {
            patients = repository.findAll();
        } else {
            // TODO: make human-way query !!!
            // TODO: make sorting, paging, filtering
            patients = repository.findAll().stream()
                    .filter(patient -> patient.getPerson()
                    .getFullName().toLowerCase().contains(fullName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return patients;
    }

    @Override
    public List<Patient> getAllForToday() {

        List<Patient> patients = new ArrayList<>();
        List<Talon> talons =talonService.getTalonsForToday();

        talons.stream().collect(Collectors.groupingBy(Talon::getPatientId))
                .entrySet().stream().forEach(entry ->
               {
                   Patient patient = this.getPatient(entry.getKey());
                   patient.setTalons(entry.getValue());
                   patients.add(patient);
               }
       );
        return patients;
    }

    @Override
    public List<Patient> saveAll(List<Patient> patients) {
        return repository.saveAll(patients);
    }

    public Patient setStatus(String patientId, Status status) {

        Patient patient = this.getPatient(patientId);
        patient.setStatus(status);
        return repository.save(patient);

    }

    public Accounting insertIncome(String patientId, int sum, String desc, PaymentType payment) {

        Accounting accounting = new Accounting(patientId, LocalDateTime.now(), sum, PaymentType.CASH, desc);
        return accountingService.createAccounting(accounting);

    }

    public Integer getBalance(String patientId){

        Integer debet = accountingService.getSumlForPatient(patientId);
        Integer kredit = talonService.getAllTalonsForPatient(patientId)
                .stream().mapToInt(Talon::getSum).sum();

        return debet + kredit;
    }


    //////////// ULTIMATE BALANCE  ///////////////
    public Balance getUltimateBalance(String patientId, LocalDate start, LocalDate finish){

        Balance balance = new Balance(patientId, start, finish);

        List<Accounting> accountings= accountingService
                .getAllIncomesForPatienetFromTo(patientId, start, finish);

        List<Accounting> payments = accountings.stream()
                .filter(ac-> (ac.getTalonId()!=null && !ac.getPayment().equals(PaymentType.DISCOUNT)))
                .collect(Collectors.toList());

        List<Accounting> discounts = accountings.stream()
                .filter(ac-> ac.getPayment().equals(PaymentType.DISCOUNT))
                .collect(Collectors.toList());

        List<Accounting> bills = accountings.stream()
                .filter(ac-> ac.getPayment().equals(PaymentType.PROC))
                .collect(Collectors.toList());

        int payment = payments.stream().mapToInt(Accounting::getSum).sum();
        int discount = discounts.stream().mapToInt(Accounting::getSum).sum();
        int bill = bills.stream().mapToInt(Accounting::getSum).sum();
        int ultSum = accountings.stream().mapToInt(Accounting::getSum).sum();

      balance.setPayments(payments);
      balance.setDiscounts(discounts);
      balance.setBills(bills);

      balance.setPayment(payment);
      balance.setDiscount(discount);
      balance.setSumForProcedures(bill);

      balance.setSum(ultSum);

      Patient patient = repository.findById(patientId).orElse(null);
      if (patient!=null) patient.setBalance(ultSum);
      repository.save(patient);

        return balance;
    }

    public Balance getUltimateBalanceShort(String patientId, int days){

        return
                this.getUltimateBalance(patientId
                        ,LocalDate.now().minusDays(days+1)
                        ,LocalDate.now().plusDays(1));
    }

    public Balance getUltimateBalanceToday(String patientId){

        return this.getUltimateBalance(patientId
                        ,LocalDate.now().minusDays(1)
                        ,LocalDate.now().plusDays(1));
    }



}
