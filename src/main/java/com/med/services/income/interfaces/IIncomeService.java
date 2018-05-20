package com.med.services.income.interfaces;

import com.med.model.Income;

import java.util.List;

/**
 * Created by george on 20.05.18.
 */

public interface IIncomeService {
    Income createIncome(Income income);
    Income getIncome(String incomeId);
    List<Income> getAll();

}
