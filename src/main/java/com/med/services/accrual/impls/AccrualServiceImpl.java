package com.med.services.accrual.impls;

import com.med.model.Accrual;
import com.med.repository.accrual.AccrualRepository;
import com.med.services.accrual.interfaces.IAccrualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 18.10.18.
 */

@Service
public class AccrualServiceImpl implements IAccrualService {

    @Autowired
    AccrualRepository repository;

    @Override
    public Accrual createAccrual(Accrual accrual) {
        return repository.save(accrual);
    }

    @Override
    public Accrual getAccrual(String accrualId) {

        return repository.findById(accrualId).orElse(null);
    }

    @Override
    public List<Accrual> getAll() {

        return repository.findAll();
    }
}
