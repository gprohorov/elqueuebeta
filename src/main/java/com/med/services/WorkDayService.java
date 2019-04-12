package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.med.model.balance.PaymentType;
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

    @Autowired
    SettingsService settingsService;

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

    // @Scheduled(cron = "0 0 7 * * *")
    public void initWorkDay() {
        WorkDay workDay = new WorkDay(LocalDate.now());
        int assigned = (int) talonService.getTalonsForToday().stream()
                .map(Talon::getPatientId).distinct().count();
        workDay.setAssignedPatients(assigned);
        workDay.setSumAtStart(cashBoxService.getCashBox());

        this.create(workDay);
    }

    // @Scheduled(cron = "0 0 10 * * *")
    public void setWorkDayStart() {
        WorkDay workDay = this.getWorkDay(LocalDate.now());
        LocalDateTime start = talonService.getTalonsForToday().stream()
            .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)
            || talon.getActivity().equals(Activity.ON_PROCEDURE)))
            .map(Talon::getStart).sorted().findFirst().orElse(null);
        workDay.setStart(start);
        this.update(workDay);
    }

    // @Scheduled(cron = "0 25 19 * * *")
    public void setWorkDayFinishValues() {
        WorkDay workDay = this.getWorkDay(LocalDate.now());

        // Ищем когда закончилась последняя процедура
        LocalDateTime finish = talonService.getTalonsForToday().stream()
                .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)))
                .map(Talon::getExecutionTime).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        workDay.setFinish(finish);

        // сумма, начислення за выполненные процедуры
        int sumForExecutedProcedures = Math.toIntExact(accountingService.getSumForDateProcedures(LocalDate.now()));
        workDay.setSumForExecutedProcedures(sumForExecutedProcedures);

        // кол-во пациентов, записанных на сегодня
        int assigned = (int) talonService.getTalonsForToday().stream()
                .map(Talon::getPatientId).distinct().count();
        workDay.setAssignedPatients(assigned);

        //  сумма, внесённая сегодня в кассу пациентами НАЛОМ
        int cash = Math.toIntExact(accountingService.getSumForDateCash(LocalDate.now()));
        workDay.setCash(cash);

        //  сумма, внесённая сегодня в кассу пациентами КАРТОЧКОЙ
        int card = Math.toIntExact(accountingService.getSumForDateCard(LocalDate.now()));
        workDay.setCard(card);

        // Сумма всех скидок
        int discount = Math.toIntExact(accountingService.getSumForDateDiscount(LocalDate.now()));
        workDay.setDiscount(discount);

        // Список скидочников с суммами скидок
        final String[] discountsStringList = {""};
        HashMap<String, Integer> discounts = new HashMap<>();
        accountingService.getAllForDate(LocalDate.now()).stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.DISCOUNT))
                .forEach(accounting -> {
                    Patient patient = patientService.getPatient(accounting.getPatientId());
                    String key = patient.getPerson().getFullName().split(" ")[0];
                    Integer value = accounting.getSum();
                    discounts.put(key, value);
                    discountsStringList[0] += key + " " + value + ", ";
                });
        workDay.setDiscountList(discountsStringList[0]);

        // витраты за сегодня
        int outlay = cashBoxService.getOutlayForToday();
        workDay.setOutlay(outlay);

        // инкассация
        int cashierWithdrawal = cashBoxService.getAllForToday().stream()
                .filter(cashBox -> cashBox.getItemId() != null)
                .filter(cashBox -> cashBox.getItemId().equals(settingsService.get().getExtractionItemId()))
                .mapToInt(CashBox::getSum).sum();
        workDay.setCashierWithdrawal(cashierWithdrawal);

        // остаток на конец дня
        int rest = cashBoxService.getCashBox();
        workDay.setSumAtFinish(rest);

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
        for (Doctor doctor : allDoctors) {
            if (!doctorsActiveList.contains(doctor)) {
                doctorsAbsentList.add(doctor.getFullName().split(" ")[0] + " "
                        + doctor.getDaysOff());
            }
        }
        workDay.setDoctorsAbsent(doctorsAbsentList.size());
        workDay.setDoctorsAbsentList(String.join(", ", doctorsAbsentList));

        List<Patient> patients = patientService.getAllForToday();

        int debt = patients.stream()
                .filter(patient -> patient.getBalance() < 0)
                .mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfTodayAll(debt);

        int active = (int) patients.stream()
                .filter(patient -> patient.calcActivity().equals(Activity.GAMEOVER)).count();
        workDay.setActivePatients(active);

        int passiveDebt = patients.stream()
                .filter(patient -> patient.getBalance() < 0)
                .filter(patient -> patient.getActivity().equals(Activity.NON_ACTIVE))
                .mapToInt(Patient::getBalance).sum();

        workDay.setDebtOfTodayPassive(passiveDebt);

        int activeDebt = patients.stream()
                .filter(patient -> patient.getBalance() < 0)
                .filter(patient -> patient.getActivity().equals(Activity.GAMEOVER))
                .mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfTodayActive(activeDebt);


        int hotel = patients.stream()
                .filter(patient -> patient.getBalance() < 0)
                .filter(patient -> patient.isHotel())
                .mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfHotel(hotel);

        int nonHotel = debt - hotel;
        workDay.setDebtOfTodayWithoutHotel(nonHotel);

        List<Patient> nonHotelPatients = patients.stream()
                .filter(patient -> patient.getBalance() < 0)
                .filter(patient -> !patient.isHotel())
                .filter(patient -> patient.getActivity().equals(Activity.GAMEOVER))
                .collect(Collectors.toList());

        String nonHotelDebt = "";
        for (Patient patient : nonHotelPatients) {
            nonHotelDebt += patient.getPerson().getFullName().split(" ")[0]
                    + " " + patient.getBalance() + ", ";
        }
        workDay.setDebtOfTodayWithoutHotelList(nonHotelDebt);


        List<Patient> truants = patients.stream()
                .filter(patient -> patient.getActivity().equals(Activity.NON_ACTIVE))
                .filter(patient -> patient.getBalance() < 0)
                .collect(Collectors.toList());

        String truantString = "";
        for (Patient patient : truants) {
            truantString += patient.getPerson().getFullName().split(" ")[0]
                    + " " + patient.getBalance() + ", ";
        }
        workDay.setTruantsString(truantString);


        List<Patient> tomorrov = patientService.getAllForDate(LocalDate.now().plusDays(1));
        List<Patient> tomorrowTruants = new ArrayList<>();

        for (Patient truant:truants) {
            if (!tomorrov.contains(truant)) {
                tomorrowTruants.add(truant);
            }
        }
        int tomorrovAbsent = tomorrowTruants.stream()
                .mapToInt(Patient::getBalance).sum();
        workDay.setDebtOfTomorrowPassive(tomorrovAbsent);

        String truantTomorrowString = "";
        for (Patient patient : truants) {
            truantTomorrowString += patient.getPerson().getFullName().split(" ")[0]
                    + " " + patient.getBalance() + ", ";
        }
        workDay.setTruantsTomorrowString(truantTomorrowString);

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