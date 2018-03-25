package com.med.services.person.impls;

import com.med.dao.person.impls.PersonDAOImpl;
import com.med.dao.person.interfaces.IPersonDAO;
import com.med.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class PersonServiceImpl implements IPersonDAO {


    private List<Person> persons = new ArrayList<>();



    @Autowired
    PersonDAOImpl personDAO;

    @PostConstruct
    void init(){
       // persons = dataStorage.getPersons();
    }


    @Override
    public Person createPerson(Person person) {


        return personDAO.createPerson(person);
    }

    @Override
    public Person updatePerson(Person person) {
        return personDAO.updatePerson(person);
    }

    @Override
    public Person getPerson(int id) {
        return personDAO.getPerson(id);
    }

    @Override
    public Person deletePerson(int id) {
        return personDAO.deletePerson(id);
    }

    @Override
    public List<Person> getAll() {
        return personDAO.getAll();
    }

    @Override
    public List<Person> getPersonListByName(String lastName) {
        return personDAO.getPersonListByName(lastName);
    }

    @Override
    public List<Person> getPersonListByLetters(String letters) {
        return personDAO.getPersonListByLetters(letters);
    }
}
