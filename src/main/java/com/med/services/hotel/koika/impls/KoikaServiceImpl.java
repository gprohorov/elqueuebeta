package com.med.services.hotel.koika.impls;

import com.med.model.hotel.Koika;
import com.med.repository.hotel.KoikaRepository;
import com.med.services.hotel.koika.interfaces.IKoikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 03.06.18.
 */
@Service
public class KoikaServiceImpl implements IKoikaService {

    @Autowired
    KoikaRepository repository;
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
