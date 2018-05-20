package com.med.controller;

import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Talon;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.user.UserService;
import com.med.services.workplace.impls.WorkPlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@RestController
@RequestMapping("/api/workplace")
@CrossOrigin("*")
public class WorkPlaceController {

/*

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TalonServiceImpl talonService;
*/
    @Autowired
    TailServiceImpl tailService;

    @Autowired
    WorkPlaceServiceImpl workPlaceService;

    @Autowired
    UserService userService;



   @RequestMapping("/first/{procedureId}")
   public Patient getFirstPatientInTail(
           @PathVariable(value = "procedureId") int procedureId) {
        return tailService.getFirstPatient(procedureId);
    }


   @RequestMapping("/setvacant/{procedureId}/{signal}")
   public boolean switchSemaFor(
           @PathVariable(value = "procedureId") int procedureId,
           @PathVariable(value = "signal") boolean signal) {
       Doctor doctor = userService.getCurrentUserInfo();
       List<Integer> allowed = doctor.getProcedureIds();
       if (allowed.contains(procedureId)) {

           workPlaceService.switchSemafor(procedureId,signal);
           return true;
       }
       return false;
    }



   /////////////////////////// START////////////////////////////

   @RequestMapping("/start/{patientId}/{procedureId}")
   public Talon start(
           @PathVariable(value = "patientId") String patientId,
           @PathVariable(value = "procedureId") int procedureId){

    int doctorId = userService.getCurrentUserInfo().getId();
   if (this.isAlowed(procedureId,doctorId)){
       return workPlaceService.start(patientId, procedureId, doctorId);
   } else {
       return null;
   }
    }


    //////////////////////////////// EXECUTE ///////////////////

   @PostMapping("/execute/{patientId}")
   public Talon execute(
           @PathVariable(value = "patientId") String patientId,
           @Valid @RequestBody String desc) {

      //  return tailService.getFirstPatient(procedureId);
       return workPlaceService.execute(patientId, desc);
    }


    //////////////////////////////// CANCEL ////////////////////

   @PostMapping("/cancel/{patientId}")
   public Talon cancel(
           @PathVariable(value = "patientId") String patientId,
           @Valid @RequestBody String desc) {

      //  return tailService.getFirstPatient(procedureId);
       return workPlaceService.cancel(patientId, desc);
    }



    private boolean isAlowed(int procedureId, int doctorId){

         return  userService.getCurrentUserInfo().getProcedureIds()
                 .contains(Integer.valueOf(procedureId));
    }



}
