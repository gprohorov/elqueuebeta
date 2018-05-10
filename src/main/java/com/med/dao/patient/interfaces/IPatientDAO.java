package com.med.dao.patient.interfaces;

import com.med.model.Patient;
import com.med.model.Person;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IPatientDAO {
    Patient createPatient(Person person);
    Patient createPatient(Patient patient);
    Patient addPatient(Patient patient);
    Patient updatePatient(Patient patient);
    Patient getPatient(int id);
    Patient deletePatient(int id);
    List<Patient> getByLastName(String lastName);
    List<Patient> getAll();
    List<Patient> insertAppointedForToday();
    //List<Patient> getAppointedForDate(LocalDate date);

}
