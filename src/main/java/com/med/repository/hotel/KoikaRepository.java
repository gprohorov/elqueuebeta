package com.med.repository.hotel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.hotel.Koika;

@Repository
public interface KoikaRepository extends MongoRepository<Koika, Integer> {}