package com.med.datastorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.med.model.CashBox;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.med.model.Salary;
import com.med.model.SalaryDTO;
import com.med.model.SalaryType;
import com.med.repository.TalonRepository;
import com.med.services.hotel.ChamberService;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;

import javax.annotation.PostConstruct;

@Configuration
public class DataStorageTest {

    @Autowired
    TalonRepository talonRepository;

    @Autowired
    TalonService talonService;

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

    @Autowired
    CashBoxService cashBoxService;

    @PostConstruct
    void init(){
     //   System.out.println("--------------  history ---------------");
     //   this.getHistory();
    //    this.findNedostachu();
    };

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

    private  void getHistory(){

        String patId = patientService.getDebetors().stream()
                .filter(patient -> patient.getBalance() == -1010)
                .findFirst().get().getId();
        System.out.println(patientService.getPatient(patId).getPerson().getFullName());
        List<Accounting> accountings = accountingService.getByPatientId(patId);

        accountings.forEach(accounting -> {

            System.out.println(accounting.getDate() + "  " + accounting.getDesc()  + "    "+ accounting.getSum());
        });
        System.out.println(accountings.stream().mapToInt(Accounting::getSum).sum());

    }
    private void findNedostachu(){
        LocalDate date = LocalDate.now().minusDays(1);
        System.out.println(date);
/*        String patId = accountingService.getAllForDate(date)
                .stream()
                .filter(accounting -> accounting.getSum() == 350)
                .findFirst().get().getPatientId();
       System.out.println(patId + "  "+patientService.getPatient(patId).getPerson().getFullName());


        System.out.println(cashBoxService.getAll().stream()
                .filter(item -> item.getDateTime().toLocalDate().equals(date))
                .filter(item -> item.getSum()==900)
              .findFirst().get().getPatientId()

        );*/

        System.out.println(accountingService.getAllForDate(date)
                .stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.CARD))
                .mapToInt(Accounting::getSum)
                .sum());
        System.out.println(cashBoxService.getAll().stream()
                .filter(item -> item.getDateTime().toLocalDate().equals(date))
                .filter(item -> item.getSum() > 0)
                .mapToInt(CashBox::getSum)
                .sum());


    }



}