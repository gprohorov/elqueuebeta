package com.med.services.doctor.impls;

import com.med.model.Doctor;
import com.med.repository.doctor.DoctorRepository;
import com.med.services.doctor.interfaces.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class DoctorServiceImpl implements IDoctorService {

    private List<Doctor> doctors = new ArrayList<>();

    @Autowired
    DoctorRepository repository;


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
            doctor.setId(id);
        }
        return repository.insert(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {


        return repository.save(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        return
                repository.findById(id).get();
    }

    @Override
    public Doctor deleteDoctor(int id) {
        Doctor doctor = this.getDoctor(id);
        repository.deleteById(id);
        return doctor;
    }

    @Override
    public List<Doctor> getAll() {
        return repository.findAll();
    }
/*

    @Override
    public Doctor getDoctorListByName(String lastName) {

        return repository.findAll().stream()
                .filter(doctor -> doctor.getLastName().equals(lastName))
                .findFirst().get();
    }
*/


}
