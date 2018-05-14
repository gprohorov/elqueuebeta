package com.med.repository.user;

import com.med.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
