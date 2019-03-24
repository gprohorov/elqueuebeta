package com.med.datastorage;

import com.med.model.SalaryDaily;
import com.med.repository.TalonRepository;
import com.med.services.*;
import com.med.services.hotel.ChamberService;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by george on 24.03.19.
 */
@Service
public class Injection {

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

    @Autowired
    SalaryDailyService salaryDailyService;

    @PostConstruct
    void init(){
   //  injectCanteenForDoctor();
    }

    private void   injectCanteenForDoctor() {
        LocalDate from = LocalDate.of(2019, Month.MARCH, 11);
        LocalDate to = LocalDate.of(2019, Month.MARCH, 24);
        int doctorId =7;  // Sveta Sorotska

        List<SalaryDaily> list = salaryDailyService
                .getSalaryListForPeriodForDoctor(from, to, 7);

        list.forEach(salaryDaily -> {
            if (salaryDaily.getStavka() == 93) salaryDaily.setStavka(113);
            System.out.println(salaryDaily.getDate());
            salaryDailyService.updateSalaryDaily(salaryDaily);
        });
    }
}
