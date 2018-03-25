package com.med.services.queuemanager.interfaces;

import com.med.model.Patient;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IQueueManagerService {

    List<Patient> includeApointed();
}
