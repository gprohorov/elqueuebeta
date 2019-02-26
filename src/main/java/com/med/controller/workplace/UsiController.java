package com.med.controller.workplace;

import java.util.ArrayList;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.services.UserService;
import com.med.services.WorkPlaceService;

@RestController
@RequestMapping("/api/workplace/usi")
@CrossOrigin("*")
public class UsiController {
	
	@Autowired
    WorkPlaceService workPlaceService;

    @Autowired
    UserService userService;

    @PostMapping("/execute/{talonId}/{zones}")
    public void executeUSI(@Valid @RequestBody String data,
		@PathVariable(value = "talonId") String talonId, 
		@PathVariable(value = "zones") int zones) {
    	
    	JSONObject jsonObj = new JSONObject(data);
    	ArrayList<ArrayList<Object>> picture = (ArrayList<ArrayList<Object>>) jsonObj.get("picture");
    	int doctorId = userService.getCurrentUserInfo().getId();
    	String usi = jsonObj.getString("usi");
        workPlaceService.execute(talonId, zones, doctorId, picture, usi);
    }
    
}