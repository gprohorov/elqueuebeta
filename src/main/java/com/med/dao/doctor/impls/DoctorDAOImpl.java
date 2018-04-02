package com.med.dao.doctor.impls;

import com.med.DataStorage;
import com.med.dao.doctor.interfaces.IDoctorDAO;
import com.med.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        if (doctor.getId()==0){
            int doctord = this.getAll().stream().mapToInt(el->el.getId())
                    .max().getAsInt() + 1;
            doctor.setId(doctord);
            this.createDoctor(doctor);
        }
        else {
            Doctor oldValues = this.getDoctor(doctor.getId());
            int index = this.getAll().indexOf(oldValues);
            this.getAll().set(index,doctor);
        }
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



    @Override
    public List<Doctor> getDoctorListByLetters(String letters) {
        if (letters.equals("")||letters.equals(null)){
            return this.getAll();}
        else {
            return  this.getAll().stream()
                    .filter(doctor -> doctor.getLastName()
                            .contains(letters))
                    .collect(Collectors.toList());
        }
    }
}
