package com.med.services.hotel.koika.impls;

import com.med.model.hotel.Koika;
import com.med.repository.hotel.KoikaRepository;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.koika.interfaces.IKoikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 03.06.18.
 */
@Service
public class KoikaServiceImpl implements IKoikaService {

    List<Koika> koikas;

    @Autowired
    ChamberServiceImpl chamberService;

    @Autowired
    KoikaRepository repository;


    @PostConstruct
    void init(){
        repository.deleteAll();

        koikas = new ArrayList<>(
             Arrays.asList(
                 new Koika(211, "211", chamberService.getChamber(21), 300),
                 new Koika(212, "212", chamberService.getChamber(21), 300),
                 new Koika(221, "221", chamberService.getChamber(22), 300),
                 new Koika(222, "222", chamberService.getChamber(22), 300),
                 new Koika(231, "231", chamberService.getChamber(23), 300),
                 new Koika(232, "232", chamberService.getChamber(23), 300),
                 new Koika(241, "241", chamberService.getChamber(24), 300),
                 new Koika(242, "242", chamberService.getChamber(24), 300),

                 new Koika(311, "311", chamberService.getChamber(31), 450),
                 new Koika(312, "312", chamberService.getChamber(31), 450),

                 new Koika(321, "321", chamberService.getChamber(32), 300),
                 new Koika(322, "322", chamberService.getChamber(32), 300),

                 new Koika(331, "331", chamberService.getChamber(33), 350),
                 new Koika(332, "332", chamberService.getChamber(33), 350),

                 new Koika(341, "341", chamberService.getChamber(34), 300),
                 new Koika(342, "342", chamberService.getChamber(34), 300),
                 new Koika(351, "351", chamberService.getChamber(35), 300),
                 new Koika(352, "352", chamberService.getChamber(35), 300),
                 new Koika(361, "361", chamberService.getChamber(36), 300),
                 new Koika(362, "362", chamberService.getChamber(36), 300),
                 new Koika(371, "371", chamberService.getChamber(37), 300),
                 new Koika(372, "372", chamberService.getChamber(37), 300),

                 new Koika(391, "391", chamberService.getChamber(31), 450),
                 new Koika(392, "392", chamberService.getChamber(31), 450)

                )
        );

       repository.saveAll(koikas);
    }



    @Override
    public Koika createKoika(Koika koika) {
        return repository.save(koika);
    }

    @Override
    public List<Koika> getAll() {
        return repository.findAll();
    }

    @Override
    public Koika getKoika(int koikaId) {
        return repository.findById(koikaId).orElse(null);
    }

    public  List<Koika> saveAll(List<Koika> koikas)
    {
        return repository.saveAll(koikas);
    }
}
