package com.med.controller.workplace;

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

import com.med.model.Usi;
import com.med.services.UsiService;

@RestController
@RequestMapping("/api/workplace/usi")
@CrossOrigin("*")
public class UsiController {

    @Autowired
    UsiService usiService;

    @GetMapping("/patient/{patientId}")
    public List<Usi> getPatientUsiList(@PathVariable(value = "patientId") String patientId) {
        return usiService.getPatientUsiList(patientId);
    }
    
    @GetMapping("/get/{id}")
    public Usi get(@PathVariable(value = "id") String id) {
    	return usiService.getById(id);
    }
    
    @PostMapping("/create/")
    public void create(@Valid @RequestBody Usi usi) {
        usiService.create(usi);
    }

    @PostMapping("/update/")
    public void updateDoctor(@Valid @RequestBody Usi usi) {
        usiService.update(usi);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable(value = "id") String id) {
        usiService.delete(id);
    }
    
}