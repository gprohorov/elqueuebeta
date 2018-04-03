package com.med.controller;

import com.med.model.*;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/patient")
@RestController
@CrossOrigin("*")
public class PatientController {

    @Autowired
    PatientServiceImpl service;

    @Autowired
    AppointmentServiceImpl appointmentService;


    ////////////////////////// CRUD//////////////////////////

    // getAll
    @GetMapping("/list")
    public List<Patient> showPatients() {
        return service.getAll();
    }


    // CREATE a new Patient
    @PostMapping("/create")
    public Patient createPatient(@Valid @RequestBody Person person) {

        return service.createPatient(person);
    }


    // READ the Patient by id
    @GetMapping("/get/{id}")
    public Patient showOnePatient(@PathVariable(value = "id") int patientId) {

        return service.getPatient(patientId);
    }

    // UPDATE the patient by id
    @PostMapping("/update/{id}")
    public Patient updatePatient(@PathVariable(value = "id") int patientId,
                                 @Valid @RequestBody Patient updates) {
        updates.setId(patientId);

        return service.updatePatient(updates);

    }

    // DELETE the patient by id
    @GetMapping("/delete/{id}")
    public Patient delPatient(@PathVariable(value = "id") int patientId) {

        return service.deletePatient(patientId);
    }



    //////////////////////END OF CRUD ////////////////////////////////////


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
    public Patient updatePatientStatus(@PathVariable(value = "id") int patientId,
                                       @PathVariable(value = "status") Status status) {

        return service.updateStatus(patientId, status);
    }

    // UPDATE the patient's activity
    // example http://localhost:8088/api/patient/update/activity/5/ACTIVE
    @GetMapping("/update/activity/{id}/{activity}")
    public Patient updatePatientActivity(@PathVariable(value = "id") int patientId,
                                       @PathVariable(value = "activity") Activity activity) {

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
    public Double getProgress(@PathVariable(value = "id") int patientId) {

        return service.getProgress(patientId);
    }

    // get a list of patients to procedure orderd by time and status
    // example   http://localhost:8088/api/patient/list/procedure/7
      @GetMapping("/list/procedure/{id}")
    public List<Patient> queueToProcedure(@PathVariable(value = "id") int procedureId){

        return service.getQueueToProcedure(procedureId);
    }
/*

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
*/

    // put the procedure into map of assigned for today
    @PostMapping("/execute/procedure/{id}")
    public Patient executeProcedure(@PathVariable(value = "id") int patientId,
                                @Valid @RequestBody int procedureId) {

        return service.executeProcedure(patientId, procedureId);
    }


    // put the procedure into map of assigned for today
/*
    @PostMapping("/add/procedure/all/{id}")
    public Patient addAllProcedures(@PathVariable(value = "id") int patientId) {

        return service.addProceduresAll(patientId);
    }
*/


}