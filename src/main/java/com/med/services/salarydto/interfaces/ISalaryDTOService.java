package com.med.services.salarydto.interfaces;

import com.med.model.SalaryDTO;


import java.util.List;

/**
 * Created by george on 28.10.18.
 */
public interface ISalaryDTOService {
    SalaryDTO createSalaryDTO(SalaryDTO salaryDTO);
    SalaryDTO getSalaryDTOById(String salaryDTOId);
    SalaryDTO getSalaryDTOByWeek(int week);



}
