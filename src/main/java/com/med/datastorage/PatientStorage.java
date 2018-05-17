package com.med.datastorage;

import com.med.model.Patient;
import com.med.repository.patient.PatientRepository;
import com.med.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 07.05.18.
 */

@Component
public class PatientStorage {

    List<Patient> patients = new ArrayList<>();

    @Autowired
    PatientRepository repository;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    void init(){
/*        repository.deleteAll();
        this.createPatientList();
        repository.saveAll(patients);

        System.out.println("============================");

        String id ="5af4466df363362f8cc69b5d";
        System.out.println(userRepository.findById(id));
  */

    }
/*

    List<Person> persons = new LinkedList<>( Arrays.asList(
            new Person("Іван",  "Іванов"),
            new Person("Петро",  "Петров"),
            new Person("Сидор", "Сидорович",  "Сидоров"
                    , "050-0000000", "Герца",
                    "Головна,5", true, LocalDate.now().minusYears(38).minusDays(20)),
            new Person("Павел", "Павлов"),
            new Person("Василий",  "Васильев"),
            new Person("Леон", "Леонов"),
            new Person("Ілля",  "Івкін"),
            new Person("Барак",  "Хусейнович","Обама", "066 6666666", "Гербедж-Таун"
                    , "Треш-авеню, 6", true, LocalDate.now().minusYears(60)),
            new Person("Олександр ",  "Іванов"),
            new Person("Дональд",  "Фредович","Трамп", "067 7777777", "Вашингтон"
                    , "Білий Дім, 1, кв.1", true, LocalDate.now().minusYears(73)),
            new Person("Павел", "Майков"),
            new Person("Василий",  "Ливанов"),
            new Person("Леон", "Киллер"),
            new Person("Ілля",  "Муромец"),
            new Person("Павел", "Куракин"),
            new Person("Василий",  "Шукшин"),
            new Person("Евгений", "Леонов"),
            new Person("Иван",  "Никитин"),
            new Person("Юрий", "Шевчук"),
            new Person("Василий",  "Лановой"),
            new Person("Игнат", "Лобов"),
            new Person("Руслан",  "Аушев"),
            new Person("Алексей", "Баталов"),
            new Person("Леонид",  "Харитонов"),
            new Person("Игорь", "Строев"),
            new Person("Ілля",  "Івашов"),
            new Person("Василий",  "Бубка"),
            new Person("Евгений", "Онегин"),
            new Person("Юрий", "Продан"),
            new Person("Василий",  "Чапаев"),
            new Person("Игнат", "Ложкин"),
            new Person("Руслан",  "Бахтияров"),
            new Person("Алексей", "Новиков"),
            new Person("Леонид",  "Леонидов"),
            new Person("Игорь", "Мамчин"),
            new Person("Иван",  "Силаев"),
            new Person("Ілля",  "Куц"),
            new Person("Дмитрий", "Донской"),
            new Person("Владимир",  "Мономах"),
            new Person("Алексей", "Крутов"),
            new Person("Леонид",  "Брежнев"),
            new Person("Игорь", "Кривин"),
            new Person("Иван",  "Калита"),
            new Person("Xopxe",  "Tuxepoc")
    ));

*/

/*

    private List<Patient> createPatientList(){
        persons.stream().forEach(person -> patients.add(new Patient(person)));
        return this.patients;
    }

*/




/*        patients.stream().forEach(patient
                -> patient.setTalons(talonService
                .getAllTalonsForPatient(patient.getId(), LocalDate.now())));



       repository.deleteAll();
       repository.saveAll(patients);





*/

}
