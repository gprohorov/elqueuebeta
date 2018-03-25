package com.med.controller;

import com.med.model.Event;
import com.med.services.event.impls.EventsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    EventsServiceImpl service;


    @RequestMapping("/events")
   public List<Event> showAll(){return service.getAll();
    }
}
