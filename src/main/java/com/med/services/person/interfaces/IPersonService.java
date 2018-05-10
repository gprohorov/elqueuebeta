package com.med.services.person.interfaces;

import com.med.model.Person;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IPersonService {


    Person createPerson(Person person);

    Person updatePerson(Person person);

    Person getPerson(int id);

    Person deletePerson(int id);

    List<Person> getAll();

    List<Person> getPersonListByName(String lastName);

    List<Person> getPersonListByLetters(String letters);

}
