package com.med.services.salarydto.impls;

import com.med.model.SalaryDTO;
import com.med.repository.salary.SalaryRepository;
import com.med.repository.salarydto.SalaryDTORepository;
import com.med.services.salarydto.interfaces.ISalaryDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by george on 07.11.18.
 */
@Service
public class SalaryDTOServiceImpl implements ISalaryDTOService {

    @Autowired
    SalaryDTORepository repository;

    @Override
    public SalaryDTO createSalaryDTO(SalaryDTO salaryDTO) {
        return repository.save(salaryDTO);
    }

    @Override
    public SalaryDTO getSalaryDTOById(String salaryDTOId) {
        return repository.findById(salaryDTOId).orElse(null);
    }

    @Override
    public SalaryDTO getSalaryDTOByWeek(int week) {

      //  LocalDate.now().get

        return null;
    }


}
