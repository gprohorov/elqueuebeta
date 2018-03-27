package com.med.services.patient.Impls;

import com.med.dao.patient.impls.PatientDAOImpl;
import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Procedure;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.event.impls.EventsServiceImpl;
import com.med.services.patient.interfaces.IPatientsService;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class PatientServiceImpl implements IPatientsService {

   // private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Procedure> procedures = new ArrayList<>();


    @Autowired
    PatientDAOImpl patientDAO;

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    EventsServiceImpl eventsService;



    @Override
    public Patient createPatient(Person person) {
        return patientDAO.createPatient(person);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientDAO.createPatient(patient);
    }

    @Override
    public Patient addPatient(Patient patient) {

        return patientDAO.addPatient(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientDAO.updatePatient(patient);
    }

    @Override
    public Patient getPatient(int id) {
        return patientDAO.getPatient(id);
    }

    @Override
    public Patient deletePatient(int id) {
        return patientDAO.deletePatient(id);
    }

    @Override
    public List<Patient> getAll() {
        return   patientDAO.getAll();

    }

    @Override
    public List<Patient> insertAppointedForToday() {

       // List<Patient> appointed =

                appointmentService
                .getAppointmentsByDate(LocalDate.now())
                .stream().map(appointment -> appointment.getPatient())
                .forEach(el-> this.createPatient(el));
             //   .collect(Collectors.toList());

      //  appointed.stream().forEach(el -> this.createPatient(el));
/*
           for (Patient patient:appointed){

            patientDAO.addPatient(patient);
            Event event = new Event(null,
                   LocalDateTime.now(), patient,
                   doctorService.getDoctor(0),
                   procedureService.getProcedure(1),
                   Action.PUT_IN_QUEUE );

            eventsService.addEvent(event);
        }
        */
        return patientDAO.getAll();
    }



}
