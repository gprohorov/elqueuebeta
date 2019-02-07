package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @PostConstruct
    void init() {
//    	 This code has been commented to the HUI to prevent garbage accrue
//    	
   // this.generateSalariesForToday();
   //    this.createSalariesForKhozDvor(3);
   //   this.injection();
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

    @Scheduled(cron = "0 20 18 * * *")
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
        List<SalaryDaily> list = repository.findAll().stream()
                .filter(salary->salary.getDate().isAfter(from.minusDays(1)))
                .filter(salary->salary.getDate().isBefore(to.plusDays(1)))
                .collect(Collectors.toList());
        List<Integer> doctorIds = list.stream()
                .filter(salary->salary.getDate().isAfter(from))
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



//-----------------------auxillary-------------------------------------------------
    public List<SalaryDaily> createSalariesForKhozDvor(int days) {
        List<SalaryDaily> list = new ArrayList<>();
        List<Integer> khozdvor = doctorService.getAllActive().stream()
            .filter(doctor -> doctor.getProcedureIds().isEmpty())
            .mapToInt(Doctor::getId).boxed().collect(Collectors.toList());
        khozdvor.add(2);
        khozdvor.stream().forEach(item -> {
            for (int i =1; i<=days; i++) {
                SalaryDaily salaryDaily = new SalaryDaily();
                salaryDaily.setDoctorId(item);
                salaryDaily.setName(doctorService.getDoctor(item).getFullName());
                salaryDaily.setDate(LocalDate.now().minusDays(i));
                salaryDaily.setStavka(
        			doctorService.getDoctor(item).getRate() / 30 - setting.get().getTax() / 30);
                list.add(salaryDaily);
            }
        });
        return repository.saveAll(list);
    }

    public void injection(){
        repository.deleteAll();
        for (int i =0; i<=6; i++){
            this.generateSalariesForDate(LocalDate.now().minusDays(i));
        }
    }
}