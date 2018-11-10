package com.med.services.cashbox.impls;

import com.med.model.CashBox;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.repository.cashbox.CashRepository;
import com.med.services.cashbox.interfaces.ICashBoxService;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 31.10.18.
 */
@Service
public class CashBoxServiceImpl implements ICashBoxService {

    @Autowired
    CashRepository repository;

    @Autowired
    SalaryServiceImpl salaryService;

    @PostConstruct
    void init(){
      //  repository.save( new CashBox(LocalDateTime.now(), null, 1, "Test", 0));
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

    // TODO: findAll to smth elegant
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
        Salary salary = new Salary(1, LocalDateTime.now(), SalaryType.BUZUNAR, rest);
        salaryService.createSalary(salary);
        return repository.save(cashBox);
    }
}
