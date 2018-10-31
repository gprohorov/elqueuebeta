package com.med.services.cashbox.impls;

import com.med.model.CashBox;
import com.med.repository.cashbox.CashRepository;
import com.med.services.cashbox.interfaces.ICashBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * Created by george on 31.10.18.
 */
@Service
public class CashBoxServiceImpl implements ICashBoxService {

    @Autowired
    CashRepository repository;

    @PostConstruct
    void init(){
        repository.save( new CashBox(LocalDateTime.now(), null, 1, "Test", 0));
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

    public CashBox toZero() {
        int rest = this.getCashBox();
        CashBox cashBox =
                new CashBox(LocalDateTime.now(), null, 1, "Кассу знято", -1*rest);
        return repository.save(cashBox);
    }
}
