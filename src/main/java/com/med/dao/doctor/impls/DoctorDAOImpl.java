package com.med.dao.doctor.impls;

import com.med.DataStorage;
import com.med.dao.doctor.interfaces.IDoctorDAO;
import com.med.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class DoctorDAOImpl implements IDoctorDAO {


   // private List<Doctor> doctors = new ArrayList<>();

    @Autowired
    DataStorage dataStorage;

/*
    @PostConstruct
    void init(){
        doctors = dataStorage.getDoctors();
    }
*/

    @Override
    public Doctor createDoctor(Doctor doctor) {

        this.getAll().add(doctor);
        return doctor;
    }

    @Override
    public Doctor getDoctor(int id) {
        return this.getAll().stream()
                .filter(doctor -> doctor.getId() == id)
                .findFirst().get();
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        Doctor oldValues = this.getDoctor(doctor.getId());
        int index = dataStorage.getDoctors().indexOf(oldValues);
        dataStorage.getDoctors().set(index,doctor);
        return doctor;

    }



    @Override
    public Doctor deleteDoctor(int id) {
        Doctor doctor = this.getDoctor(id);
        int index = this.getAll().indexOf(doctor);
        this.getAll().remove(index);
        return doctor;
    }



    @Override
    public List<Doctor> getAll() {
        return dataStorage.getDoctors();
    }
}
