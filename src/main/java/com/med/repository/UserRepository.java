package com.med.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {}