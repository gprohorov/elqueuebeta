package com.med.services.salary.interfaces;

import com.med.model.Salary;
import com.med.model.SalaryDTO;

import java.util.List;

/**
 * Created by george on 28.10.18.
 */
public interface ISalaryService {
    Salary createSalary(Salary salary);
    List<Salary> createWeekSalaryForDoctor(int doctorId);

    List<Salary> createWeekSalary();
    Salary getSalary(String salaryId);
    SalaryDTO getSalaryByDoctor(int doctorId);
    List<SalaryDTO> getSalaryList();

}
