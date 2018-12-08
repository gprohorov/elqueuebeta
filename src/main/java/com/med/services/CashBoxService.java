package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Response;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.model.Settings;
import com.med.repository.CashRepository;

@Service
public class CashBoxService {

    @Autowired
    CashRepository repository;

    @Autowired
    SalaryService salaryService;

    @Autowired
    SalaryDTOService salaryDTOService;

    @Autowired
    SettingsService settingsService; 

    public List<CashBox> getAll(){
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
			"Поповнення каси" , -1*cash.getSum());
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

    // TODO: findAll to smth elegant
    public int getTodayGiven() {
        return  repository.findAll().stream()
            .filter(el->el.getDateTime().toLocalDate().equals(LocalDate.now()))
            .filter(el->el.getSum()<0)
            .mapToInt(CashBox::getSum).sum();
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
}
