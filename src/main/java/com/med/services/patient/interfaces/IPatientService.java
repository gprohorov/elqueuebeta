package com.med.services.patient.interfaces;

import com.med.model.Patient;
import com.med.model.Status;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IPatientsService {
 Patient updateStatus(ObjectId patientId, Status status);
 Patient savePatient(Patient patient);
 Patient getPatient(ObjectId id);
 Patient deletePatient(ObjectId id);
 List<Patient> getAll(String lastName);
}
