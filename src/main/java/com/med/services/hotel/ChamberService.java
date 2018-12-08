package com.med.services.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.hotel.Chamber;
import com.med.repository.hotel.ChamberRepository;

@Service
public class ChamberService {

    @Autowired
    ChamberRepository repository;

    public Chamber createChamber(Chamber chamber) {
        return repository.save(chamber);
    }

    public List<Chamber> getAll() {
        return repository.findAll();
    }

    public Chamber getChamber(int chamberId) {
        return repository.findById(chamberId).orElse(null);
    }
}