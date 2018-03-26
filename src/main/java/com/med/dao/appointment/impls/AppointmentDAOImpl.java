package com.med.dao.appointment.impls;

import com.med.DataStorage;
import com.med.dao.appointment.interfaces.IAppointmentDAO;
import com.med.model.Appointment;
import com.med.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/11/18.
 */
@Component
public class AppointmentDAOImpl implements IAppointmentDAO {




    @Autowired
    DataStorage dataStorage;

/*
    private List<Appointment> appointments = new ArrayList<>();
    @PostConstruct
   void init(){
        appointments = dataStorage.getAppointments();
    }
*/

    @Override
    public Appointment createAppointment(Appointment appointment) {
        dataStorage.getAppointments().add(appointment);

        return null;
    }

    @Override
    public Appointment createAppointment(Patient patient, LocalDate date) {
        Appointment appointment = new Appointment( patient, date);
        return null;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public Appointment getAppointment(int id) {
        return dataStorage.getAppointments().stream()
                .filter(el->el.getId()==id).findFirst()
                .orElse(null);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {

        return dataStorage.getAppointments().stream()
                .filter(el->el.getDate().equals(date))
                .collect(Collectors.toList());

    }

    @Override
    public Appointment deleteAppointment(int id) {

       Appointment appointment = this.getAppointment(id);
        int index = dataStorage.getAppointments().indexOf(appointment);
        dataStorage.getAppointments().remove(index);

        return appointment;
    }

    @Override
    public List<Appointment> getAll() {
        return dataStorage.getAppointments();
    }
}
