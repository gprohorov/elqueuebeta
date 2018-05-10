package com.med.dao.appointment.interfaces;

import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Talon;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IAppointmentDAO {

    List<Talon> createAppointment(Patient patient, LocalDate date);
    List<Talon> createAppointment(Person person, LocalDate date);
    List<Talon>  deleteAppointment(Patient patient, LocalDate date);
    List<Patient> getAppointmentsByDate(LocalDate date);

}
