package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Talon;
import com.med.model.workday.WorkDay;
import com.med.repository.WorkDayRepository;

@Service
public class WorkDayService  {
	
    @Autowired
    WorkDayRepository repository;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    TalonService talonService;

    @Autowired
    PatientService patientService;

    @Autowired
    AccountingService accountingService;

    @Autowired
    DoctorService doctorService;

    public WorkDay create(WorkDay workDay) {
        return repository.save(workDay);
    }
    
    public WorkDay create(LocalDate date) {
        return repository.save(new WorkDay(date));
    }
    
    public WorkDay getWorkDay(String id) {
        return repository.findById(id).orElse(new WorkDay());
    }
    
    public WorkDay getWorkDay(LocalDate date) {
        return repository.findByDate(date).orElse(new WorkDay(date));
    }
    
    public WorkDay update(WorkDay workDay) {
        return repository.save(workDay);
    }
    
    @Scheduled(cron = "0 0 7 * * *")
    public void initWorkDay(){
        WorkDay workDay = new WorkDay(LocalDate.now());
        workDay.setSumAtStart(cashBoxService.getCashBox());
        int assigned = (int) talonService.getTalonsForToday().stream()
                .map(Talon::getPatientId).distinct().count();
        workDay.setAssignedPatients(assigned);
        workDay.setSumAtStart(cashBoxService.getCashBox());

        this.create(workDay);
    }
    
    @Scheduled(cron = "0 0 10 * * *")
    public void setWorkDayStart() {
        WorkDay workDay = this.getWorkDay(LocalDate.now());
        LocalDateTime start = talonService.getTalonsForToday().stream()
            .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)
            || talon.getActivity().equals(Activity.ON_PROCEDURE)))
            .map(Talon::getStart).findFirst().orElse(null);
        workDay.setStart(start);
        this.update(workDay);
    }

    @Scheduled(cron = "0 25 19 * * *")
    public void setWorkDayFinishValues() {
        WorkDay workDay = this.getWorkDay(LocalDate.now());

        LocalDateTime finish = talonService.getTalonsForToday().stream()
            .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)))
            .map(Talon::getExecutionTime).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        workDay.setFinish(finish);

        int sumForExecutedProcedures = Math.toIntExact(accountingService.getSumForDateProcedures(LocalDate.now()));
        workDay.setSumForExecutedProcedures(sumForExecutedProcedures);

        int assigned = (int) talonService.getTalonsForToday().stream()
            .map(Talon::getPatientId).distinct().count();
        workDay.setAssignedPatients(assigned);

        int cash = Math.toIntExact(accountingService.getSumForDateCash(LocalDate.now()));
        workDay.setCash(cash);

        int card = Math.toIntExact(accountingService.getSumForDateCard(LocalDate.now()));
        workDay.setCard(card);

        int discount = Math.toIntExact(accountingService.getSumForDateDiscount(LocalDate.now()));
        workDay.setDiscount(discount);

        int outlay = cashBoxService.getOutlayForToday();
        workDay.setOutlay(outlay);

        int cashierWithdrawal = cashBoxService.getAllForToday().stream()
            .filter(cashBox -> cashBox.getType().equals(CashType.EXTRACTION))
            .mapToInt(CashBox::getSum).sum();
        workDay.setCashierWithdrawal(cashierWithdrawal);

        int rest = cashBoxService.getCashBox();
        workDay.setSumAtFinish(rest);

        int debt = patientService.getAllForToday().stream().mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfTodayAll(debt);

        List<Doctor> doctorsActiveList = talonService.getTalonsForToday().stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .map(Talon::getDoctor).distinct().collect(Collectors.toList());

        // TODO: Remove hard coded values !!!
        if (!doctorsActiveList.contains(doctorService.getDoctor(2))) {
            doctorsActiveList.add(doctorService.getDoctor(2));
        }
        if (!doctorsActiveList.contains(doctorService.getDoctor(5))) {
            doctorsActiveList.add(doctorService.getDoctor(5));
        }
        workDay.setDoctorsActive(doctorsActiveList.size());

        List<Doctor> allDoctors = doctorService.getAllActiveDoctors();
        List<String> doctorsAbsentList = new ArrayList<String>();
        for (Doctor doctor:allDoctors) {
            if (!doctorsActiveList.contains(doctor)) {
            	doctorsAbsentList.add(doctor.getFullName().split(" ")[0]);
            }
        }
        workDay.setDoctorsAbsent(doctorsAbsentList.size());
        workDay.setDoctorsAbsentList(String.join(", ", doctorsAbsentList));

        int active = (int) patientService.getAllForToday().stream()
            .filter(patient -> patient.calcActivity().equals(Activity.GAMEOVER)).count();
        workDay.setActivePatients(active);

        List<Patient> patients = patientService.getAllForToday();
        int hotel = patients.stream().filter(patient -> patient.isHotel())
            .mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfHotel(hotel);

//        int present = patients.stream()
//            .filter(patient -> patient.getActivity().equals(Activity.GAMEOVER))
//            .mapToInt(Patient::getBalance)
//            .sum();
//        workDay.setDebtOfTodayActive(present);

        //------------------------ debt------------------
        System.out.println("WORKDAY COMPLETE ");
        this.update(workDay);
    }
}