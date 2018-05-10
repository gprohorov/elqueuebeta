package com.med.repository.therapy;

import com.med.model.Therapy;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface TherapyRepository extends MongoRepository<Therapy, ObjectId>{}
