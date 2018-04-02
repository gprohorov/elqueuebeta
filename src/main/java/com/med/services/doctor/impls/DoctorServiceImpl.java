package com.med.services.doctor.impls;

import com.med.dao.doctor.impls.DoctorDAOImpl;
import com.med.model.Doctor;
import com.med.services.doctor.interfaces.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    DoctorDAOImpl doctorDAO;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorDAO.createDoctor(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        return doctorDAO.updateDoctor(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        return doctorDAO.getDoctor(id);
    }

    @Override
    public Doctor deleteDoctor(int id) {
        return doctorDAO.deleteDoctor(id);
    }

    @Override
    public List<Doctor> getAll() {
        return doctorDAO.getAll();
    }

    @Override
    public List<Doctor> getDoctorListByLetters(String lastName) {
       // return doctorDAO.getDoctorListByLetters(lastName);
        return this.getAll();
    }
}
