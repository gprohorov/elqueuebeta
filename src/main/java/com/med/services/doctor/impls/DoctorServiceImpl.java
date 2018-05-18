package com.med.services.doctor.impls;

import com.med.model.Doctor;
import com.med.repository.doctor.DoctorRepository;
import com.med.services.doctor.interfaces.IDoctorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (doctor.getId()==0) {
            int id = this.getAll().stream()
                    .mapToInt(Doctor::getId).max().getAsInt() + 1;
            doctor.setId(id);
        }
        return repository.save(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        return repository.findById(id).get();
    }

    public Doctor getDoctorByUserId(String id) {
        return repository.findAll().stream().filter(doctor ->
                doctor.getUserId().equals(new ObjectId(id)))
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

      //  List<Doctor> doctors =repository.findAll();
       // doctors.stream().forEach(doctor -> doctor.setUser(UserRepository.findById(doctor.getUserId).get());

        return repository.findAll().stream().sorted().collect(Collectors.toList());
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
