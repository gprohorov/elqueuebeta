package com.med.controller.workplace;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.Tail;
import com.med.model.Talon;
import com.med.model.TalonPatient;
import com.med.services.TailService;
import com.med.services.TalonService;
import com.med.services.UserService;
import com.med.services.WorkPlaceService;

@RestController
@RequestMapping("/api/workplace")
@CrossOrigin("*")
public class UsiController {
/*
    @Autowired
    TailService tailService;

    @Autowired
    WorkPlaceService workPlaceService;

    @Autowired
    UserService userService;

    @Autowired
    TalonService talonService;

    /////////////////////////// START ////////////////////////////

    @RequestMapping("/start/{talonId}")
    public Talon start(@PathVariable(value = "talonId") String talonId) {
    	int doctorId = userService.getCurrentUserInfo().getId();
    	if (userService.isSuperAdmin() 
			|| this.isAlowed(talonService.getTalon(talonId).getProcedure().getId(), doctorId)) {
            return workPlaceService.start(talonId, doctorId);
        }
    	return null;
    }

    //////////////////////////////// EXECUTE ///////////////////

    @PostMapping("/execute/{talonId}/{zones}")
    public void execute(@Valid @RequestBody ArrayList<ArrayList<Object>> picture,
		@PathVariable(value = "talonId") String talonId, 
		@PathVariable(value = "zones") int zones) {
        workPlaceService.execute(talonId, zones, userService.getCurrentUserInfo().getId(), picture);
    }

    //////////////////////////////// CANCEL ////////////////////

    @GetMapping("/cancel/{talonId}")
    public void cancel(@PathVariable(value = "talonId") String talonId) {
    	workPlaceService.cancel(talonId, "");
    }

    private boolean isAlowed(int procedureId, int doctorId) {
         return userService.getCurrentUserInfo().getProcedureIds().contains(Integer.valueOf(procedureId));
    }

    @RequestMapping("/tails")
    public List<Tail> getHotTails() {
        return workPlaceService.getTailsForDoctor(userService.getCurrentUserInfo().getId());
    }

    @GetMapping("/patient/{patientId}/{procedureId}")
    public TalonPatient getTalonAndPatientNew(
        @PathVariable(value = "patientId") String patientId,
        @PathVariable(value = "procedureId") int procedureId) {
        return workPlaceService.getTalonPatient(patientId, procedureId);
    }

    @PostMapping("/comment/{talonId}")
    public Talon comment(
        @PathVariable(value = "talonId") String talonId,
        @Valid @RequestBody String comment) {
        return  workPlaceService.commentTalon(talonId, comment);
    }
    
    //////////////////// zones ////////////////////
    
    @GetMapping("/subzone/{talonId}")
    public Talon subZone(@PathVariable(value = "talonId") String talonId) {
        return  workPlaceService.subZone(talonId);
    }
    
    @GetMapping("/addzone/{talonId}")
    public Talon addZone(@PathVariable(value = "talonId") String talonId) {
    	return  workPlaceService.addZone(talonId);
    }
*/
}