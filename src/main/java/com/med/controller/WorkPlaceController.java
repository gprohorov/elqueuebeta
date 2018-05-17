package com.med.controller;

import com.med.model.Generic;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.generic.impls.GenericServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@RestController
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



    @RequestMapping("/generics")
   public List<Generic> showGenerics(){
        return service.getAll();
    }
}
