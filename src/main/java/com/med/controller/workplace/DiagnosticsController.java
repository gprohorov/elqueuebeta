package com.med.controller.workplace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.PatientTalonTherapy;
import com.med.model.Procedure;
import com.med.model.Therapy;
import com.med.services.TalonService;
import com.med.services.TherapyService;

@RestController
@RequestMapping("/api/workplace/diagnostic")
@CrossOrigin("*")
public class DiagnosticsController {

    @Autowired
    TherapyService service;
    
    @Autowired
    TalonService talonService;

    @RequestMapping("/list/")
    public List<Therapy> showTherapys() {
        return service.getAll();
    }

    // READ the Therapy by id
    @GetMapping("/get/{patientId}")
    public PatientTalonTherapy showOneTherapy(@PathVariable(value = "patientId") String patientId) {
        return service.getPatientTalonTherapy(patientId);
    }

    // start the Therapy
    @GetMapping("/start/{talonId}")
    public void startProcedure(@PathVariable String talonId) {
        service.startProcedure(talonId);
    }

    // cancel the Therapy
    @GetMapping("/cancel/{talonId}")
    public void cancelProcedure(@PathVariable String talonId) {
        service.cancelProcedure(talonId);
    }

    // EXECUTE the Therapy
    @PostMapping("/execute/{talonId}")
    public void executeProcedure(@PathVariable String talonId, @RequestBody Therapy therapy) {
        service.executeProcedure(talonId, therapy);
    }

    // DELETE the therapy by id
    @PostMapping("/therapy/delete/{id}")
    public Therapy delTherapy(@PathVariable(value = "id") int therapyId) {
        return null;
        //service.deleteTherapy(therapyId);
    }

    // finish the Therapy by id
    @GetMapping("/therapy/finish/{id}")
    public Therapy finishTherapy(@PathVariable(value = "id") String therapyId) {
        return service.finishTherapy(therapyId);
    }
    
    /*
    // assign talons acc. to therapy
    @GetMapping("/therapy/assign/{id}")
    public List<Talon> assignTherapy(@PathVariable(value = "id")  String therapyId) {

        return service.assignTherapy(therapyId);
    }
    */
    
    @GetMapping("/procedures")
    public List<Procedure> getProcedures() {
    	return talonService.getFilledProcedures(); 
    }
}