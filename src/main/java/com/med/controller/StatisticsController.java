package com.med.controller;

import java.time.LocalDate;
import java.util.List;

import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.Patient;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.model.statistics.dto.doctor.DoctorCurrentStatistics;
import com.med.model.statistics.dto.doctor.DoctorPercent;
import com.med.model.statistics.dto.doctor.DoctorProcedureZoneFee;
import com.med.model.statistics.dto.general.GeneralStatisticsDTO;
import com.med.model.statistics.dto.patient.DebetorDTO;
import com.med.model.statistics.dto.patient.PatientDTO;
import com.med.model.statistics.dto.procedure.ProcedureStatistics;
import com.med.services.AccountingService;
import com.med.services.CashBoxService;
import com.med.services.StatisticService;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("*")
public class StatisticsController {

    @Autowired
    AccountingService accountingService;

    @Autowired
    StatisticService service;

    @Autowired
    CashBoxService cashBoxService;

    @RequestMapping("/cash/now")
    public Long getCashAvailable() {
       return service.getCashAvailable();
    }

    @RequestMapping("/cash/total")
    public Long getCashTotal() {
        return service.getTotalCash();
    }

    //  Готивка в кассе:  вывдится три числа 1. Сколько уже есть
    //                                       2. Сколько уже насчитано за процедуры всем пацам
    //                                       3. Какой процент процедур сделано по отнош. к назначенным
    @RequestMapping("/report/current")
    public AvailableexecutedPart getCurrentReport() {
        return accountingService.getCurrentReport();
    }
    
    // Лекари - загальна колькость процедур
    // Выводится по каждому врачу:
    //   1. Имя доктора
    //   2. Кол-во процедур
    //   3. Кол-во зон
    //   4. НАработанных денег
    @RequestMapping("/doctors/{start}/{finish}")
    public List<DoctorProcedureZoneFee> getDoctorsStatistics(
              @PathVariable(value = "start") String start,
              @PathVariable(value = "finish") String finish) {
    	return service.getDoctorsProceduresFromTo(LocalDate.parse(start), LocalDate.parse(finish));
    }

    @RequestMapping("/doctors/current/{date}")
    public List<DoctorCurrentStatistics> getDoctorsCurrentStatistics(
		@PathVariable(value = "date") String date) {
    	return service.getDoctorsListCurrentStatictics(LocalDate.parse(date));
    }

    @RequestMapping("/procedures/count")
    public Long getProceduresCount() {
        return service.getAllProceduresCount();
    }

    @RequestMapping("/patients/count")
    public Long getPatientsCount() {
        return service.getAllPatientsCount();
    }

    // Боржники
    @RequestMapping("/patients/debetors")
    public List<Patient> getPatientsDebets() {
        return service.getAllDebtors();
    }
    
    // Боржники (розширено)
    @RequestMapping("/patients/debetors-ext/{start}/{finish}")
    public List<DebetorDTO> getPatientsDebetsExt(
        @PathVariable(value = "start") String start,
        @PathVariable(value = "finish") String finish) {
    	return service.getAllDebtorsExt(LocalDate.parse(start), LocalDate.parse(finish));
    }

    // Пациенты extended
    @RequestMapping("/patients/ext/{start}/{finish}")
    public List<PatientDTO> getPatientsExt(
        @PathVariable(value = "start") String start,
        @PathVariable(value = "finish") String finish) {
        return service.getPatientsStatistics(LocalDate.parse(start), LocalDate.parse(finish));
    }

    @RequestMapping("/procedures/{start}/{finish}")
    public List<ProcedureStatistics> getProceduresStatistics(
        @PathVariable(value = "start") String start,
        @PathVariable(value = "finish") String finish) {
        return service.getProceduresStatistics(LocalDate.parse(start), LocalDate.parse(finish));
    }

    @RequestMapping("/patient/{id}/{begin}/{end}")
    public PatientDTO getPatientStatistics(
        @PathVariable(value = "id") String id,
        @PathVariable(value = "begin") String begin,
        @PathVariable(value = "end") String end) {
        return service.getPatientStatistics(id, LocalDate.parse(begin), LocalDate.parse(end));
    }

    @RequestMapping("/patient/list/{begin}/{end}")
    public List<PatientDTO> getPatientsStatistics(
        @PathVariable(value = "begin") String begin,
        @PathVariable(value = "end") String end) {
        return service.getPatientsStatistics( LocalDate.parse(begin), LocalDate.parse(end));
    }

    @RequestMapping("/procedures/{start}/{finish}/{procedureId}")
    public List<DoctorPercent> getProcedureStatisticsByDoctors(
        @PathVariable(value = "start") String start,
        @PathVariable(value = "finish") String finish,
        @PathVariable(value = "procedureId") int procedureId) {
        return service.getProcedureStatisticsByDoctor(
    		LocalDate.parse(start), LocalDate.parse(finish), procedureId);
    }

    @RequestMapping("/general/{day}")
    public GeneralStatisticsDTO getGeneralStatisticsByDay(@PathVariable(value = "day") String day) {
        return service.getGeneralStatisticsDay(LocalDate.parse(day));
    }

    @RequestMapping("/general/list/{start}/{finish}")
    public List<GeneralStatisticsDTO> getGenralStatisticsFromTo(
        @PathVariable(value = "start") String start,
        @PathVariable(value = "finish") String finish) {
        return service.getGeneralStatisticsFromTo(LocalDate.parse(start), LocalDate.parse(finish));
    }
}