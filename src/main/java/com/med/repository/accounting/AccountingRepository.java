package com.med.repository.accounting;

import com.med.model.balance.Accounting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 15.04.18.
 */
@Repository
public interface AccountingRepository extends MongoRepository<Accounting, String>{

    // db.getCollection('accounting').aggregate([ { $group : { _id : { patientId: ?0 }, balance: {$sum: "$sum"} } } ])
//    @Query("{ $group : { _id : { patientId: ?0 }, balance: {$sum: '$sum'} } }")
//    public Object calcBalance(String patientId);

	List<Accounting> findByPatientId(String patientId);
	List<Accounting> findByDate(LocalDate date);

	
}
