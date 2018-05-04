package com.med.dao.event.interfaces;

import com.med.model.Event;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IEventDAO {
    Event createEvent(Event event);
    Event getEvent(int id);
    List<Event> getAll();
    List<Event> getAllEventsForPatientFromDate();
  //  List<Event> insertTodayAppointments();

}
