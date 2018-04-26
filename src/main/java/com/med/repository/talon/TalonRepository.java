package com.med.repository.talon;

import com.med.model.Talon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface TalonRepository extends MongoRepository<Talon, Long>{


}
