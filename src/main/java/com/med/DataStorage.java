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
         assigned.put(massage, 10);
         assigned.put(pulling, 10);
       // usualTherapy.setProgress(assigned);
        therapies.add(primary);
        therapies.add(usualTherapy);


        progres2.put(massage, true);
        progres2.put(pulling, false);

        progres.put(massage,false);
        progres.put(laser,false);
        progres.put(pulling,false);

        patients.add(vasa);
        patients.add(trump);
        patients.add(ivanov);
        patients.add(petrovv);

        patients.get(2).setTherapy(therapies.get(1));
        //patients.get(0).setAssignedProcedures(progres);



    }

    List<Person> persons = new LinkedList<>( Arrays.asList(
            new Person(1,"Іван",  "Іванов"),
            new Person(2,"Петро",  "Петров"),
            new Person(3,"Сидор", "Сидорович",  "Сидоров"
                    , "050-0000000", "Герца",
                    "Головна,5", true, LocalDate.now().minusYears(38).minusDays(20)),
            new Person(4,"Павел", "Павлов"),
            new Person(5,"Василий",  "Васильев"),
            new Person(6,"Леон", "Леонов"),
            new Person(7,"Ілля",  "Івкін"),
            new Person(8,"Барак",  "Хусейнович","Обама", "066 6666666", "Гербедж-Таун"
                    , "Треш-авеню, 6", true, LocalDate.now().minusYears(60)),
            new Person(9,"Олександр ",  "Іванов"),
            new Person(10,"Дональд",  "Фредович","Трамп", "067 7777777", "Вашингтон"
                    , "Білий Дім, 1, кв.1", true, LocalDate.now().minusYears(73))

            ));

    Procedure registration =   new Procedure(1, "Реєстратура", 0,0);
    Procedure diagnostics =   new Procedure(2, "Диагностика", 1,100);
    Procedure laser = new Procedure(3, "Лазерна терапия",2,"",3,50);
    Procedure pulling = new Procedure(5, "Витяжка",5,50);
    Procedure massage = new Procedure(6, "Массаж",3,70, true);
    Procedure massageNo = new Procedure(6, "Массаж",3,70, false);
    Procedure mechmassasge = new Procedure(4, "Механ. массаж",3,"",3,80);
    Procedure heating = new Procedure(7, "Прогрівання",7,"",3,80);

    List<Procedure> procedures = new LinkedList<>( Arrays.asList(

            registration,
            diagnostics,
            laser,
            massage,
            pulling,
            mechmassasge,
            massageNo,
            heating

    ));

    List<Card> cards = new LinkedList<>(Arrays.asList(
            new Card(1, "Min",1)
    ));

    Doctor SERVER = new Doctor(5, "SERVER"
            , "Computer", "Supermicro","IT",null);

    Doctor registrator = new Doctor(1, "Ірина"
            , "Іванівна", "Мельник","Регістратор","0501234577");

    Doctor diagnost = new Doctor(2, "Андрій"
            , "Володимирович", "Нечай","Діагност","0501234577");


    Doctor massagist = new Doctor(3, "Ігор"
            , "Іванович", "Петрюк","Масажист","0501234577");

   Doctor laserist = new Doctor(7, "Іван"
            , "Васильович", "Мандрик","Лазер","0501234577");



    List<Doctor> doctors = new LinkedList<>(Arrays.asList(
            SERVER, registrator, diagnost, massagist, laserist
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
   HashMap<Procedure, Boolean> progres2 = new HashMap<>();
   HashMap<Procedure, Boolean> progres = new HashMap<>();
   LocalDate today = LocalDate.now();

/*
   Therapy standardTherapy = new Therapy(1, LocalDateTime.now().minusDays(2)
           , "Ушиб", 1, assigned,executed);
*/

    private Therapy primary = new Therapy();

    private Therapy usualTherapy = new Therapy(1, LocalDateTime.now()
            ,"Osteohondroz",1, "Шейный участок",
            "url", null);

    private List<Therapy> therapies = new LinkedList<>();



    private List<Procedure> emptyList = new ArrayList<>();
    private List<Procedure> diagAndMassage = new ArrayList<>(
            Arrays.asList(diagnostics, massageNo)
    );

    private List<Procedure> proceduresForToday1 = new ArrayList<>(
            Arrays.asList(massage, laser, pulling)
    );

    private List<Procedure> proceduresForToday2 = new ArrayList<>(
            Arrays.asList(massage, laser, pulling,heating)
    );



    private Patient vasa = new Patient(persons.get(4), primary
            , diagAndMassage
            , Status.SOCIAL, LocalDateTime.now().minusMinutes(35), 0, Activity.ACTIVE) ;

   private Patient trump = new Patient(persons.get(9), primary
            , proceduresForToday1
            , Status.BISSINESS, LocalDateTime.now().minusMinutes(10), 0, Activity.NON_ACTIVE) ;

   private Patient ivanov = new Patient(persons.get(0), null
            , proceduresForToday2
            , Status.SOCIAL, LocalDateTime.now().minusMinutes(20), 0, Activity.NON_ACTIVE) ;


    private Patient petrovv= new Patient(persons.get(1),primary
            ,diagAndMassage,Status.SOCIAL
            , LocalDateTime.now().minusMinutes(11),0,Activity.ACTIVE,Reckoning.END);

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
