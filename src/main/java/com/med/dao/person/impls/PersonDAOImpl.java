package com.med.dao.person.impls;

import com.med.DataStorage;
import com.med.dao.person.interfaces.IPersonDAO;
import com.med.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class PersonDAOImpl implements IPersonDAO {


  //  private List<Person> gersons = new ArrayList<>();

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
       // gersons = dataStorage.getPersons();
    }

    //Crud
    @Override
    public Person createPerson(Person person) {
        dataStorage.getPersons().add(person);
        return person;
    }

    //cRud
    @Override
    public Person getPerson(int id) {

        return this.getAll().stream()
                .filter(person -> person.getId()==id).findFirst().get();
    }
    //crUd
    @Override
    public Person updatePerson(Person person) {
        if (person.getId()==0){
            int persoId = this.getAll().stream().mapToInt(el->el.getId())
                    .max().getAsInt() + 1;
            person.setId(persoId);
            this.createPerson(person);
        }
        else {
        Person oldValues = this.getPerson(person.getId());
       int index = this.getAll().indexOf(oldValues);
       this.getAll().set(index,person);
        }
        return person;
    }

    //cruD
    @Override
    public Person deletePerson(int id) {
        Person person = this.getPerson(id);
        int index = dataStorage.getPersons().indexOf(person);
        dataStorage.getPersons().remove(index);
        return person;
    }

    //getAll
    @Override
    public List<Person> getAll() {
        return dataStorage.getPersons();
    }

    // by name
    @Override
    public List<Person> getPersonListByName(String lastName) {
        return  this.getAll().stream()
                .filter(person -> person.getLastName()
                .equals(lastName))
                .collect(Collectors.toList());
    }
        // by letters
  @Override
    public List<Person> getPersonListByLetters(String letters) {
        if (letters.equals("")||letters.equals(null)){
            return this.getAll();}
        else {
        return  this.getAll().stream()
                .filter(person -> person.getLastName()
                .contains(letters))
                .collect(Collectors.toList());
        }
    }


}
