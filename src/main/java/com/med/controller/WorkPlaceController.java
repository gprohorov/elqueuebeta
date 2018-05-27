package com.med.controller;

import com.med.model.Patient;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
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

    @Autowired
    TalonServiceImpl talonService;


/*
    @RequestMapping("/first/{procedureId}")
    public Patient getFirstPatientInTail(
           @PathVariable(value = "procedureId") int procedureId) {
        return tailService.getFirstPatient(procedureId);
    }

    @RequestMapping("/setready/{procedureId}")
    public Boolean setReady(@PathVariable(value = "procedureId") int procedureId) {
       Doctor doctor = userService.getCurrentUserInfo();
       List<Integer> allowed = doctor.getProcedureIds();
       if (allowed.contains(procedureId)) {
           workPlaceService.setReady(procedureId);
       }
       return null;
    }
*/

    /////////////////////////// START////////////////////////////

    @RequestMapping("/start/{patientId}/{procedureId}")
    public Talon start(
           @PathVariable(value = "patientId") String patientId,
           @PathVariable(value = "procedureId") int procedureId) {
        int doctorId = userService.getCurrentUserInfo().getId();
        if (this.isAlowed(procedureId, doctorId)) {

            return workPlaceService.start(patientId, procedureId, doctorId);
        } else {
           return null;
        }
    }

    //////////////////////////////// EXECUTE ///////////////////

    @GetMapping("/execute/{patientId}/{zones}")
    public Talon execute(
           @PathVariable(value = "patientId") String patientId,
           @PathVariable(value = "zones") int zones
            ) {
        int doctorId = userService.getCurrentUserInfo().getId();
       return workPlaceService.execute(patientId, doctorId, zones);
    }


    //////////////////////////////// CANCEL ////////////////////

    @GetMapping("/cancel/{patientId}")
    public Talon cancel(
           @PathVariable(value = "patientId") String patientId
        //   ,@Valid @RequestBody String desc
    ) {

      //  return tailService.getFirstPatient(procedureId);
       return workPlaceService.cancel(patientId, "");
    }



    private boolean isAlowed(int procedureId, int doctorId) {

         return  userService.getCurrentUserInfo().getProcedureIds()
                 .contains(Integer.valueOf(procedureId));
    }


    @RequestMapping("/tails")
    public List<Tail> getHotTails() {

        int doctorId = userService.getCurrentUserInfo().getId();
        return workPlaceService.getTailsForDoctor( doctorId);
    }


    @GetMapping("/patient/{talonId}")
    public Patient getTalonAndPatient(
            @PathVariable(value = "talonId") String talonId) {

        return workPlaceService.getTalonAndPatient(talonId);
    }

    @PostMapping("/comment/{talonId}")
    public Talon comment(
            @PathVariable(value = "talonId") String talonId,
            @Valid @RequestBody String comment){


        return  workPlaceService.commentTalon(talonId, comment);
    }



}
