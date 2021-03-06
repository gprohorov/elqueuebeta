package com.med.datastorage;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import com.med.repository.*;
import com.med.repository.hotel.RecordRepository;
import com.med.services.*;
import com.med.services.hotel.ChamberService;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 24.03.19.
 */
@Service
public class Injection {

    @Autowired
    TalonRepository talonRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    AccountingRepository accountingRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    SalaryDTORepository salaryDTORepository;

    @Autowired
    SalaryDailyRepository salaryDailyRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    TherapyRepository therapyRepository;

    @Autowired
    SalaryDailyService salaryDailyService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    WorkWeekService weekService;


    @PostConstruct
    void init() {
    }

  //  @Scheduled(cron = "0 5 23 * * *")
    void showPats(){
        System.out.println("------------------------------------------");

        patientRepository.findAll().stream()
                .filter(patient -> patient.getPerson().getCellPhone()!=null)
                .filter(patient -> patient.getPerson().getCellPhone().contains("05 52"))
                .forEach(patient -> System.out.println(patient.getPerson().getFullName().toString()+
                        patient.getPerson().getCellPhone()

                ));


//
//        patientRepository.findAll().stream()
//                .filter(patient -> patient.getPerson().getCellPhone().contains("4"))
//


    }





}
