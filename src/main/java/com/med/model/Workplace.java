package com.med.model;

import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by george on 17.05.18.
 */
public class Workplace {
    private Doctor doctor;
    private Procedure procedure;
    private Tail tail;
    private Patient patient;
    private Talon talon;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TalonServiceImpl talonService;




    public Workplace() {
    }

    public Workplace(int doctorId, int procedureId) {
        this.doctor = doctorService.getDoctor(doctorId);
        this.procedure = procedureService.getProcedure(procedureId);
        this.tail = tailService.getTail(procedureId);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Tail getTail() {
        return tail;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Talon getTalon() {
        return talon;
    }

    public void setTalon(Talon talon) {
        this.talon = talon;
    }
}
