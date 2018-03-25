package com.med.services.appointment.impls;

import com.med.dao.appointment.impls.AppointmentDAOImpl;
import com.med.model.Appointment;
import com.med.model.Patient;
import com.med.services.appointment.interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/11/18.
 */
@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    AppointmentDAOImpl appointmentDAO;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentDAO.createAppointment(appointment);
    }

    @Override
    public Appointment createAppointment(Patient patient, LocalDate date) {
        return appointmentDAO.createAppointment(patient,date);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    @Override
    public Appointment getAppointment(int id) {
        return appointmentDAO.getAppointment(id);
    }

    @Override
    public Appointment deleteAppointment(int id) {
        return appointmentDAO.deleteAppointment(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentDAO.getAppointmentsByDate(date);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentDAO.getAll();
    }
}
