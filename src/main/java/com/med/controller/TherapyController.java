package com.med.controller;

import com.med.model.Therapy;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TherapyController {



    @Autowired
    TherapyServiceImpl service;

    // READ the Therapy by id
    @GetMapping("/therapy/get/{id}")
    public Therapy getTherapy(@PathVariable(value = "id") String therapyId) {

        return service.getTherapy(therapyId);
    }

    // CREATE the Therapy
    @PostMapping("/therapy/save")
    public Therapy Therapy(@RequestBody Therapy therapy) {
        return service.saveTherapy(therapy);
    }


}

