package com.med.repository.procedure;

import com.med.model.Procedure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface ProcedureRepository extends MongoRepository<Procedure, Integer>{


}
