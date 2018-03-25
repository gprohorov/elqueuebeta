package com.med.services.logs.interfaces;

import com.med.model.Event;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ILogsService {


    List<Event> getAll();

}
