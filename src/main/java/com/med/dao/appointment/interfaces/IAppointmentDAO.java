package com.med.dao.appointment.interfaces;

import com.med.model.Appointment;
import com.med.model.Patient;
import com.med.model.Person;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IAppointmentDAO {
    Appointment createAppointment(Appointment appointment);
    Appointment createAppointment(Patient patient, LocalDate date);
    Appointment createAppointment(Person person, LocalDate date);
    Appointment updateAppointment(Appointment appointment);
    Appointment getAppointment(long id);
    Appointment deleteAppointment(long id);
    List<Appointment> getAppointmentsByDate(LocalDate date);
    List<Appointment> getAll();
}
