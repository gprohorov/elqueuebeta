package com.med.services.person.impls;

import com.med.model.Person;
import com.med.repository.person.PersonRepository;
import com.med.services.person.interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Component
public class PersonServiceImpl implements IPersonService {


    private List<Person> persons = new ArrayList<>();

    @Autowired
    PersonRepository repository;

/*

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
        persons = dataStorage.getPersons();
        repository.saveAll(persons);
    }

*/

    @Override
    public Person createPerson(Person person) {
        if (person.getId()==0) {
            int id = this.getAll().stream().mapToInt(Person::getId).max().getAsInt() + 1;
            person.setId(id);
        }
        return repository.insert(person);
    }

    @Override
    public Person updatePerson(Person person) {
        if (person.getId()==0) {
            int id = this.getAll().stream().mapToInt(Person::getId).max().getAsInt() + 1;
            person.setId(id);
        }

        return repository.save(person);
    }

    @Override
    public Person getPerson(int id) {
        return
                repository.findById(id).get();
    }

    @Override
    public Person deletePerson(int id) {
        Person person = this.getPerson(id);
        repository.deleteById(id);
        return person;
    }

    @Override
    public List<Person> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Person> getPersonListByName(String lastName) {

        return repository.findAll().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonListByLetters(String letters) {

        return repository.findAll().stream()
                .filter(person -> person.getLastName().contains(letters))
                .collect(Collectors.toList());

    }
}
