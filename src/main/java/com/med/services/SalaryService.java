package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Doctor;
import com.med.model.Procedure;
import com.med.model.Response;
import com.med.model.Salary;
import com.med.model.SalaryDTO;
import com.med.model.SalaryType;
import com.med.model.Settings;
import com.med.model.Talon;
import com.med.repository.SalaryRepository;

@Service
public class SalaryService {
    
	// TODO: Rewrite it to get from Settings
	private final int TAX = 400;
    private final int CANTEEN = 20;

    @Autowired
    SalaryRepository repository;

    @Autowired
    TalonService talonService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    SalaryDTOService salaryDTOService;
    
    @Autowired
    SettingsService settingsService;

    // вносит в базу элемент зарплаты, например,  премию, штраф, бонусы.
    public Salary createSalary(Salary salary) {
        return repository.save(salary);
    }

    public List<Salary> getAll() {
        return repository.findAll();
    }

    public  List<Salary> getAllForDoctor(int doctorId){
        return repository.findByDoctorId(doctorId);
    }

    // вносит в базу списком обязат недельные платежи (за часы, минус налог, минус обед)
    public List<Salary> createWeekSalaryForDoctor(int doctorId) {
        List<Salary> list = new ArrayList<>();
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.TAX, TAX));


        List<Talon> talons = talonService.getAllTallonsBetween(
    		LocalDate.now().minusDays(6), LocalDate.now().plusDays(1)).stream()
    		.filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
			.filter(talon -> talon.getDoctor().getId() == doctorId)
            .collect(Collectors.toList());

        int days = (int) talons.stream().map(talon -> talon.getDate()).distinct().count();

        talons.stream().map(talon -> talon.getDate()).distinct().count();
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.CANTEEN, days * CANTEEN));

        final int[] hours = {0};
        talons.stream().collect(Collectors.groupingBy(Talon::getDate)).entrySet().forEach(entry -> {
               LocalDateTime begin = entry.getValue().stream()
        		   .min(Comparator.comparing(Talon::getStart)).get().getStart();
               LocalDateTime end = entry.getValue().stream()
        		   .max(Comparator.comparing(Talon::getExecutionTime)).get().getExecutionTime();
               int hrs = (int) ChronoUnit.HOURS.between(begin, end);
               hours[0] += hrs;
            });
        // TODO: Remove hardcode
        if (doctorId == 2 || doctorId == 1) hours[0] = days * 8;
        int rate = doctorService.getDoctor(doctorId).getRate();
        String name = doctorService.getDoctor(doctorId).getFullName();
        // TODO: Remove hardcode
        int weekSum = (hours[0] <= 40) ? hours[0] * rate : (hours[0] - 40) * rate * 3 / 2 + 40 * rate;
        list.add(new Salary(doctorId, LocalDateTime.now(), SalaryType.WEEK, weekSum));
        return repository.saveAll(list);
    }

    // вносит недельные начисления сразу всем врачам списком
    public void createWeekSalary() {
        List<Talon> talons = talonService.getAllTallonsBetween(
    		LocalDate.now().minusDays(7), LocalDate.now().plusDays(1)).stream()
    		.filter(talon -> talon.getActivity().equals(Activity.EXECUTED)).collect(Collectors.toList());
        List<Doctor> doctors = talons.stream()
    		.map(talon -> talon.getDoctor()).distinct().collect(Collectors.toList());
        doctors.stream().forEach(doctor -> { this.createWeekSalaryForDoctor(doctor.getId()); });
    }

    public Salary getSalary(String salaryId) {
        return repository.findById(salaryId).orElse(null);
    }

    // начисление врачу бонусов за процедуры прошедшей недели
    public Salary createWeekBonusesForDoctor(int doctorId) {
        Salary salary = new Salary(doctorId, LocalDateTime.now(), SalaryType.ACCURAL, 0);
        List<Talon> talons = talonService.getAllTallonsBetween(
    		LocalDate.now().minusDays(6), LocalDate.now().plusDays(1)).stream()
        		.filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId() == doctorId)
                .collect(Collectors.toList());
        if (talons.isEmpty()) return salary;

        final int[] sum = {0};

        talons.stream().forEach(talon -> {
            Procedure procedure = procedureService.getProcedure(talon.getProcedureId());
            int price = procedure.getSOCIAL();
            int zones = talon.getZones();
            int percent = procedure.getPercent();
            sum[0] += (int) price * zones * percent / 100;
        });
        salary.setSum(sum[0]);
        return salary;
    }

    // начисление бонусов всем врачам
    public List<Salary> createWeekBonus(){
        List<Salary> list = new ArrayList<>();
        doctorService.getAllActive().stream().forEach(doctor -> {
           list.add( this.createWeekBonusesForDoctor(doctor.getId()));
        });
        return repository.saveAll(list);
    }

    // выдача зарплаты : отметка в ведомости и в кассе
    public Response paySalary(Salary salary) {
    	
    	if (salary.getSum() > cashBoxService.getCashBox()) {
    		return new Response(false, "В касі не вистачає коштів.");
    	}
    	
    	Settings settings = settingsService.get();
    	String salaryItemId = settings.getSalaryItemId();
    	if (salaryItemId == null || salaryItemId == "null" || salaryItemId.isEmpty()) {
    		return new Response(false, "Не налаштована стаття витрат для обліку зарплати.");
    	}
    	
        SalaryDTO dto = salaryDTOService.getAll().stream()
                .filter(el->el.getClosed()==null)
                .filter(el->el.getDoctorId()==salary.getDoctorId())
                .findAny().orElse(new SalaryDTO());

        int rest = dto.getActual();
        int sum = salary.getSum();
        int kredit = doctorService.getDoctor(salary.getDoctorId()).getKredit();

        if (sum > rest + kredit) {
            return new Response(false, "Перевищено ліміт нарахованих грошей.");
        }

        salary.setDateTime(LocalDateTime.now());
        salary.setType(SalaryType.BUZUNAR);

        // kassa is down by this salary
        CashBox cashBox = new CashBox(
                LocalDateTime.now()
                , null
                , salary.getDoctorId()
                , CashType.SALLARY
                , "з/п " + doctorService.getDoctor(salary.getDoctorId()).getFullName()
                , -1*salary.getSum());
        cashBox.setItemId(salaryItemId);
        cashBoxService.saveCash(cashBox);

        this.createSalary(salary);
        dto.setRecd(dto.getRecd()+salary.getSum());
        salaryDTOService.updateSalaryDTO(dto);
        
        return new Response(true, "");
    }
    
    // выдача зарплаты суперадмином с автоматической проводкой через кассу
    public Response paySalarySA(Salary salary) {

    	Settings settings = settingsService.get();
    	String salaryItemId = settings.getSalaryItemId();
    	if (salaryItemId == null || salaryItemId == "null" || salaryItemId.isEmpty()) {
    		return new Response(false, "Не налаштована стаття витрат для обліку зарплати.");
    	}
    	String extractionItemId = settings.getExtractionItemId();
    	if (extractionItemId == null || extractionItemId == "null" || extractionItemId.isEmpty()) {
    		return new Response(false, "Не налаштована стаття для обліку зняття каси.");
    	}
    	
    	SalaryDTO dto = salaryDTOService.getAll().stream()
			.filter(el -> el.getClosed() == null)
			.filter(el -> el.getDoctorId() == salary.getDoctorId())
			.findAny().orElse(new SalaryDTO());
    	
    	int rest = dto.getActual();
    	int sum = salary.getSum();
    	int kredit = doctorService.getDoctor(salary.getDoctorId()).getKredit();
    	
    	if (sum > rest + kredit) {
    		return new Response(false, "Перевищено ліміт нарахованих грошей.");
    	}
    	
    	salary.setDateTime(LocalDateTime.now());
    	salary.setType(SalaryType.BUZUNAR);
    	
    	// kassa is up by this salary
    	CashBox cashBoxUP = new CashBox(
    			LocalDateTime.now()
    			, null
    			, 1
    			, CashType.EXTRACTION
    			, "Поповнення каси для видачі з/п " 
					+ doctorService.getDoctor(salary.getDoctorId()).getFullName()
    			, salary.getSum());
    	cashBoxUP.setItemId(extractionItemId);
    	cashBoxService.createCash(cashBoxUP);
    	
    	// kassa is down by this salary
    	CashBox cashBox = new CashBox(
    			LocalDateTime.now()
    			, null
    			, salary.getDoctorId()
    			, CashType.SALLARY
    			, "з/п " + doctorService.getDoctor(salary.getDoctorId()).getFullName()
    			, -1*salary.getSum());
    	cashBox.setItemId(salaryItemId);
    	cashBoxService.createCash(cashBox);
    	
    	this.createSalary(salary);
    	dto.setRecd(dto.getRecd() + salary.getSum());
    	salaryDTOService.updateSalaryDTO(dto);
    	
    	return new Response(true, "");
    }
    
    // TODO: Make by MongoRepository
    public List<CashBox> getPaymentsByDoctor(int doctorId, LocalDate from, LocalDate to) {
    	return cashBoxService.getAll().stream()
    			.filter(cashBox -> cashBox.getDoctorId() == doctorId)
    			.filter(cashBox -> cashBox.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
    			.filter(cashBox -> cashBox.getDateTime().toLocalDate().isBefore(to.plusDays(1)))
    			.collect(Collectors.toList());
    }

    // TODO: Remove it, if really don't needed !!!
    
    // зарплатная ведомость всех врачей за ПР0ШЕДШУЮ неделю
    // со всеми ставками, бонусами и тд
    //  обычно генерится в субботу после 15.00, когда все свалят
    // DEPRICATED
    public List<SalaryDTO> getSalaryList() {
    	List<Integer> doctorIds = doctorService.getAllActive()
                .stream().mapToInt(Doctor::getId).boxed()
                .collect(Collectors.toList());

        List<SalaryDTO> salaryDTOList = new ArrayList<>();

        doctorIds.stream().forEach(id->{
            salaryDTOList.add(this.getSalaryByDoctor(id));
        });
        return salaryDTOList;
    }

    // возвращает по доктору все его подробности по зарплате,
    // т.е строку зарплатной ведомости
    // Depricated, see SalaryDTOService.generateRowOfDoctor
    public SalaryDTO getSalaryByDoctor(int doctorId) {

        SalaryDTO dto = new SalaryDTO();
        Doctor doctor = doctorService.getDoctor(doctorId);

        dto.setDoctorId(doctorId);
        dto.setName(doctor.getFullName());
        dto.setKredit(doctor.getKredit());

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
        return dto;
    }
}