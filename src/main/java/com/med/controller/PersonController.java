package com.med.controller;

import com.med.model.Appointment;
import com.med.model.Patient;
import com.med.model.Person;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.person.impls.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonServiceImpl service;

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    PatientServiceImpl patientService;

    // get all persons
    @RequestMapping("/person/list")
    public List<Person> showPersons() {

        return service.getAll();
    }

    // CREATE a new Person
    @PostMapping("/person/create")
    public Person createPerson(@Valid @RequestBody Person person) {

        return service.createPerson(person);
    }

    // READ the Person by id
    @GetMapping("/person/get/{id}")
    public Person showOnePerson(@PathVariable(value = "id") int personId) {

        return service.getPerson(personId);
    }

    // UPDATE the person by id
    @PostMapping("/person/update/{id}")
    public Person updatePerson(@PathVariable(value = "id") int personId,
                               @Valid @RequestBody Person updates) {
        updates.setId(personId);

        return service.updatePerson(updates);

    }

    // DELETE the person by id
    @PostMapping("/person/delete/{id}")
    public Person delPerson(@PathVariable(value = "id") int personId) {

        return service.deletePerson(personId);

    }


    // get a Person list by lastName
    @GetMapping("/person/list/{name}")
    public List<Person> getPersonsByLastName(@PathVariable(value = "name") String lastName) {
        return service.getPersonListByName(lastName);
    }


    // get a Person list by the firstletters
    @GetMapping("/person/list/contains/{letters}")
    public List<Person> getPersonListByLetters(@PathVariable(value = "letters") String letters) {
        return service.getPersonListByLetters(letters);
    }



    // appoint person on a date (create patient)
    @PostMapping("/person/appoint/{id}")
    public Appointment appointPersonOnDate(@PathVariable(value = "id") int personId,
                                           @Valid @RequestBody LocalDate date) {
        Person person = service.getPerson(personId);

        return appointmentService.createAppointment(person, date);

    }

    // appoint person on today (stupid)
    @PostMapping("/person/appoint/today/{id}")
    public Appointment appointPersonOnToday(@PathVariable(value = "id") int personId) {
        Person person = service.getPerson(personId);

        return appointmentService.createAppointment(person, LocalDate.now());

    }

    //  person  ->   patient   i.e. insert into Set<Patient> of today
    // this means that the person has no diagnosis and assigned procedures
    @PostMapping("/person/topatient/{id}")
    public Patient putPersonIntoQueue(@PathVariable(value = "id") int personId) {

        Person person = service.getPerson(personId);

        return patientService.createPatient(person);

    }


}
