package com.med.services.hotel.chamber.impls;

import com.med.model.hotel.Chamber;
import com.med.repository.hotel.ChamberRepository;
import com.med.services.hotel.chamber.interfaces.IChamberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    void init(){
/*
        chambers = new ArrayList<>(

                Arrays.asList(

                       new Chamber(1, "1", Category.SOC,"",2)
                     , new Chamber(2, "2", Category.SOC,"",2)
                     , new Chamber(3, "3", Category.SOC,"",2)
                     , new Chamber(4, "4", Category.SOC,"",2)
                     , new Chamber(5, "5", Category.SOC,"",2)
                     , new Chamber(6, "6", Category.SOC,"",2)
                     , new Chamber(7, "7", Category.SOC,"",2)
                     , new Chamber(8, "8", Category.SOC,"",2)
                     , new Chamber(9, "9", Category.SOC,"",2)



                )



        );

        repository.saveAll(chambers);
        */

    }

    @Override
    public Chamber createChamber(Chamber chamber) {
      //  chambers.add(chamber);
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
