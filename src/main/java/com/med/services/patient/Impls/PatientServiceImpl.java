package com.med.services.patient.Impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.repository.accounting.AccountingRepository;
import com.med.repository.patient.PatientRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.interfaces.IPatientService;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    AccountingRepository accountingRepository;

    @Autowired
    RecordServiceImpl recordService;

    @Autowired
    TherapyServiceImpl therapyService;

    @Autowired
    ProcedureServiceImpl procedureService;

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

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

    public void deleteAll(List<Patient> patients){
        repository.deleteAll(patients);
    }

    @Override
    public Patient deletePatient(String id) {
        Patient patient = this.getPatient(id);
        List<Talon>  talons = talonService.getAllTalonsForPatient(id);
        talonService.deleteAll(talons);
        repository.deleteById(id);
        return patient;
    }



    public Patient registratePatient(PatientRegDTO data) {

        Patient patient = new Patient(data.getPerson());
        patient.setRegistration(LocalDateTime.now());
        if (data.isActivate()){
            patient.setStartActivity(LocalDateTime.now());
            patient.setLastActivity(LocalDateTime.now());
        }
        repository.save(patient);

        int appointment = (data.getAppointed()!=0)?data.getAppointed():LocalDateTime.now().getHour();
        int prId = (data.getProcedureId()!=0) ? data.getProcedureId() : 2;
        boolean actv=false;

        if (data.isActivate()&&LocalDate.parse(data.getDate()).equals(LocalDate.now())){actv=true;}

        talonService.createActiveTalon( patient.getId(), prId, LocalDate.parse(data.getDate())
                    , appointment, actv);

        return patient;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Patient getPatient(String patientId) {

        Patient patient = repository.findById(patientId).orElse(null);
        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        if (therapy!=null)patient.setTherapy(therapy);
        return  patient;
    }

    public Patient getPatientWithTalons(String id) {
        List<Talon> talons = new ArrayList<>();
        Patient patient = repository.findById(id).orElse(null);
        talonService.getTalonsForToday().stream().filter(talon -> talon.getPatientId()
                .equals(id)).forEach(talon -> patient.getTalons().add(talon));
        return  patient;
    }

    public Patient getPatientWithOneTalon(String patientId, int procedureId) {
        Patient patient = repository.findById(patientId).get();
        patient.getTalons().add(
            talonService.getTalonForTodayForPatientForProcedure(patientId, procedureId)
        );
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
        long start = System.currentTimeMillis();
        List<Patient> patients = new ArrayList<>();
        List<Talon> talons = talonService.getTalonsForToday();
     //   logger.info(">>>>  talons for today --- >>>>>>>> " + (System.currentTimeMillis() - start));


        talons.stream().collect(Collectors.groupingBy(Talon::getPatientId))
                .entrySet().stream().forEach(entry ->
               {
                   Patient patient = this.getPatient(entry.getKey());
                   patient.setTalons(entry.getValue());
                   patient.setActivity(patient.calcActivity());
                   patient.setTherapy(null);
                   if (patient.getDelta()==null) {
                       patient.setStartActivity(LocalDateTime.now());
                       patient.setLastActivity (LocalDateTime.now());
                   }

/*

                    if (patient.getDelta()> 150){
                       patient.setActivity(Activity.NON_ACTIVE);
                       patient.setStartActivity(null);
                       patient.setLastActivity(null);
                    }
*/


                   patients.add(patient);
               }
       );

       // logger.info(">>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + (System.currentTimeMillis() - start));
        logger.info(">>>>  patients for today --- >>>>>>>> " + (System.currentTimeMillis() - start));


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
    public List<Accounting> getUltimateBalance(String patientId, LocalDate start, LocalDate finish){


   //     return accountingRepository.findByPatientId(patientId);
        return accountingRepository.findByPatientIdAndDateBetween(patientId,start,finish);
    }

    public List<Accounting> getUltimateBalanceShort(String patientId, int days) {
        return this.getUltimateBalance(patientId, LocalDate.now().minusDays(days+1), LocalDate.now().plusDays(1));
    }

    public List<Accounting> getUltimateBalanceToday(String patientId) {
        LocalDate start = therapyService.findTheLastTherapy(patientId).getStart().toLocalDate();

        return this.getUltimateBalance(patientId, start.minusDays(1), LocalDate.now().plusDays(1));
    }

     public List<Accounting> getBalanceForCurrentTherapy(String patientId){
        LocalDate start = therapyService.findTheLastTherapy(patientId).getStart().toLocalDate();
        return  this.getUltimateBalance(patientId,start,LocalDate.now());
    }

    public List<Patient> getDebetors(){
         return repository.findByBalanceLessThan(0);
    }



  //    !!!  PATIENTS WITH TALONS  FOR DATE !!!
    public List<Patient> getAllForDate(LocalDate date) {

        long start = System.currentTimeMillis();

        List<Patient> patients = new ArrayList<>();
        List<Talon> talons = talonService.getTalonsForDate(date);

        talons.stream().collect(Collectors.groupingBy(Talon::getPatientId))
                .entrySet().stream().forEach(entry ->
                {
                    Patient patient = this.getPatient(entry.getKey());
                    if (patient!=null) {  /* kostil */
                        patient.setTalons(entry.getValue());
                        patient.setActivity(patient.calcActivity());

                        if (patient.getDelta() != null && patient.getDelta() > 300) {
                            //    logger.info(patient.getPerson().getFullName());
                            patient.setStartActivity(LocalDateTime.now());
                            patient.setLastActivity(LocalDateTime.now());
                            repository.save(patient);
                        }
                        patient.setTherapy(null);
                        patients.add(patient);
                    }
                }
        );
            String s = (date.equals(LocalDate.now())) ? "toaday" : date.toString();
            logger.info(" patients for " + s + "  -  " + (System.currentTimeMillis() - start)+"ms");

        return patients;
    }

    // talonService.createTalonsForPatientToDate(patientId, LocalDate.parse(date), time)

    ///////////////  assign to date //////////////////////////
    //********************************************************
    public List<Talon> assignPatientToDate(String patientId, LocalDate date, int time){

        Patient patient = this.patientBye(patientId);
        this.repository.save(patient);

        List<Talon> talons = talonService.createTalonsForPatientToDate(patientId, date, time);

        return talons;

    }


    public Patient patientBye(String patientId){
        logger.info( " patid  "+ patientId);
        Patient patient = this.getPatientWithTalons(patientId);
        logger.info(patient.toString());
        List<Talon> talons =  patient.getTalons();

        talons.stream().forEach(talon -> {

            if (!talon.getActivity().equals(Activity.EXECUTED)){
                talon.setActivity(Activity.CANCELED);
                logger.info("procedure " + talon.getProcedure().getName() + " cancelled by BYE");
            }
                }
        );
        talonService.saveTalons(talons);

        patient.setLastActivity(null);
        patient.setStartActivity(null);

        return repository.save(patient) ;
    }



}
