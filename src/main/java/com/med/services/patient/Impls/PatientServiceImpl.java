package com.med.services.patient.Impls;

import com.med.model.Patient;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.balance.Balance;
import com.med.model.balance.Course;
import com.med.model.balance.Income;
import com.med.model.balance.Payment;
import com.med.model.hotel.Hotel;
import com.med.repository.patient.PatientRepository;
import com.med.services.hotel.hotel.impls.HotelServiceImpl;
import com.med.services.income.impls.IncomeServiceImpl;
import com.med.services.patient.interfaces.IPatientService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    IncomeServiceImpl incomeService;

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

    public Income insertIncome(String patientId, int sum, Payment payment) {

        Income income = new Income(patientId, LocalDateTime.now(), sum, Payment.CASH);
        return incomeService.createIncome(income);

    }

    public Integer getBalance(String patientId){

        Integer debet = incomeService.getSumlForPatient(patientId);
        Integer kredit = talonService.getAllTalonsForPatient(patientId)
                .stream().mapToInt(Talon::getSum).sum();

        return debet + kredit;
    }


    //////////// ULTIMATE BALANCE  ///////////////
    public Balance getUltimateBalance(String patientId, LocalDate start, LocalDate finish){

        Balance balance = new Balance(patientId, start, finish);

        List<Income> incomes= incomeService
                .getAllIncomesForPatienetFromTo(patientId, start, finish);

        List<Talon> talons = talonService
                .getAllExecutedTalonsForPatientFromTo(patientId, start, finish);

        List<Hotel> hotels = hotelService
                .getAllForPatientFromTo(patientId, start, finish);

        talons.stream().collect(Collectors.groupingBy(Talon::getProcedure)).
        entrySet().stream().forEach(entry -> {
            long times  = entry.getValue().size();
            long zones  = entry.getValue().stream().mapToInt(Talon::getZones).sum();
            long sums = entry.getValue().stream().mapToInt(Talon::getSum).sum();
            Course course = new Course(entry.getKey(), times,  zones,   sums);
            balance.getCourses().add(course);
        });

        long sumForProcedures = balance.getCourses().stream().mapToLong(Course::getSumma).sum();
        balance.setSumForProcedures((int) sumForProcedures);


        long[] hotelBill = {0};
        hotels.stream().forEach(hotel -> {

            LocalDateTime tempDateTime = LocalDateTime.from( start );
            long hours = tempDateTime.until( finish, ChronoUnit.HOURS);
            int bill = (int) hours * hotel.getKoika().getPrice();
            hotelBill[0] += bill;
        });

        balance.setHotelSum( (int) (hotelBill[0]) * (-1));


        long payment = incomes.stream()
                .filter(income -> !income.getPayment().equals(Payment.DISCONT))
                .mapToLong(Income::getSum).sum();
        balance.setPayment( (int) payment);

        long discont = incomes.stream()
                .filter(income -> income.getPayment().equals(Payment.DISCONT))
                .mapToLong(Income::getSum).sum();
        balance.setDiscont((int) discont);


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
