package com.med.repository.hotel;

import com.med.model.hotel.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface HotelRepository extends MongoRepository<Hotel, String>{
    List<Hotel> findByPatientId(String patientId);

}
