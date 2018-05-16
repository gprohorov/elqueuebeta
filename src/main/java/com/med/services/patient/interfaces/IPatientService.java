package com.med.services.patient.interfaces;

import com.med.model.Patient;
import com.med.model.Status;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IPatientService {
 Patient setStatus(String patientId, Status status);
 Patient savePatient(Patient patient);
 Patient getPatient(String id);
 Patient deletePatient(String id);
 List<Patient> getAll(String lastName);
 List<Patient> getAllForToday();

}
