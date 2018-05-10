package com.med.repository.person;

import com.med.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, Integer>{


}
