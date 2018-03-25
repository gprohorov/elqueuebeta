package com.med.controller;

import com.med.model.Patient;
import com.med.services.queuemanager.impls.QueueManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api")
@RestController
public class QueueManagerController {

    @Autowired
    QueueManagerServiceImpl service;


    @RequestMapping("/today")
   public List<Patient> includeAppointedPatientsIntoQueue(){
        return service.includeApointed();
    }
}
