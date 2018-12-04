package com.med.services.cashbox.impls;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Response;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.model.Settings;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.model.statistics.dto.accounting.CurrentReport;
import com.med.repository.cashbox.CashRepository;
import com.med.services.cashbox.interfaces.ICashBoxService;
import com.med.services.salary.impls.SalaryServiceImpl;
import com.med.services.salarydto.impls.SalaryDTOServiceImpl;
import com.med.services.settings.impls.SettingsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 31.10.18.
 */
@Service
public class CashBoxServiceImpl implements ICashBoxService {

    @Autowired
    CashRepository repository;

    @Autowired
    SalaryServiceImpl salaryService;

    @Autowired
    SalaryDTOServiceImpl salaryDTOService;

    @Autowired
    SettingsServiceImpl settingsService; 

    @PostConstruct
    void init(){
      //  repository.save( new CashBox(LocalDateTime.now(), null, 1, "Test", 0));
    }
    public List<CashBox> getAll(){
        return repository.findAll();
    }

    public CashBox createCash(CashBox cash) {
    	return repository.save(cash);
    }
    
    @Override
    public Response saveCash(CashBox cash) {
    	if (Math.abs(cash.getSum()) > this.getCashBox()) {
    		return new Response(false, "В касі не вистачає коштів.");
    	}
        repository.save(cash);
        return new Response(true, "");
    }

    @Override
    public CashBox getCashBox(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public int getCashBox() {
        return repository.findAll().stream().mapToInt(CashBox::getSum).sum();
    }

    // TODO: findAll to smth elegant
    @Override
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
        CashBox cashBox =
                new CashBox(LocalDateTime.now(), null, 1, 
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
    
    //-------------------------------- 18 nov
    // report input/output cash from kassa in details
    public CurrentReport getCurrentReportDetails() {
        CurrentReport report = new CurrentReport();
        //HARDCODE
        List<CashBox> list = repository.findAll().stream()
            .filter(cash->cash.getDateTime().toLocalDate().equals(LocalDate.now()))
            .collect(Collectors.toList());

        int payed = list.stream()
            .filter(cash->cash.getSum()>0)
            .mapToInt(CashBox::getSum).sum();
        report.setPayed(payed);

        int salary = list.stream()
            .filter(cash->cash.getType().equals(CashType.SALLARY))
            .mapToInt(CashBox::getSum).sum();
        report.setSalary(salary);

        int extraction = list.stream()
            .filter(cash->cash.getType().equals(CashType.EXTRACTION))
            .mapToInt(CashBox::getSum).sum();
        report.setExtraction(extraction);

        int milk = list.stream()
            .filter(cash->cash.getType().equals(CashType.MILK))
            .mapToInt(CashBox::getSum).sum();
        report.setMilk(milk);

        int bread = list.stream()
            .filter(cash->cash.getType().equals(CashType.BREAD))
            .mapToInt(CashBox::getSum).sum();
        report.setBread(bread);

        int conserve = list.stream()
            .filter(cash->cash.getType().equals(CashType.CONSERVE))
            .mapToInt(CashBox::getSum).sum();
        report.setConservation(conserve);

        int food = list.stream()
            .filter(cash->cash.getType().equals(CashType.FOOD))
            .mapToInt(CashBox::getSum).sum();
        report.setFood(food);

        int tax = list.stream()
            .filter(cash->cash.getType().equals(CashType.TAX))
            .mapToInt(CashBox::getSum).sum();
        report.setTax(tax);

        int machine = list.stream()
            .filter(cash->cash.getType().equals(CashType.MACHINE))
            .mapToInt(CashBox::getSum).sum();
        report.setTax(machine);

        int other = list.stream()
            .filter(cash->cash.getType().equals(CashType.OTHER))
            .mapToInt(CashBox::getSum).sum();
        report.setOther(other);

        int given = salary+extraction+milk+bread+conserve+food+machine+other;
        report.setGiven(given);

        int available =payed +given;
        report.setAvailable(available);

        return report;
    }
}
