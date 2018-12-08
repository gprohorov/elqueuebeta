package com.med.repository.card;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.med.model.Card;

@Repository
public interface CardRepository extends MongoRepository<Card, Integer> {
    Card findByProcedureId(int procedureId);
}