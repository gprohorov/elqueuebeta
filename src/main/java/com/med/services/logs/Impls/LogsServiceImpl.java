package com.med.services.logs.Impls;

import com.med.datastorage.DataStorageTest;
import com.med.model.Event;
import com.med.services.logs.interfaces.ILogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Service
public class LogsServiceImpl implements ILogsService {


    @Autowired
    DataStorageTest dataStorage;

    @Override
    public List<Event> getAll() {

        return null;
    }
}
