package com.med.repository.hotel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.hotel.Chamber;

@Repository
public interface ChamberRepository extends MongoRepository<Chamber, Integer> {}