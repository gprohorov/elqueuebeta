package com.med.security.repository;

import com.med.security.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{

}
