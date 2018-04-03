package com.med.services.patient.Impls;

import com.med.dao.patient.impls.PatientDAOImpl;
import com.med.model.*;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.event.impls.EventsServiceImpl;
import com.med.services.patient.interfaces.IPatientsService;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Patient setStatus(int patientID, Status status){return null;}

    @Override
    public Patient updateStatus(int patientId, Status status) {
       Patient patient = this.getPatient(patientId);
       patient.setStatus(status);
       this.updatePatient(patient);
        return patient;
    }

    public Patient updateActivity(int patientId, Activity activity) {
       Patient patient = this.getPatient(patientId);
       patient.setActive(activity);
       patient.setLastActivity(LocalDateTime.now());
       this.updatePatient(patient);
        return patient;
    }


    public Patient updateBalance(int patientId, int balance) {
       Patient patient = this.getPatient(patientId);
       patient.setBalance(balance);
       this.updatePatient(patient);
        return patient;
    }



    public Patient updateReckoning(int patientId, Reckoning reckoning) {
        Patient patient = this.getPatient(patientId);
        patient.setReckoning(reckoning);
        this.updatePatient(patient);
        return patient;

    }


    // Add ONE procedure to execute today
 /*   public Patient addProcedure(int patientId, int procedureId) {

        Patient patient = this.getPatient(patientId);
        Procedure procedure = procedureService.getProcedure(procedureId);
        patient.assignProcedure(procedure);

        return patient;
    }

    public Patient removeProcedure(int patientId, int procedureId) {

        Patient patient = this.getPatient(patientId);
        Procedure procedure = procedureService.getProcedure(procedureId);
        patient.disAssignProcedure(procedure);

        return patient;
    }
*/

    // add ALL procedures for TODAY according to the therapy
/*    public Patient addProceduresAll(int patientId) {

        Patient patient = this.getPatient(patientId);

        if(patient.getAssignedProcedures().isEmpty()) {
            Map<Procedure, Integer> assignedTherapy = patient.getTherapy().getProgress();
            for(Map.Entry<Procedure, Integer> assignation: assignedTherapy.entrySet()){
                if (assignation.getValue()>0){
                    this.addProcedure(patientId,assignation.getKey().getId());
                }
            }
        }
        return null;
    }*/






    // get progress in crowd :  ratio of executed procedures to assigned ones
    public Double getProgress(int patientId) {
        Double progress = 0.0;
/*        Patient patient = this.getPatient(patientId);
        HashMap<Procedure,Boolean> map = patient.getAssignedProcedures();
        if (!map.isEmpty()){
           int nominator = (int) map.entrySet().stream()
                   .filter(entry->entry.getValue().equals(true)).count();
           int denominator = map.size();
           progress = Double.valueOf(nominator) /denominator;
        }*/
        return progress;
    }

      // get a sorted list of patients to the specified procedure
    public List<Patient> getQueueToProcedure(int procedureId) {

        Procedure procedure = procedureService.getProcedure(procedureId);

/*        Map.Entry<Procedure, Boolean> entry
                = new AbstractMap.SimpleEntry<Procedure, Boolean>(procedure, false);
 */
        System.out.println(procedure);



        return this.getAll().stream()
                //.filter(pat -> pat.getActive().equals(Activity.ACTIVE))
                .filter(pat -> pat.getProceduresForToday()
                        .contains(procedure)).collect(Collectors.toList());
    }

     // patient has got executed procedure, so we mark it as "done" in his assigned procedures
    public Patient executeProcedure(int patientId, @Valid int procedureId) {
        Patient patient = this.getPatient(patientId);
        Procedure procedure = procedureService.getProcedure(procedureId);
     //   patient.markProcedureAsExecuted(procedure);
        return patient;
    }

}
