package com.med.services.salary.impls;

import com.med.model.*;
import com.med.repository.salary.SalaryRepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.salary.interfaces.ISalaryService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 28.10.18.
 */
@Service
public class SalaryServiceImpl implements ISalaryService {
    private final int TAX = 400;
    private final int CANTEEN = 20;

    @Autowired
    SalaryRepository repository;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    DoctorServiceImpl doctorService;

    @PostConstruct
    void init() {
        repository.save(new Salary(2,LocalDateTime.now(),SalaryType.WEEK,1));
     // repository.deleteAll();
   /*      List<Salary> list = new ArrayList<>(
                Arrays.asList(new Salary(2,LocalDateTime.now(),SalaryType.WEEK,1),
                new Salary(2,LocalDateTime.now(),SalaryType.ACCURAL,2),
                new Salary(2,LocalDateTime.now(),SalaryType.PENALTY,3),
                new Salary(2,LocalDateTime.now(),SalaryType.REST,4),
                new Salary(2,LocalDateTime.now(),SalaryType.AWARD,5)
                )
        );
        repository.saveAll(list);
        */
    }

    @Override
    public Salary createSalary(Salary salary) {
        return repository.save(salary);
    }

    @Override
    public List<Salary> createWeekSalaryForDoctor(int doctorId) {
        List<Salary> list = new ArrayList<>();
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.TAX, TAX));

        List<Talon> talons = talonService.getAllTallonsBetween(LocalDate.now().minusDays(6),LocalDate.now().plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            //    .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

        if (doctorId!=2){
            talons = talons.stream().filter(talon -> talon.getDoctor().getId()==doctorId)
                    .collect(Collectors.toList());
        }

        int days = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();
        //talons.stream().map(talon -> talon.getDate()).distinct().forEach(System.out::println);

        talons.stream().map(talon -> talon.getDate()).distinct().count();
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.CANTEEN, days*CANTEEN));

        final int[] hours = {0};
         talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet()
                .forEach((entry)->{
                   LocalDateTime begin = entry.getValue().stream().min(Comparator.comparing(Talon::getStart)).get().getStart();
                   LocalDateTime end = entry.getValue().stream().max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
                   int hrs = (int) ChronoUnit.HOURS.between(begin,end);
                   hours[0] +=hrs;
                });
         int rate = doctorService.getDoctor(doctorId).getRate();
         String name = doctorService.getDoctor(doctorId).getFullName();
         int weekSum = 0;
         if (hours[0]<=40) weekSum=hours[0]*rate;
         else weekSum=(hours[0] - 40)*rate * 3/2 + 40*rate;
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.WEEK, weekSum));
        System.out.println(" - " + name + " : " + days + " : " + hours[0]);
        return repository.saveAll(list);
    }

    @Override
    public List<Salary> createWeekSalary() {
        List<Talon> talons = talonService.getAllTallonsBetween(LocalDate.now().minusDays(7),LocalDate.now().plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());
        List<Doctor> doctors = talons.stream().map(talon -> talon.getDoctor()).distinct().collect(Collectors.toList());
        doctors.stream().forEach(doctor -> {

            this.createWeekSalaryForDoctor(doctor.getId());
        });


        return null;
    }

    @Override
    public Salary getSalary(String salaryId) {
        return repository.findById(salaryId).orElse(null);
    }



    @Override
    public SalaryDTO getSalaryByDoctor(int doctorId) {

        SalaryDTO dto = new SalaryDTO();

        dto.setName( doctorService.getDoctor(doctorId).getFullName());

        List<Talon> talons = talonService.getAllTallonsBetween(LocalDate.now().minusDays(6),LocalDate.now().plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

        int days = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();
        String name = doctorService.getDoctor(doctorId).getFullName();
 /*       System.out.println(" - " + name + " -- " + days + "  ");

        talons.stream().map(talon -> talon.getDate()).distinct()
                .sorted()
                .forEach(System.out::println);
*/

        dto.setDays(days);

        List<Salary> list= repository.findAll().stream()
                .filter(salary -> salary.getDoctorId()==doctorId)
                .collect(Collectors.toList());
        if (list.isEmpty()) return dto;

        int weeks = list.stream().filter(salary -> salary.getType().equals(SalaryType.WEEK))
                .mapToInt(Salary::getSum).sum();
        int taxes = list.stream().filter(salary -> salary.getType().equals(SalaryType.TAX))
                .mapToInt(Salary::getSum).sum();
        int canteen = list.stream().filter(salary -> salary.getType().equals(SalaryType.CANTEEN))
                .mapToInt(Salary::getSum).sum();
        dto.setStavka(weeks - taxes - canteen);

        int accural = list.stream().filter(salary -> salary.getType().equals(SalaryType.ACCURAL))
                .mapToInt(Salary::getSum).sum();
        dto.setAccural(accural);

        int award = list.stream().filter(salary -> salary.getType().equals(SalaryType.AWARD))
                .mapToInt(Salary::getSum).sum();
        dto.setAward(award);

        int rest = list.stream().filter(salary -> salary.getType().equals(SalaryType.REST))
                .mapToInt(Salary::getSum).sum();
        dto.setRest(rest);

        int recd = list.stream().filter(salary -> salary.getType().equals(SalaryType.BUZUNAR))
                .mapToInt(Salary::getSum).sum();
        dto.setRecd(recd);

        int penalty = list.stream().filter(salary -> salary.getType().equals(SalaryType.PENALTY))
                .mapToInt(Salary::getSum).sum();
        dto.setPenalty(penalty);

        dto.setTotal(weeks - taxes - canteen + rest + accural + award - penalty - recd);
        
        return dto;
    }

    @Override
    public List<SalaryDTO> getSalaryList() {

        List<Integer> doctorIds =doctorService.getAll()
                .stream().mapToInt(Doctor::getId).boxed()
                .collect(Collectors.toList());

        List<SalaryDTO> salaryDTOList = new ArrayList<>();

        doctorIds.stream().forEach(id->{
            salaryDTOList.add(this.getSalaryByDoctor(id));
        });
        return salaryDTOList;
    }

   //------------------private------------------------

}
