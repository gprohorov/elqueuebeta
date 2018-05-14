package com.med.services.patient.Impls;

import com.med.model.Patient;
import com.med.model.Status;
import com.med.repository.patient.PatientRepository;
import com.med.services.patient.interfaces.IPatientService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        /*
        Patient patient = new Patient(person);
        if (!this.getAll().contains(patient)) {
            patient.setId(person.getId());
            patient.setLastActivity(LocalDateTime.now());
            patient.setStartActivity(LocalDateTime.now());
            patient.setStatus(Status.SOCIAL);
            patient.setActive(Activity.ACTIVE);
            Procedure registration = procedureService.getProcedure(1);
            registration.setExecuted(false);
            patient.setOneProcedureForTodayToExecute(registration);
            return this.createPatient(patient);
        }
        else return null;
        */
    }

    @Override
    public Patient getPatient(String id) {
        Patient patient = repository.findById(id).orElse(null);
        return  patient;
    }

    @Override
    public Patient deletePatient(String id) {
        Patient patient = this.getPatient(id);
        repository.deleteById(id);
        return patient;
    }

    @Override
    public List<Patient> getAll(String lastName) {

        List<Patient> patients;

        if (lastName.equals("") || lastName == null) {
            patients = repository.findAll();
        } else {
            // TODO: make human-way query !!!
            // TODO: make sorting, paging, filtering
            patients = repository.findAll().stream()
                    .filter(patient -> patient.getPerson()
                    .getLastName().equals(lastName))
                    .collect(Collectors.toList());
        }

        return patients;
    }

    public Patient updateStatus(String patientId, Status status) {
        /*
        Patient patient = this.getPatient(patientId);
        patient.setStatus(status);
        return repository.save(patient);
        */
        return null;
    }

////////////////////////////////////////////////////////////////////////

/*
    public Patient setStatus(int patientID, Status status){return null;}

    public Patient updateActivity(String patientId, Activity activity) {
        Patient patient = this.getPatient(patientId);
    //    patient.setActive(activity);
        patient.setLastActivity(LocalDateTime.now());
        this.savePatient(patient);
        return patient;
    }

    public Patient updateBalance(String patientId, int balance) {
        Patient patient = this.getPatient(patientId);
    //    patient.setBalance(balance);
        this.savePatient(patient);
        return patient;
    }
    public Patient updateReckoning(String patientId, Reckoning reckoning) {
        Patient patient = this.getPatient(patientId);
    //    patient.setReckoning(reckoning);
        this.savePatient(patient);
        return patient;
    }

    // get a sorted list of patients to the specified procedure
    public List<Patient> getQueueToProcedure(int procedureId) {

        Procedure procedure = procedureService.getProcedure(procedureId);

        List<Patient> queue = new ArrayList<>();
        List<Patient> all = this.getAll().stream()
                .filter(pat -> pat.getActive().equals(Activity.ACTIVE))
                .collect(Collectors.toList());

        boolean swtch1 = false;
        boolean swtch2 = false;


        for(Patient patient: all){
            if (patient.getProceduresForToday().contains(procedure)){
                int index = patient.getProceduresForToday().indexOf(procedure);
                if (patient.getProceduresForToday().get(index)
                        .isExecuted() == false
                        ){
                    queue.add(patient);
                }
            }



        }

        return this.getAll().stream()
                .filter(pat -> pat.getActive().equals(Activity.ACTIVE))
                .filter(pat -> pat.getProceduresForToday()
                        .contains(procedure)).collect(Collectors.toList());*//*

        return queue;
    }

    public Patient setTherapy(int patientId, int therapyId) {
        Patient patient = this.getPatient(patientId);
        Therapy therapy = therapyService.getTherapy(therapyId);
        patient.setTherapy(therapy);
        this.updatePatient(patient);
        return patient;
    }

    ////////////////////////  TAILS   ycmape/\o    //////////////////////////
    public List<Tail> getTails(){

        List<Tail> tails = new ArrayList<>();

        List<Tail> tails = dataStorage.getTails();

        for (Procedure procedure: procedureService.getAll()){
           tails.add(new Tail(procedure.getId(), procedure.getName()));
        }



        for (Tail tail:tails){
            for (Patient patient:this.getActivePatients()){
                if (patient.getProceduresForToday().stream()
                        .anyMatch(el->el.getId()==tail.getProcedureId()&&!el.isExecuted())
                        ){
                    tail.addPatient(patient);
                }
            }
        }

   for (Tail tail:tails) {

            if (tail.getPatients().stream()
                    .anyMatch(patient -> patient.getActive().equals(Activity.ON_PROCEDURE)
                    )){
                tail.setVacancies(0);
            }else {
                tail.setVacancies(0);
            }
        }

        return tails;
    }

    //  procedure starts => patient gets status ON_PROCEDURE
    //                  tail gets decremention of a number of responsible  doctors
    public Patient startProcedure(int patientId, int procedureId) {

        Talon talon = talonService.getTalonByPatientAndProcedure(patientId
                , procedureId);
        talon.setStart(LocalDateTime.now());
        talonService.updateTalon(talon);
        Patient patient = this.getPatient(patientId);
        patient.setActive(Activity.ON_PROCEDURE);
        patient.setLastActivity(LocalDateTime.now());
        repository.save(patient);

       Tail tail = tailService.getTail(procedureId);
       tail.setVacancies(0);
       tail.setVacant(false);
       tail.setPatientOnProcedure(patient);

        return patient;
    }

    // patient has executed a procedure => we mark the procedure  as TRUE in his list of  assigned procedures for tody
    //                                      patient gets a status ACTIVE
    //                                     patient gets the time of lastActivity = now
    //                                     tail gets incremention of a number of responsible  doctors
    public Patient executeProcedure(int patientId, int procedureId) {

        Tail tail = tailService.getTail(procedureId);
        Patient patient = tail.getPatientOnProcedure();
        Procedure procedure = procedureService.getProcedure(procedureId);


        patient.setLastActivity(LocalDateTime.now());

        patient.setActive(Activity.ACTIVE);
        repository.save(patient);


        Talon talon = talonService.getTalonByPatientAndProcedure(patientId
                , procedureId);

        talonService.executeTalon(talon.getId(), userService.getCurrentUserInfo().getId());


        tail.setVacancies( 1);
        tail.setVacant(true);
        tail.setPatientOnProcedure(null);

        return patient;
    }

    // patient has canceled a procedure => we DON'T mark the procedure  as TRUE in his list of  assigned procedures for tody
    //                                      patient gets a status ACTIVE
    //                                     tail gets incremention of a number of responsible  doctors


    public Patient cancelProcedure(int patientId, int procedureId) {
        Patient patient = this.getPatient(patientId);

        Procedure procedure = patient.getProceduresForToday().stream()
                .filter(pr -> pr.getId()== procedureId).findAny().get();

        patient.setActive(Activity.ACTIVE);
        patient.setLastActivity(LocalDateTime.now());
        repository.save(patient);

        Tail tail = tailService.getTailByProcedure(procedureId);
        tail.setVacancies( 1);
        tail.setVacant(true);
        tail.setPatientOnProcedure(null);

        return patient;
    }


    public Patient getFirstFromTail(int procedureId) {
        return tailService.getAll().stream()
                .filter(tl -> tl.getProcedureId() == procedureId)
                .findAny().get().getPatients().stream()
                .filter(el ->el.getActive().equals(Activity.ACTIVE))
                .findFirst().get();
    }

    /////////////////////// TALONS
    Patient getTalons(int patientId){
        Patient patient = this.getPatient(patientId);
        return patient;
    }
*/
}
