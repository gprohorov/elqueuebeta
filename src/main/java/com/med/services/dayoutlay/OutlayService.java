package com.med.services.dayoutlay;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.Outlay;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 22.11.18.
 */
@Service
public class OutlayService {

    @Autowired
    CashBoxServiceImpl cashBoxService;

    public Outlay getOutlay(LocalDate from, LocalDate to){
        Outlay dayOutlay = new Outlay();

        // Refactoring is required
        List<CashBox> cashes = cashBoxService.getAll().stream()
                .filter(csh->csh.getDateTime().toLocalDate().isAfter(LocalDate.now().minusDays(1)))
                .filter(csh->csh.getDateTime().toLocalDate().isBefore(LocalDate.now().plusDays(1)))
                .filter(csh->csh.getSum()<0)
                .collect(Collectors.toList());

        dayOutlay.setDate(LocalDate.now());

        int salary = cashes.stream().filter(cash->cash.getType().equals(CashType.SALLARY))
                .mapToInt(CashBox::getSum).sum();
        dayOutlay.setSalary(salary);

        int extraction = cashes.stream().filter(cash->cash.getType().equals(CashType.EXTRACTION))
                .mapToInt(CashBox::getSum).sum();
        dayOutlay.setExtraction(extraction);

        int tax = cashes.stream().filter(cash->cash.getType().equals(CashType.TAX))
                .mapToInt(CashBox::getSum).sum();
        dayOutlay.setTaxes(tax);

        int machine = cashes.stream().filter(cash->cash.getType().equals(CashType.MACHINE))
                .mapToInt(CashBox::getSum).sum();
        dayOutlay.setMachine(machine);

        int other = cashes.stream().filter(cash->cash.getType().equals(CashType.OTHER))
                .mapToInt(CashBox::getSum).sum();
        dayOutlay.setOther(other);

        int total = cashes.stream().mapToInt(CashBox::getSum).sum();
        dayOutlay.setOther(other);

        int kitchen = total - salary - extraction - tax - machine - other;
        dayOutlay.setKitchen(kitchen);
        return dayOutlay;
    }

    public List<CashBox> getOutlaySalary(LocalDate parse, LocalDate parse1) {
        return cashBoxService.getAll().stream()
                .filter(csh->csh.getDateTime().toLocalDate().isAfter(LocalDate.now().minusDays(1)))
                .filter(csh->csh.getDateTime().toLocalDate().isBefore(LocalDate.now().plusDays(1)))
                .filter(csh->csh.getSum()<0)
                .filter(csh->csh.getType().equals(CashType.SALLARY))
                .collect(Collectors.toList());
    }

    public List<CashBox> getOutlayMachine(LocalDate parse, LocalDate parse1) {
        return cashBoxService.getAll().stream()
                .filter(csh->csh.getDateTime().toLocalDate().isAfter(LocalDate.now().minusDays(1)))
                .filter(csh->csh.getDateTime().toLocalDate().isBefore(LocalDate.now().plusDays(1)))
                .filter(csh->csh.getSum()<0)
                .filter(csh->csh.getType().equals(CashType.MACHINE))
                .collect(Collectors.toList());
    }





}
