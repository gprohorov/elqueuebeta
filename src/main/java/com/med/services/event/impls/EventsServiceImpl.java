package com.med.services.event.impls;

import com.med.datastorage.DataStorageTest;
import com.med.model.Event;
import com.med.services.event.interfaces.IEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Service
public class EventsServiceImpl implements IEventsService {




    @Autowired
    DataStorageTest dataStorage;

    @Override
    public Event addEvent(Event event) {
        this.getAll().add(event);
        return event;
    }

    @Override
    public List<Event> getAll() {

        return  null;
    }


}
