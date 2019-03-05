package com.med.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Doctor;
import com.med.model.Usi;
import com.med.repository.UsiRepository;

@Service
public class UsiService {

	@Autowired
    UsiRepository repository;
	
	@Autowired
	DoctorService doctorService;

    public List<Usi> getPatientUsiList(String patientId) {
    	List<Usi> list = repository.findByPatientId(patientId);
    	list.forEach(doc -> {
    		doc.setDoctor(doctorService.getDoctor(doc.getDoctorId()).getFullName());
    	});
        return list;
    }
    
    public Usi getById(String id) {
    	return repository.findById(id).orElse(null);
    }
    
    public void create(Usi usi) {
        repository.insert(usi);
    }

    public void update(Usi usi) {
        repository.save(usi);
    }
    
    public void delete(String id) {
        repository.deleteById(id);
    }
}