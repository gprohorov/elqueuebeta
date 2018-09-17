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
                 new Koika(211, "21.1", chamberService.getChamber(21), 300),
                 new Koika(212, "21.2", chamberService.getChamber(21), 300),
                 new Koika(221, "22.1", chamberService.getChamber(22), 300),
                 new Koika(222, "22.2", chamberService.getChamber(22), 300),
                 new Koika(231, "23.1", chamberService.getChamber(23), 300),
                 new Koika(232, "23.2", chamberService.getChamber(23), 300),
                 new Koika(241, "24.1", chamberService.getChamber(24), 300),
                 new Koika(242, "24.2", chamberService.getChamber(24), 300),

                 new Koika(311, "31.1", chamberService.getChamber(31), 450),
                 new Koika(312, "31.2", chamberService.getChamber(31), 450),

                 new Koika(321, "32.1", chamberService.getChamber(32), 300),
                 new Koika(322, "32.2", chamberService.getChamber(32), 300),

                 new Koika(331, "33.1", chamberService.getChamber(33), 350),
                 new Koika(332, "33.2", chamberService.getChamber(33), 350),

                 new Koika(341, "34.1", chamberService.getChamber(34), 300),
                 new Koika(342, "34.2", chamberService.getChamber(34), 300),
                 new Koika(351, "35.1", chamberService.getChamber(35), 300),
                 new Koika(352, "35.2", chamberService.getChamber(35), 300),
                 new Koika(361, "36.1", chamberService.getChamber(36), 300),
                 new Koika(362, "36.2", chamberService.getChamber(36), 300),
                 new Koika(371, "37.1", chamberService.getChamber(37), 300),
                 new Koika(372, "37.2", chamberService.getChamber(37), 300),

                 new Koika(391, "39.1", chamberService.getChamber(31), 450),
                 new Koika(392, "39.2", chamberService.getChamber(31), 450)

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
