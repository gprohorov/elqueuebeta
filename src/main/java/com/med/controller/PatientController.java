package com.med.controller;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.hotel.Record;
import com.med.repository.accounting.AccountingRepository;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.user.UserService;
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

    @Autowired
    AccountingRepository accountingRepository;

    @Autowired
    RecordServiceImpl recordService;

    @Autowired
    UserService userService;

    ////////////////////////// CRUD //////////////////////////

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
        if (patient.getId() == null) {
        	service.registratePatient(patient);
        } else {
            service.savePatient(patient);
        }
        return patient;
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
        return talonService.createTalon(patientId, procedureId, days);
    }

     // final
    // create talon to date for patient on procedure
    @GetMapping("/create/talon/{patientId}/{procedureId}/{date}/{time}")
    public Talon createTalon(@PathVariable(value = "patientId") String patientId,
                             @PathVariable(value = "procedureId") int procedureId,
                             @PathVariable(value = "date") String date,
                             @PathVariable(value = "time") int time) {
        return talonService.createTalon(patientId, procedureId, LocalDate.parse(date));
    }
/*

    // create talons list to date for patient according to therapy, default 9.00
    @GetMapping("/create/talons/{patientId}/{date}/")
    public List<Talon> createTalonsToDate(@PathVariable(value = "patientId") String patientId,
                             @PathVariable(value = "date") String date) {
        return talonService.createTalonsForPatientToDate(patientId, LocalDate.parse(date));
    }Hope1234
*/
       //*******************************************************************************
      // create talon LIST to date for patient according to therapy, appointed to time
    @GetMapping("/create/talons/{patientId}/{date}/{time}")
    public List<Talon> createTalonsToDate(@PathVariable(value = "patientId") String patientId,
                             @PathVariable(value = "date") String date,
                             @PathVariable(value = "time") int time) {
     //   return talonService.createTalonsForPatientToDate(patientId, LocalDate.parse(date), time);
        return service.assignPatientToDate(patientId, LocalDate.parse(date), time);
    }


    // create active talon to date for patient on procedure
    @GetMapping("/create/activetalon/{patientId}/{procedureId}/{date}/{time}/{activate}")
    public Talon createTalon(@PathVariable(value = "patientId") String patientId,
    		@PathVariable(value = "procedureId") int procedureId,
    		@PathVariable(value = "date") String date,
            @PathVariable(value = "time") int time,
    		@PathVariable(value = "activate") Boolean activate) {
    	return talonService.createActiveTalon(patientId, procedureId, LocalDate.parse(date), time, activate);
    }

    // create talon to today for patient on registration
    @GetMapping("/create/talon/today/{patientId}")
    public Talon createTalonOnToday(@PathVariable(value = "patientId") String patientId) {
        // registration - id==1
        // today  plus 0

        return talonService.createTalon(patientId, 1, 0);
    }

/////////////////////////////////////////////////////////////////////////

    // getAll patients for today together with their's talons
    @GetMapping("/list/today")
    public List<Patient> showPatientsForToday() {
        return service.getAllForToday();
    }


    // 12.08.18
    // getAll patients for the date together with their's talons
    @GetMapping("/list/date/{date}")
    public List<Patient> showPatientsForDate(
            @PathVariable(value = "date") String date
    ) {
        return service.getAllForDate(LocalDate.parse(date));
    }




    @GetMapping("/talon/set/activity/{talonId}/{activity}")
    public Talon talonSetActivity(
        @PathVariable(value = "talonId") String talonId,
        @PathVariable(value = "activity") Activity activity) {
        return  talonService.setActivity(talonId, activity);
    }
    @GetMapping("/talon/set/outofturn/{talonId}/{out}")
    public Talon talonSetOutOfTurn(
        @PathVariable(value = "talonId") String talonId,
        @PathVariable(value = "out") boolean out) {
        return  talonService.setOutOfTurn(talonId, out);
    }

    @GetMapping("/set/status/{patientId}/{status}")
    public Patient patientSetStatus(
        @PathVariable(value = "patientId") String patientId,
        @PathVariable(value = "status") Status status) {
        return  service.setStatus(patientId, status);
    }

    @GetMapping("/talon/setall/activity/{patientId}/{activity}")
    public void setAllActivity(
        @PathVariable(value = "patientId") String patientId,
        @PathVariable(value = "activity") Activity activity) {
        talonService.setAllActivity(patientId, activity);
    }

    @GetMapping("/balance/days/{patientId}/{days}")
    public List<Accounting> getBalance(
        @PathVariable(value = "patientId") String patientId,
        @PathVariable(value = "days") int days) {
        return  service.getUltimateBalanceShort(patientId,days);
    }
    
    @GetMapping("/balance/today/{patientId}")
    public List<Accounting> getBalanceToday(@PathVariable(value = "patientId") String patientId) {
    	return service.getUltimateBalanceToday(patientId);
    }

    @GetMapping("/hotel/bill/{patientId}")
    public Accounting createBill(@PathVariable(value = "patientId") String patientId) {
    	return null;
    }

    @PostMapping("/hotel/record/")
    public Record createRecord(@Valid @RequestBody Record record) {
    	return recordService.createRecord(record);
    }

    @GetMapping("/bye/{patientId}")
    public Patient bye(
            @PathVariable(value = "patientId") String patientId) {
        return  service.patientBye(patientId);
    }


    @GetMapping("/execute/{talonId}")
    public Talon execute(
            @PathVariable(value = "talonId") String talonId) {
        int doctorId = userService.getCurrentUserInfo().getId();
        return  talonService.quickExecute(talonId, doctorId);
    }




}
