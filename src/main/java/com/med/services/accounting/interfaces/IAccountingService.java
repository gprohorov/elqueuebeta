package com.med.services.accounting.interfaces;

import com.med.model.balance.Accounting;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 20.05.18.
 */
@Service
public interface IAccountingService {
    Accounting createAccounting(Accounting accounting);
    Accounting getAccounting(String accountingId);
    List<Accounting> getAll();

    List<Accounting> getAllIncomesForPatientFromTo(String patientId, LocalDate start, LocalDate finish);

}
