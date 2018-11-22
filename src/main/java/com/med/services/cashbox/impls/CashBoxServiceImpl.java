package com.med.services.cashbox.impls;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.model.statistics.dto.accounting.CurrentReport;
import com.med.repository.cashbox.CashRepository;
import com.med.services.cashbox.interfaces.ICashBoxService;
import com.med.services.salary.impls.SalaryServiceImpl;
import com.med.services.salarydto.impls.SalaryDTOServiceImpl;
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


    @PostConstruct
    void init(){
      //  repository.save( new CashBox(LocalDateTime.now(), null, 1, "Test", 0));
    }
    public List<CashBox> getAll(){
        return repository.findAll();
    }

    @Override
    public CashBox saveCash(CashBox cash) {
        return repository.save(cash);
    }


    @Override
    public CashBox getCashBox(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public int getCashBox() {
        return repository.findAll().stream().mapToInt(CashBox::getSum).sum();
    }

    // TODO: findAll to smth elegant Hope1234

    @Override
    public int getTodayGiven() {
        return  repository.findAll().stream()
                .filter(el->el.getDateTime().toLocalDate().equals(LocalDate.now()))
                .filter(el->el.getSum()<0)
                .mapToInt(CashBox::getSum).sum();

    }

    public CashBox toZero(int sum) {
        int rest = sum;
        CashBox cashBox =
                new CashBox(LocalDateTime.now(), null, 1, "Кассу знято на " + rest, -1*rest);
        cashBox.setType(CashType.EXTRACTION);
        Salary salary = new Salary(1, LocalDateTime.now(), SalaryType.EXTRACTION, rest);
        salaryService.createSalary(salary);
       // salaryDTOService.payDoctorSalary(1, sum);
        return repository.save(cashBox);
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
