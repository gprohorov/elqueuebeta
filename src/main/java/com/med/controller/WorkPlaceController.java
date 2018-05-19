package com.med.controller;

import com.med.model.Patient;
import com.med.model.Talon;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.generic.impls.GenericServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.user.UserService;
import com.med.services.workplace.impls.WorkPlaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */

@RestController
@RequestMapping("/api/workplace")
@CrossOrigin("*")

public class WorkPlaceController {

    @Autowired
    GenericServiceImpl service;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    WorkPlaceServiceImpl workPlaceService;

    @Autowired
    UserService userService;



   @RequestMapping("/first/{procedureId}")
   public Patient getFirstPatientInTail(
           @PathVariable(value = "procedureId") int procedureId){
        return tailService.getFirstPatient(procedureId);
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

   @RequestMapping("/execute/{patientId}/{desc}")
   public Talon execute(
           @PathVariable(value = "patientId") String patientId,
           @PathVariable(value = "desc") String desc){

      //  return tailService.getFirstPatient(procedureId);
       return workPlaceService.execute(patientId, desc);
    }


    //////////////////////////////// CANCEL ////////////////////

   @RequestMapping("/cancel/{patientId}/{desc}")
   public Talon cancel(
           @PathVariable(value = "patientId") String patientId,
           @PathVariable(value = "desc") String desc){

      //  return tailService.getFirstPatient(procedureId);
       return workPlaceService.cancel(patientId, desc);
    }



    private boolean isAlowed(int procedureId, int doctorId){

         return  userService.getCurrentUserInfo().getProcedureIds()
                 .contains(Integer.valueOf(procedureId));
    }



}
