package com.med;

import com.med.model.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by george on 3/9/18.
 */
@Configuration
public class DataStorage {


    @PostConstruct
            void init(){
         assigned.put(massage, 5);
         assigned.put(pulling, 3);


        executed.put(massage, 0);
        executed.put(pulling, 0);

        progres.put(massage,true);
        progres.put(laser,true);
        progres.put(pulling,true);

        patients.add(vasa);
        patients.add(trump);
        patients.get(0).setAssignedProcedures(progres);



    }

    List<Person> persons = new LinkedList<>( Arrays.asList(
            new Person(1,"Ivan",  "Ivanov"),
            new Person(2,"Petr",  "Petrov"),
            new Person(3,"Sidor", "Sidorovich",  "Sidorini", "050-0000000", "Herza",
                    "Holovna str., 5", true, LocalDate.now().minusYears(38).minusDays(20)),
            new Person(4,"Sasha", "Sashin"),
            new Person(5,"vasa",  "vasin"),
            new Person(6,"pasha", "pashin"),
            new Person(7,"ilia",  "Ivlev"),
            new Person(8,"Barak ",  "Obama"),
            new Person(9,"Benedict ",  "Ivanov")
    ));

    Procedure registration =   new Procedure(0, "Registration", 0);
    Procedure diagnostics =   new Procedure(1, "Diagnostics", 1);
    Procedure laser = new Procedure(2, "Laser",2,"",3);
    Procedure pulling = new Procedure(5, "Pulling",5);
    Procedure massage = new Procedure(3, "Massage",3,"",3);

    List<Procedure> procedures = new LinkedList<>( Arrays.asList(

            registration,
            diagnostics,
            laser,
            massage,
            pulling

    ));

    Doctor SERVER = new Doctor(0, "SERVER"
            , "Computer", "Supermicro","IT",null);

    Doctor registrator = new Doctor(1, "Irina"
            , "Ivanovna", "Melnichuk","Registrator","0501234577");

    Doctor diagnost = new Doctor(2, "Andrew"
            , "Wladimirovich", "Nechay","Diagnost","0501234577");


    Doctor massagist = new Doctor(3, "Igor"
            , "Ivanovich", "Petruk","Massagist","0501234577");



    List<Doctor> doctors = new LinkedList<>(Arrays.asList(
            SERVER, registrator, diagnost, massagist
    ));



   private List<Event> logs = Arrays.asList(
          //  new Event(null, LocalDateTime.now(), null, doctors.get(0),procedures.get(0), Action.PRESENT),
         //   new Event(null, LocalDateTime.now(), patient.get(0), doctors.get(0),procedures.get(0), Action.REGISTRATED),
          //  new Event(null, LocalDateTime.now(), patient.get(0), doctors.get(0),procedures.get(0), Action.PUT_IN_QUEUE)

    );


   private List<Event> events = new ArrayList<>();
           //Arrays.asList(
          //  new Event(null, LocalDateTime.now(), null, doctors.get(0),procedures.get(0), Action.PRESENT),
         //   new Event(null, LocalDateTime.now(), patient.get(0), doctors.get(0),procedures.get(0), Action.REGISTRATED),
          //  new Event(null, LocalDateTime.now(), patient.get(0), doctors.get(0),procedures.get(0), Action.PUT_IN_QUEUE)



   Map<Procedure, Integer> assigned = new HashMap<Procedure, Integer>();
   Map<Procedure, Integer> executed = new HashMap<Procedure, Integer>();
   HashMap<Procedure, Boolean> progres = new HashMap<>();
   LocalDate today = LocalDate.now();

/*
   Therapy standardTherapy = new Therapy(1, LocalDateTime.now().minusDays(2)
           , "Ушиб", 1, assigned,executed);
*/

    private Therapy primary = new Therapy();

    private Patient vasa = new Patient(9, persons.get(4), primary
            , progres
            , Status.SOCIAL, LocalDateTime.now().minusMinutes(15), 0, Activity.NON_ACTIVE) ;

   private Patient trump = new Patient(11, persons.get(7), primary
            , null
            , Status.VIP, LocalDateTime.now().minusMinutes(10), 0, Activity.NON_ACTIVE) ;


    private List<Patient> patients = new LinkedList<>();

    private List<Appointment> appointments = new LinkedList<>( Arrays.asList(
            new Appointment(1, new Patient(persons.get(0)), LocalDate.now().plusDays(1)),
            new Appointment(2, new Patient(persons.get(1)), LocalDate.now()),
            new Appointment(3, new Patient(persons.get(2)), LocalDate.now().plusDays(1)),
            new Appointment(4, new Patient(persons.get(3)), LocalDate.now()),
            new Appointment(5, new Patient(persons.get(3)), LocalDate.now().plusDays(2))
           , new Appointment(6, vasa, LocalDate.now().plusDays(5)
    )));

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Person> patients) {
        this.persons = patients;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Event> getLogs() {
        return logs;
    }

    public List<Event> getEvents() { return events; }

    public void setLogs(List<Event> logs) {
        this.logs = logs;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
