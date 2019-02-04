package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Doctor;
import com.med.model.SalaryDaily;
import com.med.model.Talon;
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
//    	This code has been commented to the HUI to prevent garbage accrue
//    	
//       this.generateSalariesForToday();
//       this.createSalariesForKhozDvor(3);
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

    public SalaryDaily createSalaryDailyForDoctor(int doctorId) {

        SalaryDaily salary = new SalaryDaily();

        salary.setDoctorId(doctorId);
        salary.setName(doctorService.getDoctor(doctorId).getFullName());
        salary.setDate(LocalDate.now());

        int stavka = (doctorService.getDoctor(doctorId).getRate() / 30) 
    		- setting.get().getTax() / 30 - setting.get().getCanteen();
        if (( LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY))
            ||
              (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY))
           ) {
            stavka += setting.get().getCanteen();
        }
        salary.setStavka(stavka);

        List<Talon> talons = talonService.getTalonsForToday().stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .filter(talon -> talon.getDoctor().getId() == doctorId)
            .collect(Collectors.toList());
        int bonuses = salaryDTOService.generateBonusesForDoctor(talons);
        salary.setBonuses(bonuses);

        return this.createSalaryDaily(salary);
    }
    
    public List<SalaryDaily> generateSalariesForToday() {
        List<SalaryDaily> list = new ArrayList<>();
        doctorService.getAllActive().forEach(doctor -> 
        	list.add(this.createSalaryDailyForDoctor(doctor.getId())));
        return list;
    }

    // SalaryDaily getSalaryBy
    public List<SalaryDaily> getSalariesForDate(LocalDate date) {
        return this.repository.findAll().stream()
            .filter(salary -> salary.getDate().equals(date)).collect(Collectors.toList());
    }

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
}