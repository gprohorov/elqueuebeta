package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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

    @PostConstruct
    void init() {
//    	 This code has been commented to the HUI to prevent garbage accrue
//    	
   // this.generateSalariesForToday();
    //  this.createSalariesForKhozDvorForJan(31);
   //   this.injection();
   //    this.inject2();
    }

    public SalaryDaily createSalaryDaily(SalaryDaily salaryDaily) {
        return repository.save(salaryDaily);
    }
    public SalaryDaily getSalaryDaily(String salaryDailyId) {
        return repository.findById(salaryDailyId).orElse(null);
    }
    public SalaryDaily updateSalaryDaily(SalaryDaily salaryDaily) {
        return repository.save(salaryDaily);
    }
    public List<SalaryDaily> getSalaryListForPeriodForDoctor(LocalDate from,
                                                             LocalDate to,
                                                             int doctorId){
        return repository.findByDateBetweenAndDoctorId(from, to, doctorId);
    }
    public SalaryDaily showSummaryForPeriodForDoctor(LocalDate from,
                                                     LocalDate to,
                                                     int doctorId){
       // from = from.minusDays(1);
       // to = to.plusDays(1);

        SalaryDaily salarySummary= new SalaryDaily();
        salarySummary.setDoctorId(doctorId);
        salarySummary.setName(doctorService.getDoctor(doctorId).getFullName());
        salarySummary.setFrom(from);
        salarySummary.setDate(to);

        List<SalaryDaily> list = this.getSalaryListForPeriodForDoctor(from,to,doctorId);
        int stavka = list.stream().mapToInt(SalaryDaily::getStavka).sum();
        salarySummary.setStavka(stavka);
        int bonuses = list.stream().mapToInt(SalaryDaily::getBonuses).sum();
        salarySummary.setBonuses(bonuses);

        return salarySummary;

    }

    public SalaryDaily createSalaryDailyForDoctor(int doctorId, LocalDate date) {

        SalaryDaily salary = new SalaryDaily();

        List<Talon> talons = talonService.getTalonsForDate(date).stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());

        salary.setDoctorId(doctorId);
        salary.setName(doctorService.getDoctor(doctorId).getFullName());
        salary.setDate(date);

        int stavka = (doctorService.getDoctor(doctorId).getRate() / 30) 
    		- setting.get().getTax() / 30 - setting.get().getCanteen();
        if (( date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
            ||
              (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
           ) {
            stavka += setting.get().getCanteen();
        }
        if(!salaryDTOService.fullTimeList.contains(doctorId)){
            if ( talons.isEmpty() ) {
                stavka = 0 - setting.get().getTax()/30;
            }
        }
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            stavka = doctorService.getDoctor(doctorId).getRate() / 30
            - setting.get().getTax() / 30;
        }

        salary.setStavka(stavka);

        int bonuses = salaryDTOService.generateBonusesForDoctor(talons);
        salary.setBonuses(bonuses);

        return this.createSalaryDaily(salary);
    }

    @Scheduled(cron = "0 15 19 * * *")
    public List<SalaryDaily> generateSalariesForToday() {
        List<SalaryDaily> list = new ArrayList<>();
        doctorService.getAllActive().forEach(doctor -> 
        	list.add(this.createSalaryDailyForDoctor(doctor.getId(), LocalDate.now())));
        return list;
    }

    public void generateSalariesForDate(LocalDate date) {
        doctorService.getAllActive().forEach(doctor ->
        	this.createSalaryDailyForDoctor(doctor.getId(), date));
    }


    public List<SalaryDaily> getSalariesForDate(LocalDate date) {
        return this.repository.findAll().stream()
            .filter(salary -> salary.getDate().equals(date)).collect(Collectors.toList());
    }


    public List<SalaryDaily> getSalarySummaryForPeriod(LocalDate from, LocalDate to){
        List<SalaryDaily> list = repository.findByDateBetween(from.minusDays(1), to.plusDays(1));
        List<Integer> doctorIds = list.stream()
               // .filter(salary->salary.getDate().isAfter(from))
                .mapToInt(SalaryDaily::getDoctorId).distinct().boxed()
                .sorted()
                .collect(Collectors.toList());
        List<SalaryDaily> summary = new ArrayList<>();

        doctorIds.stream().forEach(id->{
            SalaryDaily doctorSummary = new SalaryDaily();
            Doctor doctor = doctorService.getDoctor(id);
            doctorSummary.setDoctorId(id);
            doctorSummary.setName(doctor.getFullName());
            doctorSummary.setFrom(from);
            doctorSummary.setDate(to);

            int days = (int) list.stream()
                    .filter(salaryDaily -> salaryDaily.getBonuses() != 0)
                    .count();
            if (salaryDTOService.fullTimeList.contains(id)) {
                days = (int) list.stream()
                        .filter(salary->!salary.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
                        .count();
            }
            doctorSummary.setDays(days);

            int stavka = list.stream()
                    .filter(salary->salary.getDoctorId()==id)
                    .mapToInt(SalaryDaily::getStavka)
                    .sum();
            doctorSummary.setStavka(stavka);
            int bonuses = list.stream()
                    .filter(salary->salary.getDoctorId()==id)
                    .mapToInt(SalaryDaily::getBonuses)
                    .sum();
            doctorSummary.setBonuses(bonuses);
            summary.add(doctorSummary);

        });
        return summary;
    }


    public List<SalaryDaily>  showCurrentSalariesForToday(){
        List<SalaryDaily> salaries = new ArrayList<>();
        List<Talon> talons = talonService.getTalonsForToday();
        List<Integer> doctorIds = doctorService.getAllActive().stream()
                .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        doctorIds.forEach(doc->{
            SalaryDaily salary = this.showCureentSalaryForDoctor(talons,doc);
            salaries.add(salary);
        });

        return salaries;
    }
    public SalaryDaily showCureentSalaryForDoctor(List<Talon> list, int doctorId){
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

        if(salaryDTOService.fullTimeList.contains(doctorId)){
            stavka = doctor.getRate()/30 - setting.get().getTax()/30 - setting.get().getCanteen();
            if( LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                    ) stavka+= setting.get().getCanteen();
            salary.setStavka(stavka);
            if(doctor.getProcedureIds().isEmpty()) return salary;
            bonuses = salaryDTOService.generateBonusesForDoctor(talons);
            salary.setBonuses(bonuses);
            return salary;

        }

        if(talons.isEmpty()){
            salary.setStavka( 0 - setting.get().getTax()/30 );
            return salary;
        }

        stavka = doctor.getRate()/30 - setting.get().getTax()/30 - setting.get().getCanteen();

        if( LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                ) stavka+= setting.get().getCanteen();
        salary.setStavka(stavka);

        bonuses = salaryDTOService.generateBonusesForDoctor(talons);
        salary.setBonuses(bonuses);

        return  salary;
    }
//--------------------------------  15 feb
    public List<PermanentPayroll> getPermanentPayrollFromTo(LocalDate from, LocalDate to){
        List<PermanentPayroll> list = new ArrayList<>() ;

        List<SalaryDaily> summaries = getSalarySummaryForPeriod(from, to);

        summaries.forEach(salary -> {
            PermanentPayroll payroll = new PermanentPayroll();
            payroll.setDoctorId(salary.getDoctorId());
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
            list.add(payroll);
        });
        return list;
    }

    public PermanentPayroll calculatePayroll(Integer id
            , List<SalaryDaily> salaries
             , LocalDate from
             , LocalDate to) {
        PermanentPayroll payroll = new PermanentPayroll();
        payroll.setDoctorId(id);
        payroll.setName(doctorService.getDoctor(id).getFullName());
        payroll.setFrom(from);
        payroll.setTo(to);

        return payroll;
    }

    //-----------------------auxillary-------------------------------------------------
    public List<SalaryDaily> createSalariesForKhozDvorForJan(int days) {
        List<SalaryDaily> list = new ArrayList<>();
        List<Integer> khozdvor = doctorService.getAllActive().stream()
            .filter(doctor -> doctor.getProcedureIds().isEmpty())
            .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        khozdvor.add(2);
        khozdvor.add(5);
        LocalDate jan01 = LocalDate.of(2019, Month.JANUARY,1);
        khozdvor.stream().forEach(item -> {
            for (int i =0; i<days; i++) {
                SalaryDaily salaryDaily = new SalaryDaily();
                salaryDaily.setDoctorId(item);
                salaryDaily.setName(doctorService.getDoctor(item).getFullName());
                salaryDaily.setDate(jan01.plusDays(i));
                int stavka = doctorService.getDoctor(item).getRate() / 30 - setting.get().getTax() / 30
                        - setting.get().getCanteen();
                if (jan01.plusDays(i).getDayOfWeek().equals(DayOfWeek.SUNDAY)) stavka +=setting.get().getCanteen();
                if (jan01.plusDays(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) stavka +=setting.get().getCanteen();
                salaryDaily.setStavka(stavka);

                list.add(salaryDaily);
            }
        });
        List<SalaryDaily> sorted = list.stream().sorted(Comparator.comparing(SalaryDaily::getDoctorId)).collect(Collectors.toList());

        return repository.saveAll(sorted);
    }

    public void injection(){
        repository.deleteAll();
        for (int i =0; i<=6; i++){
            this.generateSalariesForDate(LocalDate.now().minusDays(i));
        }
    }

    public void inject2(){
        LocalDate start = LocalDate.of(2019,Month.JANUARY,1).minusDays(1);
        LocalDate finish = LocalDate.of(2019,Month.JANUARY,31).plusDays(1);
        repository.deleteAll(repository.findByDateBetween(start, finish));
    }



}