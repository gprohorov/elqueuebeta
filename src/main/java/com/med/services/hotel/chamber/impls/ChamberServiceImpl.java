package com.med.services.hotel.chamber.impls;

import com.med.model.Category;
import com.med.model.hotel.Chamber;
import com.med.repository.hotel.ChamberRepository;
import com.med.services.hotel.chamber.interfaces.IChamberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 01.06.18.
 */
@Service
public class ChamberServiceImpl implements IChamberService{

    private  List<Chamber> chambers;

    @Autowired
    ChamberRepository repository;

    @PostConstruct
    void init() {
    /*
		repository.deleteAll();
        	chambers = new ArrayList<>(
                Arrays.asList(
                new Chamber(21,"21", Category.SOC, 2, "", 2),
                new Chamber(22,"22", Category.SOC, 2, "", 2),
                new Chamber(23,"23", Category.SOC, 2, "", 2),
                new Chamber(24,"24", Category.SOC, 2, "", 2),

                new Chamber(31,"31", Category.LUX, 3, "", 2),
                new Chamber(32,"32", Category.SOC, 3, "", 2),
                new Chamber(33,"33", Category.SEMILUX, 3, "", 2),
                new Chamber(34,"34", Category.SOC, 3, "", 2),
                new Chamber(35,"35", Category.SOC, 3, "", 2),
                new Chamber(36,"36", Category.SOC, 3, "", 2),
                new Chamber(37,"37", Category.SOC, 3, "", 2),
                new Chamber(39,"39", Category.LUX, 3, "", 2)
                )
        );
       repository.saveAll(chambers);
    */
    }

    @Override
    public Chamber createChamber(Chamber chamber) {
      //  chambers.add(chamber); 066 8 55 00 35
        return repository.save(chamber);
    }

    @Override
    public List<Chamber> getAll() {

        return repository.findAll();
    }

    @Override
    public Chamber getChamber(int chamberId) {
        return repository.findById(chamberId).orElse(null);
    }



}
