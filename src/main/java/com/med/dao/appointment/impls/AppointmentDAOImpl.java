package com.med.dao.appointment.impls;

import com.med.dao.appointment.interfaces.IAppointmentDAO;
import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Talon;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/11/18.
 */
@SuppressWarnings("ALL")
@Component
public class AppointmentDAOImpl implements IAppointmentDAO {


    @Override
    public List<Talon> createAppointment(Patient patient, LocalDate date) {
        return null;
    }

    @Override
    public List<Talon> createAppointment(Person person, LocalDate date) {
        return null;
    }

    @Override
    public List<Talon> deleteAppointment(Patient patient, LocalDate date) {
        return null;
    }

    @Override
    public List<Patient> getAppointmentsByDate(LocalDate date) {
        return null;
    }
}
