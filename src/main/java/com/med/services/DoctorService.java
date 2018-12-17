package com.med.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Doctor;
import com.med.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;
    
    @Autowired
    SalaryDTOService salaryDTOService;

    public Doctor createDoctor(Doctor doctor) {
        if (doctor.getId() == 0) {
            doctor.setId(this.getAll().stream().mapToInt(Doctor::getId).max().getAsInt() + 1);
        }
        return repository.insert(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
    	if (doctor.getId() == 0) {
            doctor.setId(this.getAll().stream().mapToInt(Doctor::getId).max().getAsInt() + 1);
        }
        repository.save(doctor);
        salaryDTOService.recalculateDTO(doctor.getId());
        return doctor;
    }

    public Doctor getDoctor(int id) {
        return repository.findById(id).get();
    }

    public Doctor getDoctorByUserId(String id) {
        return repository.findAll().stream().filter(doctor -> doctor.getUserId() != null)
            .filter(doctor -> doctor.getUserId().equals(id)).findFirst().orElse(null);
    }

    public Doctor deleteDoctor(int id) {
        Doctor doctor = this.getDoctor(id);
        repository.deleteById(id);
        return doctor;
    }

    public List<Doctor> getAll() {
    	return repository.findAll().stream().sorted(Comparator.comparing(Doctor::getId))
            .collect(Collectors.toList());
    }
}