package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 22.04.18.
 */
@Document
public class Talon {

    @Id
    private String id;

    private String patientId;
    private LocalDate date;
    private Procedure procedure;
    private int zones = 1;
    private String desc;
    private LocalDateTime start;
    private LocalDateTime executionTime;
    private Doctor doctor;
    private Status status = Status.SOCIAL;
    private int sum;
    /////////
    private Activity activity = Activity.NON_ACTIVE;


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    public Talon() {
    }

    // full
    public Talon(String id, String patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
        this.sum = sum;
    }
    // without id
    public Talon(String patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
        this.sum = sum;
    }
    //  patient and procedure  for today
    public Talon(String patientId, Procedure procedure) {
        this.patientId = patientId;
        this.date = LocalDate.now();
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public Talon(String patientId, Procedure procedure, LocalDate date) {
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }
    public Talon(String patientId, Procedure procedure, int days) {
        this.patientId = patientId;
        this.date = LocalDate.now().plusDays(days);
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public int getProcedureId() {
        return this.procedure.getId();
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {this.sum = sum;}

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     *
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     *
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     *
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     *
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Talon{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date=" + date +
                ", procedure=" + procedure +
                ", zones=" + zones +
                ", desc='" + desc + '\'' +
                ", start=" + start +
                ", executionTime=" + executionTime +
                ", doctor=" + doctor +
                ", status=" + status +
                ", sum=" + sum +
                ", activity=" + activity +
                '}';
    }
}

