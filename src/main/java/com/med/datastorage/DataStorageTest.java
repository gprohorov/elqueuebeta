package com.med.datastorage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.med.model.Salary;
import com.med.model.SalaryDTO;
import com.med.model.SalaryType;
import com.med.repository.TalonRepository;
import com.med.services.AccountingService;
import com.med.services.PatientService;
import com.med.services.ProcedureService;
import com.med.services.SalaryDTOService;
import com.med.services.SalaryService;
import com.med.services.hotel.ChamberService;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;

@Configuration
public class DataStorageTest {

    @Autowired
    TalonRepository talonRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    ChamberService chamberService;

    @Autowired
    KoikaService koikaService;

    @Autowired
    RecordService recordService;

    @Autowired
    AccountingService accountingService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    SalaryService salaryService;

    @Autowired
    SalaryDTOService salaryDTOService;

    public void launch() {}

    public void taskOne() {
    	/*
        // salaryService.createWeekSalary();
        List<SalaryDTO> list = salaryService.getSalaryList();
        LocalDate from = LocalDate.of(2018,10,29);
        LocalDate to = LocalDate.of(2018,11,3);
        LocalDateTime opened = LocalDateTime.now().minusDays(7);
        int week =  to.getDayOfYear()/7;

        list.stream().forEach(item-> {

        //    item.setFrom(from);
        //    item.setTo(to);
       //     item.setWeek(week);
       //     item.setOpened(opened);
         //   salaryDTOService.createSalaryDTO(item);
        });
		*/
    }

    public List<SalaryDTO> taskTwo() {
        return salaryDTOService.createNewTable();
    }

    public void reset() { }

    public void taskTree(int doctorId) {
	    List<Salary> list = salaryService.getAll().stream()
    		.filter(salary -> salary.getDoctorId()==doctorId)
	        .filter(salary -> salary.getType().equals(SalaryType.BUZUNAR))
            .collect(Collectors.toList());
	    list.stream().forEach(salary -> {
	        System.out.println(salary.getDateTime().toLocalDate() + "  " + 
        		salary.getDateTime().getHour() + ":" +
                salary.getDateTime().getMinute() + ":" +
                salary.getDateTime().getSecond() + ":" + ", Sum: " + salary.getSum());
	    });
        System.out.println(list.stream().mapToInt(Salary::getSum).sum());
    }
    
    public void resetPatientsTable() {
        System.out.println("Talon table updated");
    }
}