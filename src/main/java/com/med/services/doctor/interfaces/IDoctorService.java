package com.med.services.doctor.interfaces;

import com.med.dao.doctor.interfaces.IDoctorDAO;
import com.med.model.Doctor;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IDoctorService extends IDoctorDAO {


    List<Doctor> getDoctorListByLetters(String lastName);
}
