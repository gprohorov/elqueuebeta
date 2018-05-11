package com.med.services.appointment.interfaces;

import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Talon;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/11/18.
 */
@SuppressWarnings("ALL")
public interface IAppointmentService  {
    public List<Talon> createAppointmentToDate(Person person, LocalDate date);
    public List<Talon> createAppointmentToDate(Patient patient, LocalDate date);
    public List<Talon> deleteAppointment(Patient patient, LocalDate date);
    public List<Patient> getPatientsAppointedByDate(LocalDate date);
    public List<Patient> getPatientsAppointedForToday(LocalDate date);

}
