package com.med.repository.hotel;

import com.med.model.hotel.Chamber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface ChamberRepository extends MongoRepository<Chamber, Integer>{

}
