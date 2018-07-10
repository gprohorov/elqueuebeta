package com.med.repository.card;

import com.med.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface CardRepository extends MongoRepository<Card, Integer>{
    Card findByProcedureId(int procedureId);

}
