package com.med.dao.patient.impls;

import com.med.DataStorage;
import com.med.dao.patient.interfaces.IPatientDAO;
import com.med.model.Appointment;
import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Status;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.event.impls.EventsServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class PatientDAOImpl implements IPatientDAO {


//1 private List<Patient> patients = new LinkedList<>();



    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    EventsServiceImpl eventsService;
    
    @Autowired
    DataStorage dataStorage;


    @PostConstruct
    void init(){
      //  patients = dataStorage.getPatients();

    }


    @Override
    public Patient createPatient(Person person) {
        Patient patient = new Patient(person);
        patient.setId(person.getId());
        return this.createPatient(patient);
    }

    @Override
    public Patient createPatient(Patient patient) {

        if(
             this.getAll().stream()
                     .noneMatch(ptn->ptn.getPerson().getId()==patient.getPerson().getId())
           )
        {
            patient.setLastActivity(LocalDateTime.now());
            patient.setStatus(Status.SOCIAL);
            dataStorage.getPatients().add(patient);
            return patient;
        }
        else return null;
    }

    @Override
    public Patient addPatient(Patient patient) {
        this.getAll().add(patient);
        return patient;
    }

    @Override
    public Patient updatePatient(Patient patient) {

        Patient ptn = this.getAll().stream()
                .filter(p->p.getId()==patient.getId()).findFirst().get();

        int index = this.getAll().indexOf(ptn);

        this.getAll().set(index,patient);

        return patient;
    }

    @Override
    public Patient getPatient(int patientId) {
       // System.out.println(patientId);
        return dataStorage.getPatients().stream().filter(patient -> patient.getId()==patientId)
                .findFirst().get();
    }

    @Override
    public Patient deletePatient(int id) {
     Patient patient = dataStorage.getPatients().stream().filter(pat -> pat.getPerson().getId()==id)
             .findFirst().orElse(null);
    int index = dataStorage.getPatients().indexOf(patient);
     dataStorage.getPatients().remove(index);
        return patient;
    }

    @Override
    public List<Patient> getAll() {

        return dataStorage.getPatients().stream().sorted().collect(Collectors.toList());
    }


    @Override
    public List<Patient> insertAppointedForToday() {

        System.out.println(this.getAll().size());

        for(Appointment appointment: appointmentService.getAppointmentsByDate(LocalDate.now())){

            Patient patient = appointment.getPatient();
            this.getAll().add(patient);
        }

        return this.getAll();
    }
}
