package com.med.services.accrual.interfaces;

import com.med.model.Accrual;

import java.util.List;

/**
 * Created by george on 18.10.18.
 */
public interface IAccrualService {

    Accrual createAccrual(Accrual accrual);
    Accrual getAccrual(String accrualId);
    List<Accrual> getAll();

}
