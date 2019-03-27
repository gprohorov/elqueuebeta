package com.med.datastorage;

import com.med.model.Doctor;
import com.med.model.SalaryDaily;
import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import com.med.repository.TalonRepository;
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

    @Autowired
    DoctorService doctorService;

    private List<Integer> fullTimeList;


    @PostConstruct
    void init() {
        fullTimeList = doctorService.getAllActive().stream()
                .filter(doc -> doc.getProcedureIds().isEmpty())
                .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        fullTimeList.add(2); // для Иры.
        fullTimeList.add(5); // для ЮВ


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
  //  @Scheduled(cron = "0 5 23 * * *")
    private void getDaysOff(){
        List<Doctor> doctors = doctorService.getAllActiveDoctors().stream()
                .filter(doctor -> (!fullTimeList.contains(doctor)))
                .collect(Collectors.toList());
        LocalDate from = LocalDate.of(2019, Month.JANUARY,1);
        LocalDate to = LocalDate.now();
        for (Doctor doctor:doctors) {
            List<SalaryDaily> salaries = salaryDailyService
                    .getSalaryListForPeriodForDoctor(from,to, doctor.getId());
            int daysOff = (int) salaries.stream()
                    .filter(salary ->!salary.getDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) )
                    .filter(salary ->!salary.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) )
                    .filter(salary ->salary.getBonuses() == 0 )
                    .count();
            System.out.println(doctor.getFullName()+" " + daysOff);
        }

    }
}
