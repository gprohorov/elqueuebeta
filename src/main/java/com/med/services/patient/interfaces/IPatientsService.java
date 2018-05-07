package com.med.services.patient.interfaces;

import com.med.dao.patient.interfaces.IPatientDAO;
import com.med.model.Patient;
import com.med.model.Status;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IPatientsService extends IPatientDAO {

 Patient updateStatus(int patientId, Status status);
}
