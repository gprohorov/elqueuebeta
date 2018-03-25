package com.med.dao.patient.interfaces;

import com.med.model.Patient;
import com.med.model.Person;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IPatientDAO {
    Patient createPatient(Person person);
    Patient addPatient(Patient patient);
    Patient updatePatient(Patient patient);
    Patient getPatient(int id);
    Patient deletePatient(int id);
    List<Patient> getAll();
    List<Patient> insertAppointedForToday();
    //List<Patient> getAppointedForDate(LocalDate date);

}
