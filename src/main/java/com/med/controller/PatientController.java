package com.med.controller;

import com.med.model.Patient;
import com.med.model.Person;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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


    @GetMapping("/patient/list")
   public Set<Patient> showPatients(){
        return service.getAll();
    }

     @GetMapping("/patient/list/appointed/today")
   public List<Patient> getPatientsAppointedForToday(){
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
    public Patient showOnePatient(@PathVariable(value = "id")  int patientId) {

        return service.getPatient(patientId);
    }

    // UPDATE the patient by id
    @PostMapping("/patient/update/{id}")
    public Patient updatePatient(@PathVariable(value = "id")  int patientId,
                               @Valid @RequestBody Patient updates)  {
        updates.setId(patientId);

        return service.updatePatient(updates);

    }

    // DELETE the patient by id
    @PostMapping("/patient/delete/{id}")
    public Patient delPatient(@PathVariable(value = "id")  int patientId)  {

        return service.deletePatient(patientId);
    }

    
    //
     @PostMapping("/patient/list/today/insert")
   public Set<Patient> insertPatientsToday(){
        return service.insertAppointedForToday();
    }





}
