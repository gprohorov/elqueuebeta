package com.med.datastorage;

import com.med.model.*;
import com.med.repository.doctor.DoctorRepository;
import com.med.repository.patient.PatientRepository;
import com.med.repository.person.PersonRepository;
import com.med.repository.procedure.ProcedureRepository;
import com.med.repository.talon.TalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by george on 3/9/18.
 */



@Configuration
public class DataStorageTest {


    @Autowired
    PatientRepository patientRepository;



	@Autowired
    DoctorRepository doctorRepository;

	@Autowired
    ProcedureRepository procedureRepository;
	
	@Autowired
    PersonRepository personRepository;

	@Autowired
    TalonRepository talonRepository;


    @PostConstruct
           void init(){
  /*        assigned.put(massage, 10);
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

        patients.get(2).setTherapy(therapies.get(1));*/
        //patients.get(0).setAssignedProcedures(progres);
      //  patientRepository.deleteAll();
      //  patientRepository.saveAll(patients);

     //   patients.clear();

   // talonService.deleteAll();
   //  talons.stream().forEach(talon -> talonService.createTalon(talon));
    //    talonService.


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
                    , "Білий Дім, 1, кв.1", true, LocalDate.now().minusYears(73)),
           new Person(11,"Павел", "Майков"),
           new Person(12,"Василий",  "Ливанов"),
           new Person(13,"Леон", "Киллер"),
           new Person(14,"Ілля",  "Муромец"),
           new Person(15,"Павел", "Куракин"),
           new Person(16,"Василий",  "Шукшин"),
           new Person(17,"Евгений", "Леонов"),
           new Person(18,"Иван",  "Никитин"),
           new Person(19,"Юрий", "Шевчук"),
           new Person(20,"Василий",  "Лановой"),
           new Person(21,"Игнат", "Лобов"),
           new Person(22,"Руслан",  "Аушев"),
           new Person(23,"Алексей", "Баталов"),
           new Person(24,"Леонид",  "Харитонов"),
           new Person(25,"Игорь", "Строев"),
           new Person(26,"Ілля",  "Івашов"),
            new Person(27,"Василий",  "Бубка"),
            new Person(28,"Евгений", "Онегин"),
            new Person(29,"Юрий", "Продан"),
            new Person(30,"Василий",  "Чапаев"),
            new Person(31,"Игнат", "Ложкин"),
            new Person(32,"Руслан",  "Бахтияров"),
            new Person(33,"Алексей", "Новиков"),
            new Person(34,"Леонид",  "Леонидов"),
            new Person(35,"Игорь", "Мамчин"),
            new Person(36,"Иван",  "Силаев"),
            new Person(37,"Ілля",  "Куц"),
            new Person(38,"Дмитрий", "Донской"),
            new Person(39,"Владимир",  "Мономах"),
            new Person(40,"Алексей", "Крутов"),
            new Person(41,"Леонид",  "Брежнев"),
            new Person(42,"Игорь", "Кривин"),
        //    new Person(43,"Иван",  "Калита"),
            new Person(344,"Дмитрий",  "Гук")
         ));


    Procedure registration =   new Procedure(1, "Registration",100);
    Procedure diagnostics =   new Procedure(2, "Diagnostics",100);
    Procedure manual = new Procedure(3, "Manual therapy",100);
    Procedure pulling = new Procedure(4, "Dry pull",100);
    Procedure mechmassasge = new Procedure(5, "Mechanical massage",100);
    Procedure massage = new Procedure(6, "Manual massage",100);
    Procedure ultrasound = new Procedure(7, "USound",100);







    ///--------------------------------------------------------------------------------------

    Procedure laserforTrump = new Procedure(3, "Лазерна терапия",50);
    Procedure pullingforTrump = new Procedure(5, "Витяжка",50);
    Procedure massageForTrump = new Procedure(6, "Массаж",70);

    Procedure laserforIvanov = new Procedure(3, "Лазерна терапия",50);
    Procedure pullingforIvanov = new Procedure(5, "Витяжка",50);
    Procedure massageForIvanov = new Procedure(6, "Массаж",70);

    Procedure massageforPetrov = new Procedure(6, "Массаж",70);
    Procedure heatingForPetrov = new Procedure(7, "Прогрівання",80);

    Procedure massageforVas = new Procedure(6, "Массаж",70);
    Procedure diagnosticsForVas =   new Procedure(2, "Диагностика", 100);
    Procedure registrationForVas =   new Procedure(1, "Реєстратура", 0);



    List<Procedure> procedures = new LinkedList<>( Arrays.asList(

            registration,
            diagnostics,
            manual,
            massage,
            pulling,
       ultrasound
         //   massageNo,


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
          //  SERVER,
            registrator, diagnost, massagist, laserist
    ));






   Map<Procedure, Integer> assigned = new HashMap<Procedure, Integer>();
   HashMap<Procedure, Boolean> progres2 = new HashMap<>();
   HashMap<Procedure, Boolean> progres = new HashMap<>();
   LocalDate today = LocalDate.now();

/*
   Therapy standardTherapy = new Therapy(1, LocalDateTime.now().minusDays(2)
           , "Ушиб", 1, assigned,executed);
*/

    private Therapy primary =null;

    Procedure massage5 = new Procedure(6,"Массаж", 5);
    Procedure heating5 = new Procedure(7,"Прогрівання", 5);

    List<Procedure> testList = new ArrayList<>(
            Arrays.asList(massage5, heating5)
    );

    private Therapy usualTherapy = null;
/*

            new Therapy(null, LocalDateTime.now()
            ,"Osteohondroz",1, "Шейный участок",
            "url", testList);
*/

    private Therapy therapy1 = null;
/*
            new Therapy(2, LocalDateTime.now()
            , "Грижа", 77
            ," поперек", "url:", this.schema4()
    );

*/

    private List<Therapy> therapies = new ArrayList<>(
            Arrays.asList(usualTherapy, therapy1)
    );


    public List<Therapy> getTherapies() {
        return therapies;
    }

    private List<Procedure> emptyList = new ArrayList<>();

    private List<Procedure> proceduresForTrump = new ArrayList<Procedure>(
           Arrays.asList( laserforTrump, pullingforTrump,massageForTrump)
    );

    private List<Procedure> proceduresForIvanov = new ArrayList<Procedure>(
           Arrays.asList( laserforIvanov, pullingforIvanov, massageForIvanov)
    );

     private List<Procedure> proceduresForPetrov = new ArrayList<Procedure>(
           Arrays.asList( heatingForPetrov, massageforPetrov)
    );

     private List<Procedure> proceduresForVas = new ArrayList<Procedure>(
           Arrays.asList( registrationForVas, diagnosticsForVas, massageforVas)
    );

    private List<Procedure> schema1(){
        return new ArrayList<>(
        Arrays.asList(
                new Procedure(6,"Массаж", 5)
                ,new Procedure(7,"Прогрівання", 5)
                ,new Procedure(5, "Витяжка",50)
                ));
    }
     private List<Procedure> schema2(){
        return new ArrayList<>(
        Arrays.asList(
                 new Procedure(3, "Лазерна терапия",50)
                ,new Procedure(5, "Витяжка",50)
                ,new Procedure(6,"Массаж", 5)
                ));
    }

    private List<Procedure> schema3(){
        return new ArrayList<>(
        Arrays.asList(
                 new Procedure(3, "Лазерна терапия",50)
                ,new Procedure(4, "Механ. массаж",80)
                ,new Procedure(6,"Массаж", 5)
                ));
    }

      private List<Procedure> schema4(){
        return new ArrayList<>(
        Arrays.asList(
                new Procedure(2, "Диагностика", 100)
                , new Procedure(4, "Механ. массаж",80)

                ));
    }






/*

    Procedure laserforTrump = new Procedure(3, "Лазерна терапия",2,"",3,50);
    Procedure pullingforTrump = new Procedure(5, "Витяжка",5,50);
    Procedure massageForTrump = new Procedure(6, "Массаж",3,70);

*/




    private List<Procedure> procDiag = new ArrayList<>(
            Arrays.asList(diagnostics)
    );

    private List<Procedure> proceduresIvanov = new ArrayList<>(
            Arrays.asList(massage, ultrasound, pulling)
    );

    private List<Procedure> proceduresForToday2 = new ArrayList<>(
            Arrays.asList(massage, ultrasound, pulling,mechmassasge)
    );



    private Patient vasa = new Patient(persons.get(4), null
            , proceduresForVas
            , Status.SOCIAL, LocalDateTime.now().minusMinutes(60), LocalDateTime.now().minusMinutes(120),0, Activity.ACTIVE) ;

   private Patient trump = new Patient(persons.get(9), null
            , proceduresForTrump
            , Status.BUSINESS, LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(99), 0, Activity.NON_ACTIVE) ;


   private Patient ivanov = new Patient(persons.get(0), null
            , proceduresForIvanov
            , Status.SOCIAL, LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(79),0, Activity.NON_ACTIVE) ;



    private Patient petrovv= new Patient(persons.get(1),null
            ,proceduresForPetrov,   Status.SOCIAL
            , LocalDateTime.now().minusMinutes(11),LocalDateTime.now().minusMinutes(77),0,Activity.ACTIVE);

    private List<Patient> patients = new LinkedList<>(
            Arrays.asList(
                    new Patient(persons.get(12), null, this.schema2()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(12)
                    , LocalDateTime.now().minusMinutes(111),0, Activity.ACTIVE)

                    ,  new Patient(persons.get(13), null, this.schema1()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(2)
                            ,LocalDateTime.now().minusHours(2), 0, Activity.ACTIVE)

                    ,  new Patient(persons.get(24), null, this.schema2()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(22)
                            , LocalDateTime.now().minusMinutes(123),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(14), null, this.schema3()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(31)
                            , LocalDateTime.now().minusMinutes(222),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(15), null, this.schema4()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(4)
                            , LocalDateTime.now().minusMinutes(156),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(16), null, this.schema2()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(46)
                            , LocalDateTime.now().minusMinutes(241),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(17), null, this.schema2()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(3)
                            , LocalDateTime.now().minusMinutes(218),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(18), null, this.schema1()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(13)
                            , LocalDateTime.now().minusMinutes(180),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(19), null, this.schema4()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(61)
                            , LocalDateTime.now().minusMinutes(199),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(20), null, this.schema2()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(22)
                            , LocalDateTime.now().minusMinutes(211),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(21), null, this.schema4()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(43)
                            , LocalDateTime.now().minusMinutes(232),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(22), null, this.schema4()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(23)
                            , LocalDateTime.now().minusMinutes(312),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(23), null, this.schema4()
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(23)
                            , LocalDateTime.now().minusMinutes(195),0, Activity.ACTIVE)

            )
    );

///////////////////////////// TAILS  //////////////////////////////////

    private List<Tail> tails = new ArrayList<>();

    public List<Tail> getTails() {
        return tails;
    }

    public void setTails(List<Tail> tails) {
        this.tails = tails;
    }



    ////////////////////////////////////////////////////////////

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


    public void resetPatientsTable(){
/*
       assigned.put(massage, 10);
        assigned.put(pulling, 10);
        // usualTherapy.setProgress(assigned);
        therapies.add(primary);
        therapies.add(usualTherapy);


        progres2.put(massage, true);
        progres2.put(pulling, false);

        progres.put(massage,false);
        progres.put(pulling,false);

        patients.add(vasa);
        patients.add(trump);
        patients.add(ivanov);
        patients.add(petrovv);

        patients.get(2).setTherapy(therapies.get(1));
        //patients.get(0).setAssignedProcedures(progres);
        patientRepository.deleteAll();
        patientRepository.saveAll(patients);

        patients.clear();*/

     // talonRepository.deleteAll();
     //   talons.stream().forEach(System.out::println);
    //    talonRepository.save(talons.get(0));
     //   talonRepository.save(talons.get(1));


        List<Patient> patients = patientRepository.findAll();
        patients.stream().forEach(patient
                -> patient.setLastActivity(LocalDateTime.now().minusMinutes((patient.getId()*3))));

        patients.stream().forEach(patient
                -> patient.setStartActivity(LocalDateTime.now().minusMinutes(patient.getId()*10)));

       patients.get(0).setLastActivity(LocalDateTime.now().minusMinutes(5));
       patients.get(1).setLastActivity(LocalDateTime.now().minusMinutes(15));

        patientRepository.saveAll(patients);

        List<Talon> talons = talonRepository.findAll();
        talons.stream().forEach(talon -> talon.setDate(LocalDate.now()));
        talonRepository.saveAll(talons);

    }



}
