package com.med.services.patient.Impls;

import com.med.model.*;
import com.med.repository.patient.PatientRepository;
import com.med.services.patient.interfaces.IPatientsService;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service 
public class PatientServiceMongoImpl implements IPatientsService {

    private static List<Patient> patients = new ArrayList<>();

    @Autowired
    PatientRepository repository;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    TalonServiceImpl talonService;


/*
    @Autowired
    DataStorage dataStorage;


    @PostConstruct
    void init() {
        patients = dataStorage.getPatients();
        repository.saveAll(patients);
    }
*/

    @Override
    public Patient createPatient(Person person) {
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
    }

    @Override
    public Patient createPatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return repository.insert(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Patient getPatient(int id) {
        Patient patient =repository.findById(id).orElse(null);
        patient.setDelta(ChronoUnit.MINUTES.between(
                patient.getLastActivity(), LocalDateTime.now()));
        return  patient;
    }

    @Override
    public Patient deletePatient(int id) {
         Patient patient = this.getPatient(id);
         repository.deleteById(id);
        return patient;
    }

    @Override
    public List<Patient> getByLastName(String lastName) {
        return this.getAll().stream()
                .filter(patient -> patient.getPerson().getLastName().equals(lastName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Patient> getAll() {

        List<Patient> patients = repository.findAll();

        for (Patient patient:patients) {
            patient.setDelta(ChronoUnit.MINUTES.between(
                    patient.getLastActivity(), LocalDateTime.now()
            ));
        }

        return patients.stream().sorted().collect(Collectors.toList());


    }

    @Override
    public List<Patient> insertAppointedForToday() {
        return null;
    }

    @Override
    public Patient updateStatus(int patientId, Status status) {
        Patient patient = this.getPatient(patientId);
        patient.setStatus(status);
        return repository.save(patient);
    }

////////////////////////////////////////////////////////////////////////


    public Patient setStatus(int patientID, Status status){return null;}





    public Patient updateActivity(int patientId, Activity activity) {
        Patient patient = this.getPatient(patientId);
        patient.setActive(activity);
        patient.setLastActivity(LocalDateTime.now());
        this.updatePatient(patient);
        return patient;
    }


    public Patient updateBalance(int patientId, int balance) {
        Patient patient = this.getPatient(patientId);
        patient.setBalance(balance);
        this.updatePatient(patient);
        return patient;
    }



    public Patient updateReckoning(int patientId, Reckoning reckoning) {
        Patient patient = this.getPatient(patientId);
        patient.setReckoning(reckoning);
        this.updatePatient(patient);
        return patient;

    }

    // get progress in crowd :  ratio of executed procedures to assigned ones
    public Double getProgress(int patientId) {
        Double progress = 0.0;
/*        Patient patient = this.getPatient(patientId);
        HashMap<Procedure,Boolean> map = patient.getAssignedProcedures();
        if (!map.isEmpty()){
           int nominator = (int) map.entrySet().stream()
                   .filter(entry->entry.getValue().equals(true)).count();
           int denominator = map.size();
           progress = Double.valueOf(nominator) /denominator;
        }*/
        return progress;
    }

/*


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
*/
/*
        return this.getAll().stream()
                .filter(pat -> pat.getActive().equals(Activity.ACTIVE))
                .filter(pat -> pat.getProceduresForToday()
                        .contains(procedure)).collect(Collectors.toList());*//*

        return queue;
    }


*/

/*

    public Patient setTherapy(int patientId, int therapyId) {
        Patient patient = this.getPatient(patientId);
        Therapy therapy = therapyService.getTherapy(therapyId);
        patient.setTherapy(therapy);
        this.updatePatient(patient);
        return patient;
    }
*/

////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    ////////////////// HERE   /////////////////////////////////////
    /////////////////////////////////////////////////////////

    public List<Patient> getActivePatients(){
        return this.getAll().stream()
                .filter(el->el.getActive().equals(Activity.ACTIVE)
                 //        ||
                 //       el.getActive().equals(Activity.ON_PROCEDURE)
                )
                .collect(Collectors.toList());
    }



/*


    ////////////////////////  TAILS   ycmape/\o    //////////////////////////
    public List<Tail> getTails(){

        List<Tail> tails = new ArrayList<>();

 */
/*       List<Tail> tails = dataStorage.getTails();

        for (Procedure procedure: procedureService.getAll()){
           tails.add(new Tail(procedure.getId(), procedure.getName()));
        }*//*



        for (Tail tail:tails){
            for (Patient patient:this.getActivePatients()){
                if (patient.getProceduresForToday().stream()
                        .anyMatch(el->el.getId()==tail.getProcedureId()&&!el.isExecuted())
                        ){
                    tail.addPatient(patient);
                }
            }
        }
             */
/*   for (Tail tail:tails){

            if (tail.getPatients().stream()
                    .anyMatch(patient -> patient.getActive().equals(Activity.ON_PROCEDURE)
                    )){
                tail.setVacancies(0);
            }else {
                tail.setVacancies(0);
            }
        }
        *//*





        return tails;
    }

*/


    //  procedure starts => patient gets status ON_PROCEDURE
    //                  tail gets decremention of a number of responsible  doctors
    public Patient startProcedure(int patientId, int procedureId) {

        Patient patient = this.getPatient(patientId);
        int index = this.getAll().indexOf(patient);

        Procedure procedure =procedureService.getProcedure(patientId);

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

        patient.setDelta();
        int duration = (int) patient.getDelta();
        patient.setLastActivity(LocalDateTime.now());
        patient.setActive(Activity.ACTIVE);
        repository.save(patient);


        Talon talon = talonService.getTalonByPatientAndProcedure(patientId
                , procedureId);
        talonService.executeTalon(talon.getId(),1, duration);


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
        int index = this.getAll().indexOf(patient);
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



}
