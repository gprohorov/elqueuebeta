package com.med.services.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.hotel.Koika;
import com.med.repository.hotel.KoikaRepository;

@Service
public class KoikaService {

    @Autowired
    ChamberService chamberService;

    @Autowired
    KoikaRepository repository;

    public Koika createKoika(Koika koika) {
        return repository.save(koika);
    }

    public List<Koika> getAll() {
        return repository.findAll();
    }

    public Koika getKoika(int koikaId) {
        return repository.findById(koikaId).orElse(null);
    }

    public  List<Koika> saveAll(List<Koika> koikas) {
        return repository.saveAll(koikas);
    }
}