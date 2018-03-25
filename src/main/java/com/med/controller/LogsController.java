package com.med.controller;

import com.med.model.Event;
import com.med.services.logs.Impls.LogsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@RestController
public class LogsController {

    @Autowired
    LogsServiceImpl service;


    @RequestMapping("/showlogs")
   public List<Event> showLogs(){
        return service.getAll();
    }
}
