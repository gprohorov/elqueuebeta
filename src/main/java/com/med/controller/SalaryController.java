package com.med.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.med.model.*;
import com.med.services.SalaryDailyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import com.med.services.CashBoxService;
import com.med.services.SalaryDTOService;
import com.med.services.SalaryService;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin("*")
public class SalaryController {

    @Autowired
    SalaryService service;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    SalaryDTOService salaryDTOService;

    @Autowired
    SalaryDailyService salaryDailyService;

    @RequestMapping("/list/old")
    public List<SalaryDTO> showSalaries() {
        return service.getSalaryList();
    }
    
    @RequestMapping("/list")
    public List<SalaryDTO> showSalariesNew() {
    	return salaryDTOService.getOpenTable();
    }

    // a doctor get a salary into buzunar from kasa
    @RequestMapping("/get")
    public Response paySalary(@Valid @RequestBody Salary salary) {
        return service.paySalary(salary);
    }
    
    // a doctor get a salary into buzunar from nechay personally
    @RequestMapping("/get-sa")
    public Response paySalarySA(@Valid @RequestBody Salary salary) {
    	return service.paySalarySA(salary);
    }

    // i.e. nechay  insert bonus or a penalty
    @RequestMapping("/create")
    public Salary createSalary(@Valid @RequestBody Salary salary) {
        return service.createSalary(salary);
    }

    // !!! Do it !!!
    @RequestMapping("/set")
    public void insertPenalty(@Valid @RequestBody AwardPenaltyDTO dto) {
        if (dto.getAward() != 0) {
            Salary salary = new Salary(
                    dto.getDoctorID()
                    , LocalDateTime.now()
                    , SalaryType.AWARD
                    , dto.getAward());
            service.createSalary(salary);
            SalaryDTO record = salaryDTOService.getAll().stream()
                    .filter(row->row.getDoctorId()==dto.getDoctorID())
                    .filter(row->row.getClosed()==null)
                    .findAny().get();
            record.setAward(record.getAward() + dto.getAward());
            salaryDTOService.updateSalaryDTO(record);
        }

        if (dto.getPenalty() != 0 ) {
            Salary salary = new Salary(
                    dto.getDoctorID()
                    , LocalDateTime.now()
                    , SalaryType.PENALTY
                    , dto.getPenalty());
            service.createSalary(salary);
            SalaryDTO record = salaryDTOService.getAll().stream()
                    .filter(row->row.getDoctorId()==dto.getDoctorID())
                    .filter(row->row.getClosed()==null)
                    .findAny().get();
            record.setPenalty(record.getPenalty() + dto.getPenalty());
            salaryDTOService.updateSalaryDTO(record);
        }
    }
    
    // 17 nov
    @RequestMapping("/list/summary/{from}/{to}")
    public List<SalaryDTO> showSummarySalaries(
        @PathVariable(value = "from") String from,
        @PathVariable(value = "to") String to) {
        return salaryDTOService.getSummarySalaryList(LocalDate.parse(from), LocalDate.parse(to));
    }
    
    @RequestMapping("/doctor/summary/{doctor}/{from}/{to}")
    public SalaryDTO showDoctorSummarySalary(
		@PathVariable(value = "doctor") int doctorId,
		@PathVariable(value = "from") String from,
		@PathVariable(value = "to") String to) {
    	return salaryDTOService.getDoctorSummarySalary(doctorId, LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/list/{week}")
    public List<SalaryDTO> showSalariesByWeek(@PathVariable(value = "week") int week) {
        return salaryDTOService.getTableByWeek(week);
    }

    @RequestMapping("/list/payment/{doctor}/{from}/{to}")
    public List<CashBox> getPaymentsForDoctor(
        @PathVariable(value = "doctor") int doctorId,
        @PathVariable(value = "from") String from,
        @PathVariable(value = "to") String to) {
		return service.getPaymentsByDoctor(doctorId, LocalDate.parse(from), LocalDate.parse(to));
    }
/*
    @RequestMapping("/inject")
    public List<SalaryDTO> inject() {
        return salaryDTOService.inject();
    }
    */
    @RequestMapping("/doctor/{doctorId}/{from}/{to}")
    public DoctorPeriodSalary getDoctorSalaryForPeriod(
        @PathVariable(value = "doctorId") int doctorId,
        @PathVariable(value = "from") String from,
        @PathVariable(value = "to") String to) {
        return salaryDTOService.getDoctorSalaryForPeriod(doctorId, LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/doctor/preview")
    public DoctorPeriodSalary getDoctorSalaryPreview(@Valid @RequestBody String data) {
        return salaryDTOService.getDoctorSalaryByJSON(new JSONObject(data));
    }
    
    @RequestMapping("/doctor/preview/save")
    public void saveDoctorSalaryPreview(@Valid @RequestBody String data) {
    	salaryDTOService.saveDoctorSalaryByJSON(new JSONObject(data));
    }
    
    @RequestMapping("/date/{date}")
    public List<SalaryDaily> getSalariesForDate(@PathVariable(value = "date") String date) {
        int hour = 19; // 19.00 the time when salaries are written to base
        if (LocalDate.parse(date).equals(LocalDate.now()) && LocalDateTime.now().getHour()<hour) {
            return salaryDailyService.showCurrentSalariesForToday();
        }
        return salaryDailyService.getSalariesForDate(LocalDate.parse(date));
    }
    
    @RequestMapping("/daily/{from}/{to}")
    public List<SalaryDaily> getSalariesForPeriod(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to) {
        return salaryDailyService.getSalarySummaryForPeriod(
                LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/payroll/{from}/{to}")
    public List<PermanentPayroll> getPayrollForPeriod(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to) {
        return salaryDailyService.getPermanentPayrollFromTo(
                LocalDate.parse(from), LocalDate.parse(to));
    }


}