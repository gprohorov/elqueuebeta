package com.med.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.Doctor;
import com.med.services.DoctorService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    DoctorService service;

    @RequestMapping("/doctor/list")
    public List<Doctor> showDoctors(){
        return service.getAll();
    }

    @PostMapping("/doctor/create")
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }
    
    @GetMapping("/doctor/get/{id}")
    public Doctor showOneDoctor(@PathVariable(value = "id") int doctorId) {
        return service.getDoctor(doctorId);
    }

    @PostMapping("/doctor/update/")
    public Doctor updateDoctor(@Valid @RequestBody Doctor updates) {
        return service.updateDoctor(updates);
    }

    @GetMapping("/doctor/delete/{id}")
    public Doctor delDoctor(@PathVariable(value = "id") int doctorId) {
        return service.deleteDoctor(doctorId);
    }
}