package com.med.controller;

import com.med.model.Patient;
import com.med.model.Talon;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/patient")
@RestController
@CrossOrigin("*")
public class PatientController {

    @Autowired
    PatientServiceImpl service;

    ////////////////////////// CRUD//////////////////////////

    // getAll
    @GetMapping("/list/")
    public List<Patient> showPatients() {
        return service.getAll("");
    }

    @GetMapping("/list/{search}")
    public List<Patient> showPatients(@PathVariable(value = "search") String search) {
        return service.getAll(search);
    }

    // READ the Patient by id
    @GetMapping("/get/{id}")
    public Patient showOnePatient(@PathVariable(value = "id") String patientId) {
        return service.getPatient(patientId);
    }

    // Save the patient
    @PostMapping("/save/")
    public Patient savePatient(@Valid @RequestBody Patient patient) {
        return service.savePatient(patient);
    }

    // DELETE the patient by id
    @GetMapping("/delete/{id}")
    public Patient delPatient(@PathVariable(value = "id") String patientId) {
        return service.deletePatient(patientId);
    }



    //////////////////////END OF CRUD ////////////////////////////////////


    // create talon to date for patient on procedure
    @GetMapping("/create/talon/procedure/date/{patientId}/{procedureId}/{days}")
    public Talon createTalon(@PathVariable(value = "patientId") String patientId,
                                 @PathVariable(value = "procedureId") int procedureId,
                                 @PathVariable(value = "days") int days) {


        return null;}






/*

    @GetMapping("/list/appointed/today")
    public List<Patient> getPatientsAppointedForToday() {
        return appointmentService
                .getAppointmentsByDate(LocalDate.now())
                .stream().map(appointment -> appointment.getPatient())
                .collect(Collectors.toList());
    }

    // insert into patients list all appointed for today
    @PostMapping("/list/today/insert")
    public List<Patient> insertPatientsToday() {
        return service.insertAppointedForToday();
    }

    // UPDATE the patient's status
    @GetMapping("/update/status/{id}/{status}")
    public Patient updatePatientStatus(@PathVariable(value = "id") String patientId,
                                       @PathVariable(value = "status") Status status) {
        return service.updateStatus(patientId, status);
    }

    // UPDATE the patient's activity
    // example http://localhost:8088/api/patient/update/activity/5/ACTIVE
    @GetMapping("/update/activity/{id}/{activity}")
    public Patient updatePatientActivity(@PathVariable(value = "id") int patientId,
                                       @PathVariable(value = "activity") Activity activity) {
        if (activity.equals(Activity.ON_PROCEDURE)) {
        Tail tail = this.getTails().stream()
                .filter(tail1 -> tail1.getProcedureId()==6).findAny().get();
        tail.setVacancies(tail.getVacancies()-1);
        tail.setVacant(false);
        }

        if (activity.equals(Activity.ACTIVE)) {
        Tail tail = this.getTails().stream()
                .filter(tail1 -> tail1.getProcedureId()==6).findAny().get();
        tail.setVacancies(tail.getVacancies()+1);
        tail.setVacant(true);
        }
        return service.updateActivity(patientId, activity);
    }

  // UPDATE the patient's balance
    // example  http://localhost:8088/api/patient/update/balance/5/100
    @GetMapping("/update/balance/{id}/{balance}")
    public Patient updatePatientBalance(@PathVariable(value = "id") int patientId,
                                       @PathVariable(value = "balance") int balance) {

        return service.updateBalance(patientId, balance);
    }

    // UPDATE the patient's reckoning
    // example  http://localhost:8088/api/patient/update/balance/5/100
    @GetMapping("/update/reckoning/{id}/{reckoning}")
    public Patient updatePatientReckoning(@PathVariable(value = "id") int patientId,
                                          @PathVariable(value = "reckoning") Reckoning reckoning) {

        return service.updateReckoning(patientId, reckoning);
    }


  // get progress in crowd :  ratio of executed procedures to assigned ones
    @PostMapping("/progress/{id}")
    public String getProgress(@PathVariable(value = "id") int patientId) {

        return service.getProgress(patientId);
    }

    // get a list of patients to procedure orderd by time and status
    // example   http://localhost:8088/api/patient/list/procedure/7
      @GetMapping("/list/procedure/{id}")
    public List<Patient> queueToProcedure(@PathVariable(value = "id") int procedureId){

        return service.getQueueToProcedure(procedureId);
    }


    // put THE procedure (ONLY ONE)  into map of assigned for today
    @PostMapping("/add/procedure/{id}")
    public Patient addProcedure(@PathVariable(value = "id") int patientId,
                                @Valid @RequestBody int procedureId) {

        return service.addProcedure(patientId, procedureId);
    }

    // remove the procedure from the map of assigned for today
    @PostMapping("/remove/procedure/{id}")
    public Patient removeProcedure(@PathVariable(value = "id") int patientId,
                                @Valid @RequestBody int procedureId) {

        return service.removeProcedure(patientId, procedureId);
    }

     // start procedure i.e. set patient's status as ON_PROCEDURE
    // and decrement the number of vacant doctors responsible for this procedure
     @GetMapping("/start/procedure/{patid}/{procid}")
     public Patient startProcedure(@PathVariable(value = "patid") int patientId,
                                     @PathVariable(value = "procid") int procedureId) {

         return service.startProcedure(patientId, procedureId);
     }


    // procedure executed successfully => patient gets status ACTIVE
    //      the procedure in his schema marked as DONE
    //    the number of vacant doctors responsible for this procedure => + 1
    @GetMapping("/execute/procedure/{patid}/{procid}")
    public Patient executeProcedure(@PathVariable(value = "patid") int patientId,
                                @PathVariable(value = "procid") int procedureId
                                )
    {

        return service.executeProcedure(patientId, procedureId);
    }


    // procedure canceled => patient gets status ACTIVE
    //    the number of vacant doctors responsible for this procedure => + 1
    @GetMapping("/cancel/procedure/{patid}/{procid}")
    public Patient cancelProcedure(@PathVariable(value = "patid") int patientId,
                                @PathVariable(value = "procid") int procedureId) {

        return service.cancelProcedure(patientId, procedureId);
    }





    // set therapy for patient

    @GetMapping("/set/therapy/{patient_id}/{therapy_id}")
    public Patient setTherapy(@PathVariable(value = "patient_id") int patientId,
                                @PathVariable(value = "therapy_id") int therapyId) {

        return service.setTherapy(patientId, therapyId);
    }

    // get tails for each procedure
    @GetMapping("/get/tails")
    public List<Tail> getTails(){

        return service.getTails();
    }

//  get the first patient for the procedure from tail
    @GetMapping("/tail/get/first/{procid}")
    public Patient getFirstFromTail(@PathVariable(value = "procid") int procedureId) {


        return service.getFirstFromTail(procedureId);
    }

*/

}