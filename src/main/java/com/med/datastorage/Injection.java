package com.med.datastorage;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
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
    SalaryDailyService salaryDailyService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    WorkDayService workDayService;

    private List<Integer> fullTimeList;


    @PostConstruct
    void init() {
        fullTimeList = doctorService.getAllActive().stream()
                .filter(doc -> doc.getProcedureIds().isEmpty())
                .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        fullTimeList.add(2); // для Иры.
        fullTimeList.add(5); // для ЮВ
      //  this.shhwHotelSummary();

 //   this.showExtraction();
     //   this.updateBalance();
      //  this.refreshWorkDay();
      //  this.showAllAfterExtraction();
    }
  private void shhwHotelSummary(){
        LocalDate from = LocalDate.of(2019, Month.JANUARY,1);
        LocalDate to = LocalDate.now();
        int sum = accountingService.getByDateBetween(from,to).stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.HOTEL))
                .mapToInt(Accounting::getSum)
                .sum();
      System.out.println("-------Hotel----- " + sum);
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
    private void showExtraction(){
       cashBoxService.getAllForToday().stream()
               .filter(cashBox -> cashBox.getItemId()!=null)
                .filter(cashBox -> cashBox.getItemId().equals(settingsService.get().getExtractionItemId()))
                .forEach(System.out::println);
    }
    private void updateBalance(){
        Patient patient = patientService.getAllForToday().stream()
                .filter(pat->pat.getBalance()==-2440).findFirst()
                .orElse(null);
        patient.setBalance(-2090);
        System.out.println(patient.getPerson().getFullName());
        patientService.savePatient(patient);
    }
    private void refreshWorkDay(){
        workDayService.setWorkDayFinishValues();
    }

    private void showAllAfterExtraction(){
        LocalDateTime  dateTime = LocalDateTime.of(2019,Month.APRIL,18,16,47);
        List<Accounting> accountings = accountingService.getAllForDate(LocalDate.now())
                .stream()
                .filter(accounting -> accounting.getDateTime().isAfter(dateTime))
                .filter(accounting -> accounting.getPatientId() != null)
                .filter(accounting -> accounting.getSum()>0)
                .collect(Collectors.toList());
        accountings.forEach(accounting -> {
            String id = accounting.getPatientId();
            Patient patient = patientService.getPatient(id);
            String name = patient.getPerson().getFullName();
            int balance = patient.getBalance();

            System.out.println(name + "  " + accounting.getDateTime() + "  "
            + " cymma " + accounting.getSum() + " " + accounting.getPayment()
            + " " + " balance " + balance );



        });
    }
}
