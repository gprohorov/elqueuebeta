package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.med.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.repository.SalaryDailyRepository;

@Service
public class SalaryDailyService {

    @Autowired
    SalaryDailyRepository repository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    SettingsService setting;

    @Autowired
    TalonService talonService;

    @Autowired
    SalaryDTOService salaryDTOService;

    @Autowired
    SalaryService salaryService;

    private List<Integer> fullTimeList;


    @PostConstruct
    void init() {
        fullTimeList = doctorService.getAllActive().stream()
                .filter(doc -> doc.getProcedureIds().isEmpty())
                .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
    //    fullTimeList.add(2); // для Иры.
    //   fullTimeList.add(5); // для ЮВ
	// This code has been commented to the HUI to prevent garbage accrue
    // this.generateSalariesForToday();
    // this.createSalariesForKhozDvorForJan(31);
    // this.injection();
    // this.inject2();
     //   this.inject4();
    }

    public SalaryDaily createSalaryDaily(SalaryDaily salaryDaily) {
        return repository.save(salaryDaily);
    }

    public List<SalaryDaily> getAllByDate(LocalDate date){
        return this.repository.findByDate(date);
    }

    public List<SalaryDaily> getAllForToday(){
        return this.repository.findByDate(LocalDate.now().minusDays(30));
    }

    public SalaryDaily getSalaryDaily(String salaryDailyId) {
        return repository.findById(salaryDailyId).orElse(null);
    }

    public SalaryDaily updateSalaryDaily(SalaryDaily salaryDaily) {
        return repository.save(salaryDaily);
    }

    public List<SalaryDaily> getSalaryListForPeriodForDoctor(LocalDate from, LocalDate to, int doctorId) {
        return repository.findByDateBetweenAndDoctorId(from, to, doctorId);
    }

    public SalaryDaily showSummaryForPeriodForDoctor(LocalDate from, LocalDate to, int doctorId) {
       // from = from.minusDays(1);
       // to = to.plusDays(1);

        SalaryDaily salarySummary= new SalaryDaily();
        salarySummary.setDoctorId(doctorId);
        salarySummary.setName(doctorService.getDoctor(doctorId).getFullName());
        salarySummary.setFrom(from);
        salarySummary.setDate(to);

        List<SalaryDaily> list = this.getSalaryListForPeriodForDoctor(from, to, doctorId);
        int stavka = list.stream().mapToInt(SalaryDaily::getStavka).sum();
        salarySummary.setStavka(stavka);
        int bonuses = list.stream().mapToInt(SalaryDaily::getBonuses).sum();
        salarySummary.setBonuses(bonuses);

        return salarySummary;
    }

    public SalaryDaily createSalaryDailyForDoctor(int doctorId, LocalDate date) {

        SalaryDaily salary = new SalaryDaily();
        Doctor doctor = doctorService.getDoctor(doctorId);

        List<Talon> talons = talonService.getTalonsForDate(date).stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());

        if( talons.isEmpty() && !this.isWeekEnd(date) ) {
           doctor.setDaysOff(doctor.getDaysOff() +1);
           doctorService.updateDoctor(doctor);
        }

        salary.setDoctorId(doctorId);
        salary.setName(doctorService.getDoctor(doctorId).getFullName());
        salary.setDate(date);

/*   April 10 22
       int stavka = doctorService.getDoctor(doctorId).getRate() / 175;
        int stavka = (doctorService.getDoctor(doctorId).getRate() / 30)
    		- setting.get().getTax() / 30 - setting.get().getCanteen();

        // HARDCORE:  25 are the vacations days +  all the state holidays
        if(doctor.getDaysOff() > 25){
            stavka = 0 - setting.get().getTax() / 30 - setting.get().getCanteen();
        }

        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            stavka = doctorService.getDoctor(doctorId).getRate() / 30
            		- setting.get().getTax() / 30;
        }
*/
        double workingHours = this.getWorkingHoursForDoctorForDay(doctorId, date);
        System.out.println(doctor.getFullName() + " - "+ workingHours);
        int stavka = (int) (doctorService.getDoctor(doctorId).getRate() * workingHours / 175);
        // HARDCODE:   175  -  working hours in a month

        salary.setStavka(stavka);
        salary.setHours((int) workingHours);


        int bonuses = salaryDTOService.generateBonusesForDoctor(talons);

        salary.setBonuses(bonuses);

        return this.createSalaryDaily(salary);
    }

   // @Scheduled(cron = "0 15 19 * * *")
    public void generateSalariesForToday() {

        List<SalaryDaily> list = this.getSalariesForDate(LocalDate.now());
        this.repository.deleteAll(list);
        list.clear();

        doctorService.getAllActive().forEach(doctor ->
        	list.add(this.createSalaryDailyForDoctor(doctor.getId(), LocalDate.now())));
       // return list;
    }


    public void generateSalariesForDate(LocalDate date) {

        List<SalaryDaily> list = this.getSalariesForDate(date);
        this.repository.deleteAll(list);
        list.clear();

        doctorService.getAllActive().forEach(doctor ->
        	this.createSalaryDailyForDoctor(doctor.getId(), date));
    }

    public List<SalaryDaily> getSalariesForDate(LocalDate date) {
        return this.repository.findAll().stream()
            .filter(salary -> salary.getDate().equals(date))
            .sorted(Comparator.comparing(SalaryDaily::getDoctorId))
              .collect(Collectors.toList());
    }

    public List<SalaryDaily> getSalarySummaryForPeriod(LocalDate from, LocalDate to){
        List<SalaryDaily> list = repository.findByDateBetween(from.minusDays(1), to.plusDays(1));
        List<Integer> doctorIds = list.stream()
               // .filter(salary->salary.getDate().isAfter(from))
                .mapToInt(SalaryDaily::getDoctorId).distinct().boxed()
                .sorted().collect(Collectors.toList());
        List<SalaryDaily> summary = new ArrayList<>();

        doctorIds.stream().forEach(id -> {
            SalaryDaily doctorSummary = new SalaryDaily();
            Doctor doctor = doctorService.getDoctor(id);
            doctorSummary.setDoctorId(id);
            doctorSummary.setName(doctor.getFullName());
            doctorSummary.setFrom(from);
            doctorSummary.setDate(to);

            int days = (int) list.stream()
                .filter(salaryDaily -> salaryDaily.getBonuses() != 0)
                .filter(salaryDaily -> salaryDaily.getDoctorId()==id).count();
            if (this.fullTimeList.contains(id)) {
                days = (int) list.stream()
                .filter(salary->!salary.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
                 .filter(salary->salary.getDoctorId()==id)
                        .count();
            }
            doctorSummary.setDays(days);

            int stavka = list.stream().filter(salary->salary.getDoctorId() == id)
                .mapToInt(SalaryDaily::getStavka).sum();
            doctorSummary.setStavka(stavka);
            int bonuses = list.stream().filter(salary->salary.getDoctorId() == id)
                .mapToInt(SalaryDaily::getBonuses).sum();
            doctorSummary.setBonuses(bonuses);
            summary.add(doctorSummary);
        });
        return summary;
    }

    public List<SalaryDaily> showCurrentSalariesForToday() {
        List<SalaryDaily> salaries = new ArrayList<>();
        List<Talon> talons = talonService.getTalonsForToday();
        List<Integer> doctorIds = doctorService.getAllActive().stream()
            .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        doctorIds.forEach(doc -> {
            SalaryDaily salary = this.showCureentSalaryForDoctor(talons, doc);
            salaries.add(salary);
        });
        return salaries;
    }
    //  current state for today
    public SalaryDaily showCureentSalaryForDoctor(List<Talon> list, int doctorId) {
        Doctor doctor = doctorService.getDoctor(doctorId);
        List<Talon> talons = list.stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .filter(talon -> talon.getDoctor().getId()==doctorId)
            .collect(Collectors.toList());
        SalaryDaily salary = new SalaryDaily();
        salary.setDate(LocalDate.now());
        salary.setDoctorId(doctorId);
        salary.setName(doctor.getFullName());
        salary.setFrom(LocalDate.now());

        int stavka = 0;
        int bonuses = 0;

        if (this.fullTimeList.contains(doctorId)) {
            stavka = doctor.getRate() / 30 - setting.get().getTax() / 30 - setting.get().getCanteen();
            if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)
             || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)
            ) stavka+= setting.get().getCanteen();
            salary.setStavka(stavka);
            if(doctor.getProcedureIds().isEmpty()) return salary;
            bonuses = salaryDTOService.generateBonusesForDoctor(talons);
            salary.setBonuses(bonuses);
            return salary;
        }

        if (talons.isEmpty()) {
            salary.setStavka(0 - setting.get().getTax() / 30 );
            return salary;
        }

        stavka = doctor.getRate()/30 - setting.get().getTax() / 30 - setting.get().getCanteen();

        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)
         || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)
        ) stavka += setting.get().getCanteen();
        salary.setStavka(stavka);

        bonuses = salaryDTOService.generateBonusesForDoctor(talons);
        salary.setBonuses(bonuses);

        return  salary;
    }

    // --------------------------------  15 feb
    public List<PermanentPayroll> getPermanentPayrollFromTo(LocalDate from, LocalDate to) {
        List<PermanentPayroll> list = new ArrayList<>();

        List<SalaryDaily> summaries = getSalarySummaryForPeriod(from, to);

        summaries.forEach(salary -> {
            PermanentPayroll payroll = new PermanentPayroll();
            payroll.setName(salary.getName());
            payroll.setDoctorId(salary.getDoctorId());
            payroll.setActive(doctorService.getDoctor(salary.getDoctorId()).isActive());
            payroll.setFrom(salary.getFrom());
            payroll.setTo(salary.getDate());
            payroll.setDays(salary.getDays());
            payroll.setStavka(salary.getStavka());
            payroll.setAccural(salary.getBonuses());

            List<Salary> slrs = salaryService.getAllForDoctor(salary.getDoctorId())
                    .stream()
                    .filter(slr->slr.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
                    .filter(slr->slr.getDateTime().toLocalDate().isBefore(to.plusDays(1)))
                    .collect(Collectors.toList());

            int award = slrs.stream()
                    .filter(slr->slr.getType().equals(SalaryType.AWARD))
                    .mapToInt(Salary::getSum).sum();

          payroll.setAward(award);

            int penalty = slrs.stream()
                    .filter(slr->slr.getType().equals(SalaryType.PENALTY))
                    .mapToInt(Salary::getSum).sum();

            payroll.setPenalty(penalty);

            int buzunar = slrs.stream()
                    .filter(slr->slr.getType().equals(SalaryType.BUZUNAR))
                    .mapToInt(Salary::getSum).sum();
            payroll.setRecd(buzunar);
            payroll.setActualNow(this.getRestForTodayForDoctor(salary.getDoctorId()));
            list.add(payroll);
        });
        return list;
    }


//----------------------------- 10 march   ----------------------------------------
    public int getRestForTodayForDoctor(int id){
        LocalDate from = LocalDate.of(2019, Month.JANUARY,1);
        LocalDate to = LocalDate.now();

        int dailySummary = this.showSummaryForPeriodForDoctor(from.minusDays(1),to.plusDays(1),id)
                .getTotal();

        List<Salary> slrs = salaryService.getAllForDoctor(id).stream()
                .filter(salary -> salary.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
                .collect(Collectors.toList());

        int buzunar = slrs.stream()
                .filter(slr->slr.getType().equals(SalaryType.BUZUNAR))
                .mapToInt(Salary::getSum).sum();

        int award = slrs.stream()
                .filter(slr->slr.getType().equals(SalaryType.AWARD))
                .mapToInt(Salary::getSum).sum();


        int penalty = slrs.stream()
                .filter(slr->slr.getType().equals(SalaryType.PENALTY))
                .mapToInt(Salary::getSum).sum();

        int result = dailySummary + award - penalty - buzunar;

        return result;
    }

    public PermanentPayroll calculatePayroll(Integer id, List<SalaryDaily> salaries,
			LocalDate from, LocalDate to) {
        PermanentPayroll payroll = new PermanentPayroll();
        payroll.setDoctorId(id);
        payroll.setName(doctorService.getDoctor(id).getFullName());
        payroll.setFrom(from);
        payroll.setTo(to);

        return payroll;
    }
//----------------------------   26 feb
    public void reGenerateSalaryForDoctorFromTo(int doctorId, LocalDate from, LocalDate to) {
        List<SalaryDaily> list = this.getSalaryListForPeriodForDoctor(from.minusDays(1),to.plusDays(1),doctorId);
        this.repository.deleteAll(list);
        int days = (int) ChronoUnit.DAYS.between(from,to);
        for (int i = 0; i <=days ; i++) {
            createSalaryDailyForDoctor(doctorId,from.plusDays(i));
        }



    }
    //  vacation++
    //-------------------- 24 march ---------------
    public List<Doctor> setDoctorsTruant(LocalDate date){
        List<Doctor> doctors = doctorService.getAllActiveDoctors().stream()
                .filter(doctor -> (!fullTimeList.contains(doctor)))
                .collect(Collectors.toList());
        List<SalaryDaily> salaries = this.getSalariesForDate(date);
        for (Doctor doctor:doctors){
            boolean isEmpty = salaries.stream()
                    .filter(salary -> salary.getDoctorId() == doctor.getId())
                    .collect(Collectors.toList()).isEmpty();
            if( isEmpty
                && (!date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                && (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY) )
              ) {
                doctor.setDaysOff(doctor.getDaysOff()+1);
                doctorService.updateDoctor(doctor);
            }
        }
        return null;
    }
    //-----------------------auxillary injections-------------------------------------------------
    // 24th of Feb
    // generate Salaries for a period for ALL
    public List<SalaryDaily> createDailySalariesForAllFromTo(LocalDate from, LocalDate to) {
        List<SalaryDaily> salaryDailies =
                this.repository.findByDateBetween(from.minusDays(1), to.plusDays(1));
        this.repository.deleteAll(salaryDailies);
        List<Integer> doctorIds = doctorService.getAllActive().stream()
                .mapToInt(Doctor::getId).boxed()
                .collect(Collectors.toList());
        int days = (int) ChronoUnit.DAYS.between(from, to) + 1;

        LocalDate date = null;
        for (int i = 0; i < days; i++) {
            date = from.plusDays(i);

            for (Integer id : doctorIds) {
                this.createSalaryDailyForDoctor(id, date);
            }
        }
        return null;
    }

    public List<SalaryDaily> createSalariesForKhozDvorForJan(int days) {
        List<SalaryDaily> list = new ArrayList<>();
        List<Integer> khozdvor = doctorService.getAllActive().stream()
            .filter(doctor -> doctor.getProcedureIds().isEmpty())
            .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        khozdvor.add(2);
        khozdvor.add(5);
        LocalDate jan01 = LocalDate.of(2019, Month.JANUARY, 1);
        khozdvor.stream().forEach(item -> {
            for (int i = 0; i < days; i++) {
                SalaryDaily salaryDaily = new SalaryDaily();
                salaryDaily.setDoctorId(item);
                salaryDaily.setName(doctorService.getDoctor(item).getFullName());
                salaryDaily.setDate(jan01.plusDays(i));
                int stavka = doctorService.getDoctor(item).getRate() / 30
            		- setting.get().getTax() / 30 - setting.get().getCanteen();
                if (jan01.plusDays(i).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                	stavka += setting.get().getCanteen();
                }
                if (jan01.plusDays(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                	stavka += setting.get().getCanteen();
                }
                salaryDaily.setStavka(stavka);

                list.add(salaryDaily);
            }
        });
        List<SalaryDaily> sorted = list.stream()
			.sorted(Comparator.comparing(SalaryDaily::getDoctorId)).collect(Collectors.toList());

        return repository.saveAll(sorted);
    }

    public void injection() {
        repository.deleteAll();
        for (int i = 0; i <= 6; i++) {
            this.generateSalariesForDate(LocalDate.now().minusDays(i));
        }
    }

    public void inject2() {
        LocalDate start = LocalDate.of(2019, Month.JANUARY, 1).minusDays(1);
        LocalDate finish = LocalDate.of(2019, Month.JANUARY, 31).plusDays(1);
        repository.deleteAll(repository.findByDateBetween(start, finish));
    }

    // @Scheduled(cron = "0 20 17 ? * SUN")

    public void inject4(){
        System.out.println("INJECT-4");
        LocalDate start = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate finish = LocalDate.of(2019, Month.JANUARY, 31);
        this.createDailySalariesForAllFromTo(start, finish);
        System.out.println("INJECT-4  END");
    }
    //@Scheduled(cron = "0 30 18 ? * SAT")
    public void inject5(){
        System.out.println("INJECT-5");
        List<SalaryDaily> list = new ArrayList<>();
        this.repository.findAll().stream()
                .forEach(salaryDaily -> {
                  salaryDaily.setBonuses( (salaryDaily.getBonuses() * 9/10));
                  list.add(salaryDaily);
                });
        repository.saveAll(list);
        System.out.println("INJECT-5  END");
    }

    private boolean isWeekEnd(LocalDate date){
        return date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public long getWorkingHoursForDoctorForDay(int doctorId, LocalDate date){

        List<Talon> list = talonService.getTalonsForDate(date)
                .stream()
                .filter(talon -> talon.getDoctor() != null)
                .filter(talon -> talon.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return 0;
        }

        LocalDateTime begin = list.stream()
        .map(talon -> talon.getStart())
        .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        LocalDateTime end = list.stream()
        .map(talon -> talon.getExecutionTime())
        .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        return  ChronoUnit.HOURS.between(begin, end) + 1 ;
    }



}
