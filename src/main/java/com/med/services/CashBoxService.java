package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Response;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.model.Settings;
import com.med.repository.CashRepository;

import javax.annotation.PostConstruct;

@Service
public class CashBoxService {

    @Autowired
    CashRepository repository;

    @Autowired
    SalaryService salaryService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    SettingsService settings;

    @PostConstruct
    void init(){
    //  this.inject1();
    }

    public List<CashBox> getAll() {
        return repository.findAll();
    }

    public List<CashBox> getAllFromToExceptTypePatient(LocalDate from, LocalDate to) {
    	return repository.findAllByDateTimeIsBetweenAndTypeIsNot(from, to.plusDays(1), CashType.PATIENT);
    }

    public CashBox createCash(CashBox cash) {
    	return repository.save(cash);
    }

    public Response saveCash(CashBox cash) {
    	if (Math.abs(cash.getSum()) > this.getCashBox()) {
    		return new Response(false, "В касі не вистачає коштів.");
    	}
        repository.save(cash);
        return new Response(true, "");
    }

    public Response saveCashSA(CashBox cash) {

    	Settings settings = settingsService.get();
    	String extractionItemId = settings.getExtractionItemId();
    	if (extractionItemId == null || extractionItemId == "null" || extractionItemId.isEmpty()) {
    		return new Response(false, "Не налаштована стаття для обліку зняття каси.");
    	}

    	CashBox cashBox = new CashBox(LocalDateTime.now(), null, 1, CashType.EXTRACTION,
			"Поповнення каси" , -1 * cash.getSum());
    	cashBox.setItemId(extractionItemId);
    	repository.save(cashBox);

    	repository.save(cash);
    	return new Response(true, "");
    }

    public CashBox getCashBox(String id) {
        return repository.findById(id).orElse(null);
    }

    public int getCashBox() {
        return repository.findAll().stream().mapToInt(CashBox::getSum).sum();
    }

    public Response toZero(int sum) {

    	if (sum > this.getCashBox()) {
    		return new Response(false, "В касі не вистачає коштів.");
    	}

    	Settings settings = settingsService.get();
    	String extractionItemId = settings.getExtractionItemId();
    	if (extractionItemId == null || extractionItemId == "null" || extractionItemId.isEmpty()) {
    		return new Response(false, "Не налаштована стаття витрат для обліку здачі каси.");
    	}
        CashBox cashBox = new CashBox(LocalDateTime.now(), null, 1,
    		(sum > 0 ? "Касу знято" : "Внесено в касу"), -1*sum);
        cashBox.setType(CashType.EXTRACTION);
        cashBox.setItemId(extractionItemId);
        Salary salary = new Salary(1, LocalDateTime.now(), SalaryType.EXTRACTION, sum);
        salaryService.createSalary(salary);
        repository.save(cashBox);
        return new Response(true, "");
    }

    public List<CashBox> findByItemId(String id) {
    	return repository.findByItemId(id);
    }

    public void saveAll(List<CashBox> list) {
    	repository.saveAll(list);
    }

    public List<CashBox> getAllForToday() {
        LocalDateTime morning = LocalDate.now().atStartOfDay();
        return repository.findAllByDateTimeAfter(morning);
    }

    public int getOutlayForToday() {
        LocalDateTime morning = LocalDateTime.now().minusHours(12);
        return repository.findAllByDateTimeAfter(morning).stream()
                .filter(cashBox -> cashBox.getSum()<0)
                .filter(cashBox -> !cashBox.getType().equals(CashType.EXTRACTION))
                .mapToInt(CashBox::getSum)
                .sum();
    }


    // 17 september 2019
    public int getOutlayForPeriod(LocalDate from, LocalDate to) {

        // FROM is not included !!!!
        return repository.findAll().stream()
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().isAfter(from))
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().isBefore(to))
                .filter(cashBox -> cashBox.getSum()<0)
                .filter(cashBox -> !cashBox.getType().equals(CashType.EXTRACTION))
                .mapToInt(CashBox::getSum)
                .sum();
    }

// 28 september 2019
    public int getOutlayForMonth(int month, int year) {

        // FROM is not included !!!!
        return repository.findAll().stream()
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().getMonthValue() == month)
                .filter(cashBox -> cashBox.getDateTime().toLocalDate().getYear() == year)
                .filter(cashBox -> cashBox.getSum()<0)
                .filter(cashBox -> !cashBox.getType().equals(CashType.EXTRACTION))
                .mapToInt(CashBox::getSum)
                .sum();
    }



    //----------------------  injection
   // @Scheduled(cron = "0 40 12 * * *")
    private void testOutlay(){
        System.out.println("Started");
        repository.findAll().stream()
                .filter(cashBox
                        -> cashBox.getDateTime().toLocalDate().isAfter(LocalDate.of(2019,9,2)))
                .filter(cashBox
                        -> cashBox.getDateTime().toLocalDate().isBefore(LocalDate.of(2019,9,10)))
                .map(CashBox::getDateTime).sorted()
                .forEach(System.out::println);

        System.out.println("Finished");

    }

    // чего не достает  в кешбоксе  - взять из селери
    private void inject1(){
         int doctorId = 2;
         LocalDateTime date1 = LocalDateTime.of(2019,Month.SEPTEMBER,2,13,38);
         LocalDateTime date2 = LocalDateTime.of(2019,Month.FEBRUARY,20,8,4);
         this.injectPair(doctorId,date1,4000);
         this.injectPair(doctorId,date2,300);

          doctorId = 4;
         date1 = LocalDateTime.of(2019,Month.FEBRUARY,12,13,43,3);
        this.injectPair(doctorId,date1,9000);

        doctorId =5;
        date1 = LocalDateTime.of(2019,Month.FEBRUARY,12,8,26,27);
        this.injectPair(doctorId,date1,2000);
        //
          doctorId =8;
        date1 = LocalDateTime.of(2019,Month.FEBRUARY,23,14,18,37);
       this.injectPair(doctorId,date1,2500);

          doctorId = 17;
        date1 = LocalDateTime.of(2019,Month.FEBRUARY,8,9,21,50);
      this.injectPair(doctorId,date1,1500);

          doctorId = 19;
        date1 = LocalDateTime.of(2019,Month.FEBRUARY,19,10,19,43);
    this.injectPair(doctorId,date1,5400);

          doctorId = 22;
        date1 = LocalDateTime.of(2019,Month.FEBRUARY,9,14,1,21);
        date2 = LocalDateTime.of(2019,Month.FEBRUARY,23,14,19,36);
     this.injectPair(doctorId,date1,3140);
     this.injectPair(doctorId,date2,2200);

    }
    private List<CashBox> injectPair(int doctorId, LocalDateTime dateTime, int sum){
        List<CashBox> list = new ArrayList<>();

        CashBox inCashBox = new CashBox();
        inCashBox.setType(CashType.EXTRACTION);
        inCashBox.setDateTime(dateTime);
        inCashBox.setDoctorId(1);
        inCashBox.setDesc("поповнення каси для видачы з.п. " +
                doctorService.getDoctor(doctorId).getFullName());
        inCashBox.setSum(sum);
        inCashBox.setItemId(settings.get().getExtractionItemId());
        list.add(inCashBox);

        CashBox outCashBox = new CashBox();
        outCashBox.setType(CashType.SALLARY);
        outCashBox.setDateTime(dateTime);
        outCashBox.setDoctorId(doctorId);
        outCashBox.setDesc("з.п. " +
                doctorService.getDoctor(doctorId).getFullName());
        outCashBox.setSum(-1*sum);
        outCashBox.setItemId(settings.get().getSalaryItemId());
        list.add(outCashBox);
        System.out.println(" ----------  pair is injected");
        return repository.saveAll(list);
    }


}