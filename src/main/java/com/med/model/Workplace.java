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
    private Talon talon;
    private Patient patient;



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
        this.tail = tailService.getTail(procedure.getId());
    }

    public Tail getTail() {
        return tail;
    }

    public Talon getTalon() {
        return talon;
    }

    public void setTalon(Talon talon) {

        this.talon = talon;
        if(talon!=null ){
        this.patient = patientService.getPatient(talon.getPatientId());
        }else {
            this.patient=null;
        }
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workplace)) return false;

        Workplace workplace = (Workplace) o;

        if (!getDoctor().equals(workplace.getDoctor())) return false;
        return getProcedure().equals(workplace.getProcedure());
    }

    @Override
    public int hashCode() {
        int result = getDoctor().hashCode();
        result = 31 * result + getProcedure().hashCode();
        return result;
    }
}
