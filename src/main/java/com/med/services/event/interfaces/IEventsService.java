package com.med.services.event.interfaces;

import com.med.model.Event;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IEventsService {

    Event addEvent(Event event);
    List<Event> getAll();
   // List<Event> appointed(LocalDate date);


}
