package com.med.services.talon.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.card.impls.CardServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.interfaces.ITalonService;
import com.med.services.therapy.impls.TherapyServiceImpl;
import com.med.services.workplace.impls.WorkPlaceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TalonServiceImpl implements ITalonService {

    @Autowired
    TalonRepository repository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    TherapyServiceImpl therapyService;

    @Autowired
    WorkPlaceServiceImpl workPlaceService;

    @Autowired
    AccountingServiceImpl accountingService;

    private static final Logger logger = LoggerFactory.getLogger(TalonServiceImpl.class);

    @PostConstruct
    void init(){}

    @Override
    public Talon createTalon(String patientId, int procedureId, int days) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure,days);

        return repository.save(talon);
    }

    public Talon saveTalon(Talon talon){
        return repository.save(talon);
    }

    @Override
    public Talon createTalon(String patientId, int procedureId, LocalDate date) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure, date);
        talon.setAppointed(10);

        return repository.save(talon);

    }    public Talon createTalon(String patientId, int procedureId, LocalDate date, int time) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure, date);

        return repository.save(talon);
    }
    
    public Talon createActiveTalon(String patientId, int procedureId, LocalDate date, int time, Boolean activate) {
    	
    	Procedure procedure = procedureService.getProcedure(procedureId);
    	Talon talon = new Talon(patientId, procedure, date, time);

    	if (activate) {
    		talon.setActivity(Activity.ACTIVE);
    		Patient patient = patientService.getPatient(patientId);
    		if (patient.getStartActivity()==null){
    		patient.setStartActivity(LocalDateTime.now());
    		patient.setLastActivity(LocalDateTime.now());
    		patientService.savePatient(patient);}
    	}
       // System.out.println(" patid " + talon.getPatientId());
        return repository.save(talon);
    }

    @Override
    public List<Talon> getAll() {
        return repository.findAll();
    }

    @Override
    public Talon getTalon(String id) {

        return repository.findById(id).orElse(null);
    }

    public Talon getTalonByProcedure(String patientId, int procedureId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getProcedure().getId()==procedureId)
                .filter(tal -> tal.getPatientId()==patientId)
                .filter(tal -> tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public List<Talon> findAll() {
        return repository.findAll();
    }

    public Talon getTalonByPatient(String patientId,  Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getPatientId().equals(patientId))
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public Talon getTalonByDoctor(String patientId, int doctorId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getDoctor().getId()==doctorId)
                .filter(tal->tal.getPatientId()==patientId)
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    @Override
    public List<Talon> getTalonsForToday() {
        return repository.findByDate(LocalDate.now());
    }





    public Talon getTalonForTodayForPatientForProcedure(String patientId, int procedureId) {
        return repository.findByDateAndPatientId(LocalDate.now(), patientId)
            .stream().filter(talon ->
                ( talon.getActivity().equals(Activity.ACTIVE)
                    || talon.getActivity().equals(Activity.ON_PROCEDURE)
                )
                && talon.getProcedure().getId() == procedureId
            )
            .findFirst().get();
    }

    public List<Talon> getAllTalonsForPatient(String patientId) {
        return repository.findByPatientId(patientId);
    }

    @Override
    public List<Talon> getTalonsForDate(LocalDate date) {
        return repository.findByDate(date);
    }


    public List<Talon> getAllTallonsBetween(LocalDate start, LocalDate finish){
        return repository.findByDateBetween(start.minusDays(1), finish.plusDays(1));
    }


    @Override
    public Talon setActivity(String talonId, Activity activity) {
        List<Activity> activities = new ArrayList<>(
            Arrays.asList(
                Activity.EXECUTED,
                Activity.EXPIRED,
                Activity.CANCELED,
                Activity.ON_PROCEDURE
            )
        );
        Talon talon = this.getTalon(talonId);
        if(talon != null && !activities.contains(talon.getActivity())){
            Activity former = talon.getActivity();
            talon.setActivity(activity);
            if (former==Activity.NON_ACTIVE && activity==Activity.ACTIVE) {
                Patient patient = patientService.getPatient(talon.getPatientId());
                LocalDateTime start = patient.getStartActivity();
                if (start == null || start.toLocalDate().isBefore(LocalDate.now())) {
                    patient.setStartActivity(LocalDateTime.now());
                    patient.setLastActivity(LocalDateTime.now());
                }
                patientService.savePatient(patient);
            }
        }
        return repository.save(talon);
    }


    public void setAllActivity(String patientId, Activity activity) {

        LocalDateTime start = LocalDateTime.now();
        List<Talon> talons = this.getTalonsForToday().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
                .collect(Collectors.toList());

      if (activity.equals(Activity.ACTIVE)){
          List<Integer> free = procedureService.getFreeProcedures();

          talons.stream().forEach(talon ->{

              if (free.contains(Integer.valueOf(talon.getProcedureId())))
              this.setActivity(talon.getId(), Activity.ACTIVE);
          }
          );
      }else talons.stream().forEach(talon -> this.setActivity(talon.getId(), activity));

      if (activity.equals(Activity.NON_ACTIVE)){
          Patient patient = patientService.getPatient(patientId);
          patient.setStartActivity(null);
          patient.setLastActivity(null);
          patientService.savePatient(patient);
      }

      logger.info(">>>> activate talons >>>>>>    " + ChronoUnit.MILLIS.between( start, LocalDateTime.now()) + "ms");
    }





    public List<Patient> toPatientList(List<Talon> talons){
        List<Patient> patients = new ArrayList<>();

        talons.stream().forEach(talon -> {
            Patient patient = patientService.getPatient(talon.getPatientId());
         //   System.out.println(talon.toString());
            patient.setActivity(talon.getActivity());
            patient.setTherapy(null);
            patient.setTalon(talon);
            patients.add(patient);
        });

        return patients;
    }

    public List<Talon> saveTalons(List<Talon> talons) {
        return repository.saveAll(talons);
    }


    public List<Talon> getAllExecutedTalonsForPatientFromTo(
            String patientId
            , LocalDate start
            , LocalDate finish){

        return getAll().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
                .filter(talon -> talon.getDate().isAfter(start.minusDays(0)))
                .filter(talon -> talon.getDate().isBefore(finish.plusDays(1)))
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());
    }


    public void deleteAll(List<Talon> talons){
        repository.deleteAll(talons);
    }





    public List<Procedure> getFilledProcedures() {

        List<Procedure> procedures =procedureService.getAll();
        procedures.stream().forEach(procedure -> {

            int nmbr = (int) this.getTalonsForToday().stream()
                    .filter(talon -> talon.getProcedure().equals(procedure)).count();
            procedure.setToday(nmbr);
        });

        return procedures;
    }

    public Talon setOutOfTurn(String talonId, boolean out) {
        Talon talon = this.getTalon(talonId);
        if (talon.getActivity().equals(Activity.ON_PROCEDURE)) {
            return null;
        }
        if (out) {
            talon.setActivity(Activity.INVITED);
        } else {
            talon.setActivity(Activity.ACTIVE);
        }
        return repository.save(talon) ;
    }

///////////  without appointment 9.00 by default
    public List<Talon> createTalonsForPatientToDate(String patientId, LocalDate date) {
       Patient patient = patientService.getPatientWithTalons(patientId);
        List<Talon> talons = new ArrayList<>();

        this.removeTalonsForPatientToDate(patientId,date);

        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        if (therapy!=null){
         talons = therapyService.generateTalonsByTherapyToDate(therapy,date);
        }else {
            Procedure diagnostics = procedureService.getProcedure(2);
            talons.add(new Talon(patientId, diagnostics, date));
        }
    return repository.saveAll(talons);
    }


///////////  with appointment
    public List<Talon> createTalonsForPatientToDate(String patientId, LocalDate date, int time){
       Patient patient = patientService.getPatientWithTalons(patientId);
        List<Talon> talons = new ArrayList<>();

        this.removeTalonsForPatientToDate(patientId,date);

        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        if (therapy!=null){
         talons = therapyService.generateTalonsByTherapyToDate(therapy,date);
         talons.stream().forEach(talon -> {
                 talon.setAppointed(time);
            if(date.equals(LocalDate.now())) {
                patient.setStartActivity(LocalDateTime.now());
                patient.setLastActivity(LocalDateTime.now());
                patientService.savePatient(patient);
            }
         });
         
            // recall the udarno-wave therapy last time date -  KOSTIL
         Talon talon = talons.stream().filter(tl->tl.getProcedure().getId()==9)
                 .findFirst().orElse(null);
             if (talon!=null){
             LastTalonInfo last = this.getLastExecuted(talon);
             talon.setLast(last);
            }
        }else {
            Procedure diagnostics = procedureService.getProcedure(2);
            talons.add(new Talon(patientId, diagnostics, date, time));
        }
    return repository.saveAll(talons);
    }


    /////////   remove all talons for date in order to create new ones
    public List<Talon> removeTalonsForPatientToDate(String patientId, LocalDate date) {
        List<Talon> talons = repository.findByDateAndPatientId(date, patientId);
        repository.deleteAll(talons);
    return talons;
    }


    public Talon quickExecute(String talonId) {

        Talon talon = repository.findById(talonId).orElse(null);
        Procedure procedure = talon.getProcedure();

        if (procedure.getId()!=3) return null;

        Patient patient = patientService.getPatient(talon.getPatientId());
        patient.setLastActivity(LocalDateTime.now());

        Doctor doctor = doctorService.getAll().get(0);

        talon.setActivity(Activity.EXECUTED);
        talon.setStart(LocalDateTime.now());
        talon.setExecutionTime(LocalDateTime.now());
        talon.setZones(1);
        talon.setDoctor(doctor);

        String desc = "Адміністратор, "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - процедуру завершено.<br/><br/>";
        talon.setDesc(talon.getDesc() + desc);
        talon.setStatus(patient.getStatus());

        int price = this.getPrice(patient, procedure.getId());

        int sum = procedure.isZoned() ? price * talon.getZones() : price;
        talon.setSum(sum);

        // create accounting
        String descr = procedure.getName() + " x" + talon.getZones();
        Accounting accounting = new Accounting(doctor.getId()
                , patient.getId()
                , LocalDateTime.now()
                , talon.getId()
                , (-1* sum)
                , PaymentType.PROC
                , descr);
        accountingService.createAccounting(accounting);


        return repository.save(talon);
    }


    private int getPrice(Patient patient, int procedureId){
        int price ;
        Procedure procedure = procedureService.getProcedure(procedureId);
        switch (patient.getStatus()){

            case SOCIAL: price= procedure.getSOCIAL();
                break;

            case VIP: price= procedure.getVIP();
                break;

            case ALL_INCLUSIVE: price= procedure.getALL_INCLUSIVE();
                break;

            case BUSINESS: price= procedure.getBUSINESS();
                break;

            case FOREIGN:    price=procedure.getFOREIGN();
                break;

            default:price=procedure.getSOCIAL();
                break;
        }
        return price;
    }

    private LastTalonInfo getLastExecuted(Talon talon){
        Talon tln = repository.findByPatientIdAndDateGreaterThan(talon.getPatientId(),
                       LocalDate.now().minusDays(10)).stream()
                        .filter(tal -> tal.getProcedure().getId()==9 )
                        .filter(tal -> tal.getActivity().equals(Activity.EXECUTED))
                        .sorted(Comparator.comparing(Talon::getDate).reversed())
                        .findFirst().orElse(null);

        if (tln == null) {
            return null;
        }

        return new LastTalonInfo(tln.getDate(), tln.getZones(), tln.getDoctor());
    }

}
