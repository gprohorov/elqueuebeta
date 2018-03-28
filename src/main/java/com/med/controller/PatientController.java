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
@RequestMapping("/api")
@RestController
public class PatientController {

    @Autowired
    PatientServiceImpl service;

    @Autowired
    AppointmentServiceImpl appointmentService;


    ////////////////////////// CRUD//////////////////////////
    @GetMapping("/patient/list")
    public List<Patient> showPatients() {
        return service.getAll();
    }

    @GetMapping("/patient/list/appointed/today")
    public List<Patient> getPatientsAppointedForToday() {
        return appointmentService
                .getAppointmentsByDate(LocalDate.now())
                .stream().map(appointment -> appointment.getPatient())
                .collect(Collectors.toList());
    }


    // CREATE a new Patient
    @PostMapping("/patient/create")
    public Patient createPatient(@Valid @RequestBody Person person) {

        return service.createPatient(person);
    }


    // READ the Patient by id
    @GetMapping("/patient/get/{id}")
    public Patient showOnePatient(@PathVariable(value = "id") int patientId) {

        return service.getPatient(patientId);
    }

    // UPDATE the patient by id
    @PostMapping("/patient/update/{id}")
    public Patient updatePatient(@PathVariable(value = "id") int patientId,
                                 @Valid @RequestBody Patient updates) {
        updates.setId(patientId);

        return service.updatePatient(updates);

    }

    // DELETE the patient by id
    @PostMapping("/patient/delete/{id}")
    public Patient delPatient(@PathVariable(value = "id") int patientId) {

        return service.deletePatient(patientId);
    }

    //////////////////////////////////////////////////////////


    // insert into patients list all appointed for today
    @PostMapping("/patient/list/today/insert")
    public List<Patient> insertPatientsToday() {
        return service.insertAppointedForToday();
    }


    // UPDATE the patient's status
    @PostMapping("/patient/update/status/{id}")
    public Patient updatePatientStatus(@PathVariable(value = "id") int patientId,
                                       @Valid @RequestBody Status status) {

        return service.updateStatus(patientId, status);
    }

    // UPDATE the patient's activity
    @PostMapping("/patient/update/balance/{id}")
    public Patient updatePatientActivity(@PathVariable(value = "id") int patientId,
                                       @Valid @RequestBody Activity activity) {

        return service.updateActivity(patientId, activity);
    }

  // UPDATE the patient's activity
    @PostMapping("/patient/update/activity/{id}")
    public Patient updatePatientBalance(@PathVariable(value = "id") int patientId,
                                       @Valid @RequestBody int balance) {

        return service.updateBalance(patientId, balance);
    }



}