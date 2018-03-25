package com.med.dao.appointment.interfaces;

import com.med.model.Appointment;
import com.med.model.Patient;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IAppointmentDAO {
    Appointment createAppointment(Appointment appointment);
    Appointment createAppointment(Patient patient, LocalDate date);
    Appointment updateAppointment(Appointment appointment);
    Appointment getAppointment(int id);
    Appointment deleteAppointment(int id);
    List<Appointment> getAppointmentsByDate(LocalDate date);
    List<Appointment> getAll();
}
