package com.med.services.income.interfaces;

import com.med.model.balance.Income;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 20.05.18.
 */
@Service
public interface IIncomeService {
    Income createIncome(Income income);
    Income getIncome(String incomeId);
    List<Income> getAll();

    List<Income> getAllIncomesForPatienetFromTo(String patientId, LocalDate start, LocalDate finish);

}
