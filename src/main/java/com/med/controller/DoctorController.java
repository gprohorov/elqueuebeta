package com.med.controller;

import com.med.model.Doctor;
import com.med.services.doctor.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    DoctorServiceImpl service;



    @RequestMapping("/doctor/list")
   public List<Doctor> showDoctors(){
        return service.getAll();
    }


/*

    // get a Person list by lastName
    @GetMapping("/doctor/list/{name}")
    public List<Doctor> getDoctorsByLastName(@PathVariable(value = "name") String lastName) {
        return service.getDoctorListByLetters(lastName);
    }

*/

    // CREATE a new Doctor
    @PostMapping("/doctor/create")
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {

        return service.createDoctor(doctor);
    }


    // READ the Doctor by id
    @GetMapping("/doctor/get/{id}")
    public Doctor showOneDoctor(@PathVariable(value = "id")  int doctorId) {

        return service.getDoctor(doctorId);
    }

    // UPDATE the doctor by id
    @PostMapping("/doctor/update/")
    public Doctor updateDoctor(@Valid @RequestBody Doctor updates)  {
     //   updates.setId(doctorId);

        return service.updateDoctor(updates);

    }

    // DELETE the doctor by id
    @GetMapping("/doctor/delete/{id}")
    public Doctor delDoctor(@PathVariable(value = "id")  int doctorId)  {

        return service.deleteDoctor(doctorId);

    }


}
