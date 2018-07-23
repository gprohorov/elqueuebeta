package com.med.controller;

import com.med.model.Patient;
import com.med.model.statistics.dto.AvailableexecutedPart;
import com.med.model.statistics.dto.DoctorProcedureZoneFee;
import com.med.model.statistics.dto.ProcedureStatistics;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.statistics.impls.StatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("*")
public class StatisticsController {

    @Autowired
    AccountingServiceImpl accountingService;

    @Autowired
    StatisticServiceImpl service;

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

    	// TODO: Seems to be groupingBy inside is not working propertly. Duplicate doctors in table.
        // Done!
        
    	return service.getDoctorsProceduresFromTo(LocalDate.parse(start), LocalDate.parse(finish));
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


    @RequestMapping("/procedures/{start}/{finish}")
    public List<ProcedureStatistics> getProceduresStatistics(
            @PathVariable(value = "start") String start,
            @PathVariable(value = "finish") String finish) {


        return service.getProcedureStatistics(LocalDate.parse(start), LocalDate.parse(finish));
    }

}
