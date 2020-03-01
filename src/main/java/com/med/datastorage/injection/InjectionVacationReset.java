package com.med.datastorage.injection;

import com.med.model.Doctor;
import com.med.repository.hotel.ChamberRepository;
import com.med.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by george on 24.03.19.
 */
@Service
public class InjectionVacationReset {

    @Autowired
    DoctorService doctorService;


 @PostConstruct
    void init() {
       List<Doctor> allDoctors = doctorService.getAll();
       allDoctors.stream().forEach(doctor -> {
           doctor.setDaysOff(0);
           System.out.println(doctor.getFullName());
        doctorService.updateDoctor(doctor);
       });
    }


}
