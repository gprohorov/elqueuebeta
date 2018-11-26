package com.med.services.salary.impls;

import com.med.model.*;
import com.med.repository.salary.SalaryRepository;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.salary.interfaces.ISalaryService;
import com.med.services.salarydto.impls.SalaryDTOServiceImpl;
import com.med.services.settings.impls.SettingsServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    CashBoxServiceImpl cashBoxService;

    @Autowired
    SalaryDTOServiceImpl salaryDTOService;
    
    @Autowired
    SettingsServiceImpl settingsService;

    @PostConstruct
    void init() {
        repository.save(new Salary(2,LocalDateTime.now(),SalaryType.WEEK,0));
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

    // вносит в базу элемент зарплаты, например,  премию, штраф, бонусы.
    @Override
    public Salary createSalary(Salary salary) {
        return repository.save(salary);
    }

    public List<Salary> getAll(){
        return repository.findAll();
    }

    // вносит в базу списком обязат недельные платежи (за часы, минус налог, минус обед)
    @Override
    public List<Salary> createWeekSalaryForDoctor(int doctorId) {
        List<Salary> list = new ArrayList<>();
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.TAX, TAX));

        List<Talon> talons = talonService.getAllTallonsBetween(LocalDate.now().minusDays(6),LocalDate.now().plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
              .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

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
         if (doctorId==2 || doctorId==1) {hours[0] = days * 8; }
         int rate = doctorService.getDoctor(doctorId).getRate();
         String name = doctorService.getDoctor(doctorId).getFullName();
         int weekSum = 0;
         if (hours[0]<=40) weekSum=hours[0]*rate;
         else weekSum=(hours[0] - 40)*rate * 3/2 + 40*rate;
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.WEEK, weekSum));
     //   System.out.println(" - " + name + " : " + days + " : " + hours[0]);
        return repository.saveAll(list);
    }

    // вносит недельные начисления сразу всем врачам списком
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


    // возвращает по доктору все его подробности по зарплате,
    // т.е строку зарплатной ведомости
    //Depricated, see SalaryDTOService.generateRowOfDoctor
    @Override
    public SalaryDTO getSalaryByDoctor(int doctorId) {

        SalaryDTO dto = new SalaryDTO();

        Doctor doctor = doctorService.getDoctor(doctorId);

        dto.setName(doctor.getFullName());
        dto.setDoctorId(doctorId);

        int kredit = doctor.getKredit();
        dto.setKredit(kredit);

        LocalDate strt = LocalDate.of(2018,10,29);
        LocalDate fnsh = LocalDate.of(2018,11,3);
        dto.setFrom(strt);
        dto.setTo(fnsh);
        dto.setWeek(fnsh.getDayOfYear()/7);

        List<Talon> talons = talonService.getAllTallonsBetween(strt, fnsh)
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

        int days = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();
        dto.setDays(days);

        final int[] hours = {0};
        talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet()
                .forEach((entry)->{
                    LocalDateTime begin = entry.getValue().stream().min(Comparator.comparing(Talon::getStart)).get().getStart();
                    LocalDateTime end = entry.getValue().stream().max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
                    int hrs = (int) ChronoUnit.HOURS.between(begin,end);
                    hours[0] +=hrs;
                });
        dto.setHours(hours[0]);
        if (doctorId==2 || doctorId==1){dto.setHours(days*8);}


        List<Salary> list= repository.findAll().stream()
                .filter(salary -> salary.getDoctorId()==doctorId)
                .collect(Collectors.toList());
        if (list.isEmpty()) return dto;

        int week = list.stream().filter(salary -> salary.getType().equals(SalaryType.WEEK))
                .mapToInt(Salary::getSum).sum();
        int taxes = list.stream().filter(salary -> salary.getType().equals(SalaryType.TAX))
                .mapToInt(Salary::getSum).sum();
        int canteen = list.stream().filter(salary -> salary.getType().equals(SalaryType.CANTEEN))
                .mapToInt(Salary::getSum).sum();
        dto.setStavka(week - taxes - canteen);

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
        int total = week - taxes - canteen + rest + accural + award - penalty;
        dto.setTotal(total);
        dto.setActual(total - recd);
      //  System.out.println(dto);
        return dto;
    }

    // начисление врачу бонусов за процедуры прошедшей недели
    public Salary createWeekBonusesForDoctor(int doctorId) {
        System.out.printf("Called");
       Salary salary = new Salary(doctorId, LocalDateTime.now(), SalaryType.ACCURAL,0);

        List<Talon> talons = talonService.getAllTallonsBetween(LocalDate.now().minusDays(6),LocalDate.now().plusDays(1))
                .stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());
        if (talons.isEmpty()){return salary;}

        final int[] sum = {0};

        talons.stream().forEach(talon -> {
            Procedure procedure = procedureService.getProcedure(talon.getProcedureId());
            int price = procedure.getSOCIAL();
            int zones = talon.getZones();
            int percent = procedure.getPercent();
            sum[0] += (int) price * zones * percent / 100;
        });
        salary.setSum(sum[0]);
        //System.out.println(salary.getSum());
        return salary;
    }

    // начисление бонусов всем врачам
    public List<Salary> createWeekBonus(){
        List<Salary> list = new ArrayList<>();

        doctorService.getAll().stream().forEach(doctor -> {

           list.add( this.createWeekBonusesForDoctor(doctor.getId()));
        });

        return repository.saveAll(list);
    }

    // выдача зарплаты : отметка в ведомости и в кассе
    public Response paySalary(Salary salary){
    	
    	Response response = new Response(false, "");

    	Settings settings = settingsService.get();
    	String salaryItemId = settings.getSalaryItemId();
    	if (salaryItemId == null || salaryItemId == "null" || salaryItemId.isEmpty()) {
    		response.setStatus(false);
    		response.setMessage("Не налаштована стаття витрат для обліку зарплати.");
    		return response;
    	}
    	
        SalaryDTO dto = salaryDTOService.getAll().stream()
                .filter(el->el.getClosed()==null)
                .filter(el->el.getDoctorId()==salary.getDoctorId())
                .findAny().orElse(new SalaryDTO());

        int rest = dto.getActual();
        int sum = salary.getSum();
       // int rest = this.getSalaryByDoctor(salary.getDoctorId()).getActual();
        int kredit = doctorService.getDoctor(salary.getDoctorId()).getKredit();

       // System.out.println("rest = "+ rest +" kredit = " + kredit +"  suma = "+sum);

        if (sum>rest+kredit) {
            response.setStatus(false);
            response.setMessage("Брак коштів");
            return response;
        }

        salary.setDateTime(LocalDateTime.now());
        salary.setType(SalaryType.BUZUNAR);

        // kassa is down by this salary
        CashBox cashBox = new CashBox(
                LocalDateTime.now()
                , null
                , salary.getDoctorId()
                , CashType.SALLARY
                ," з/п " + doctorService.getDoctor(salary.getDoctorId()).getFullName()
                , -1*salary.getSum());
        cashBox.setItemId(salaryItemId);
        cashBoxService.saveCash(cashBox);

        this.createSalary(salary);
        dto.setRecd(dto.getRecd()+salary.getSum());
        salaryDTOService.updateSalaryDTO(dto);
        response.setStatus(true);
        return response;
    }


    // зарплатная ведомость всех врачей за ПР0ШЕДШУЮ неделю
    // со всеми ставками, бонусами и тд
    //  обычно генерится в субботу после 15.00, когда все свалят
    // DEPRICATED
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

    public List<CashBox> getPaymentsByDoctor(int doctorId, LocalDate from, LocalDate to) {
        return cashBoxService.getAll().stream()
                .filter(cashBox -> cashBox.getDoctorId()==doctorId)
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().isBefore(to.plusDays(1)))
                .collect(Collectors.toList());
    }
}
