package com.med.services.doctor.impls;

import com.med.model.Doctor;
import com.med.repository.DoctorRepository;
import com.med.services.doctor.interfaces.IDoctorService;
import com.med.services.salarydto.impls.SalaryDTOServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class DoctorServiceImpl implements IDoctorService {

    private List<Doctor> doctors = new ArrayList<>();

    @Autowired
    DoctorRepository repository;
    
    @Autowired
    SalaryDTOServiceImpl salaryDTOService;

/*

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
        doctors = dataStorage.getDoctors();
        repository.saveAll(doctors);
    }

*/


    @Override
    public Doctor createDoctor(Doctor doctor) {
        if (doctor.getId()==0) {
            int id = this.getAll().stream().mapToInt(Doctor::getId).max().getAsInt() + 1;
            doctor.setId(id+1);
        }
        return repository.insert(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor.getId()==0) {
            int id = this.getAll().stream()
                    .mapToInt(Doctor::getId).max().getAsInt() + 1;
            doctor.setId(id);
        }
        repository.save(doctor);
        salaryDTOService.recalculateDTO(doctor.getId());
        return doctor;
    }

    @Override
    public Doctor getDoctor(int id) {
        return repository.findById(id).get();
    }

    public Doctor getDoctorByUserId(String id) {
        return repository.findAll().stream().filter(doctor -> doctor.getUserId() != null)
                .filter(doctor -> doctor.getUserId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public Doctor deleteDoctor(int id) {
        Doctor doctor = this.getDoctor(id);
        repository.deleteById(id);
        return doctor;
    }

    @Override
    public List<Doctor> getAll() {
     return repository.findAll().stream().sorted(Comparator.comparing(Doctor::getId))
                .collect(Collectors.toList());
    }



}
