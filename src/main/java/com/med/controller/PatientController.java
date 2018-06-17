package com.med.controller;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.balance.Balance;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @Autowired
    TalonServiceImpl talonService;

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


        return talonService.createTalon(patientId,procedureId,days);}

     // final
    // create talon to date for patient on procedure
    @GetMapping("/create/talon/{patientId}/{procedureId}/{date}")
    public Talon createTalon(@PathVariable(value = "patientId") String patientId,
                             @PathVariable(value = "procedureId") int procedureId,
                             @PathVariable(value = "date") String date) {


        return talonService.createTalon(patientId,procedureId, LocalDate.parse(date));}


    // create talon to today for patient on registration
    @GetMapping("/create/talon/today/{patientId}")
    public Talon createTalonOnToday(@PathVariable(value = "patientId") String patientId)
                                  {
        // registration - id==1
        // today  plus 0

        return talonService.createTalon(patientId,1,0);}


/////////////////////////////////////////////////////////////////////////

    // getAll patientss for today together with their's talons
    @GetMapping("/list/today")
    public List<Patient> showPatientsForToday() {

        return service.getAllForToday();
    }



    @GetMapping("/talon/set/activity/{talonId}/{activity}")
    public Talon talonSetActivity(
            @PathVariable(value = "talonId") String talonId,
            @PathVariable(value = "activity") Activity activity)
    {

        return  talonService.setActivity(talonId, activity);
    }


    @GetMapping("/set/status/{patientId}/{status}")
    public Patient patientSetStatus(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "status") Status status)
    {

        return  service.setStatus(patientId, status);
    }




    @GetMapping("/talon/setall/activity/{patientId}/{activity}")
    public List<Talon> setAllActivity(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "activity") Activity activity){

        return  talonService.setAllActivity(patientId, activity);
    }

    @GetMapping("/balance/days/{patientId}/{days}")
    public Balance getBalance(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "days") int days){

        return  service.getUltimateBalanceShort(patientId,days);
    }

    @GetMapping("/balance/today/{patientId}")
    public Balance getBalanceToday(
            @PathVariable(value = "patientId") String patientId){

        return  service.getUltimateBalanceToday(patientId);
    }




}