package com.med.services.appointment.impls;

import com.med.model.*;
import com.med.services.appointment.interfaces.IAppointmentService;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
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
    TalonServiceImpl talonService;

    @Autowired
    ProcedureServiceImpl procedureService;

    // out of sense

    @Override
    public List<Talon> createAppointment(Patient patient, LocalDate date) {

        return null;
    }

    @Override
    public List<Talon> createAppointment(Person person, LocalDate date) {
        Patient patient = new Patient(person);
        patient.setActive(Activity.NON_ACTIVE);

        Procedure registration = procedureService.getProcedure(1);
        Talon talon = new Talon(patient.getId(), registration);
        talon.setDate(date);
        talonService.createTalon(talon);


        Procedure diagnostics = procedureService.getProcedure(2);
        talon = new Talon(patient.getId(), diagnostics);
        talon.setDate(date);
        talonService.createTalon(talon);

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
