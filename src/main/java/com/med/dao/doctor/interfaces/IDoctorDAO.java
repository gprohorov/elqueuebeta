package com.med.dao.doctor.interfaces;

import com.med.model.Doctor;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IDoctorDAO {
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor);
    Doctor getDoctor(int id);
    Doctor deleteDoctor(int id);
    List<Doctor> getAll();

    List<Doctor> getDoctorListByLetters(String lastName);
}
