package com.med.services.appointment.impls;

import com.med.model.*;
import com.med.services.appointment.interfaces.IAppointmentService;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/11/18.
 */
@SuppressWarnings("ALL")
@Service
public class AppointmentServiceImpl implements IAppointmentService {


    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    PatientServiceImpl patientService;

    @Override
    public List<Talon> createAppointmentToDate(Person person, LocalDate date) {

        Patient patient = new Patient(person);
        List<Talon> talons = new ArrayList<>();

        patient.setActive(Activity.NON_ACTIVE);

        Procedure registration = procedureService.getProcedure(1);
        Talon talon1 = new Talon(patient.getId(), registration);
        talon1.setDate(date);
        talons.add(talon1);


        Procedure diagnostics = procedureService.getProcedure(2);
        Talon talon2 = new Talon(patient.getId(), diagnostics);
        talon2.setDate(date);
        talons.add(talon2);

       talons.stream().forEach(talon -> talonService.createTalon(talon));

        return talons;
    }

    @Override
    public List<Talon> createAppointmentToDate(Patient patient, LocalDate date) {
        List<Talon> talons = new ArrayList<>();

        patient.setActive(Activity.NON_ACTIVE);

        Procedure registration = procedureService.getProcedure(1);
        Talon talon1 = new Talon(patient.getId(), registration);
        talon1.setDate(date);
        talons.add(talon1);

        talons.stream().forEach(talon -> talonService.createTalon(talon));

        return talons;
    }

    @Override
    public List<Talon> deleteAppointment(Patient patient, LocalDate date) {
          return null;
    }

    @Override
    public List<Patient> getPatientsAppointedByDate(LocalDate date) {

        List<Patient> patients = new ArrayList<>();

        talonService.getAll().stream()
                .filter(talon -> talon.getDate().equals(date))
                .mapToInt(Talon::getPatientId)
                .distinct()
                .forEach(el-> patients.add(patientService.getPatient(el)));

        return patients;
    }

    @Override
    public List<Patient> getPatientsAppointedForToday(LocalDate date) {
        return this.getPatientsAppointedByDate(LocalDate.now());
    }


}
