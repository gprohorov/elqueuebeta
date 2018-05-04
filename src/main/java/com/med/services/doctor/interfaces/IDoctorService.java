package com.med.services.doctor.interfaces;

import com.med.model.Doctor;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IDoctorService  {
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor doctor);
    Doctor getDoctor(int id);
    Doctor deleteDoctor(int id);
    List<Doctor> getAll();

    //List<Doctor> getDoctorListByLetters(String lastName);
}
