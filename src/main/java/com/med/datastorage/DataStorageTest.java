package com.med.datastorage;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Configuration
public class DataStorageTest {



    @Autowired
    TalonRepository talonRepository;

    @Autowired
    PatientServiceImpl patientService;


    public void NoapteBun(){
       List<Talon> talons =  talonRepository.findAll();

       talons.stream().forEach(talon -> {

                 if(   talon.getActivity().equals(Activity.ACTIVE)
                    || talon.getActivity().equals(Activity.TEMPORARY_NA)){

                     talon.setActivity(Activity.EXPIRED);
                 }
                });
        talonRepository.saveAll(talons);


        List<Patient> patients = patientService.getAllForToday();

        patients.stream().forEach(

                patient -> {
                    patient.setStartActivity(null);
                    patient.setLastActivity(null);
                }
        );

        patientService.saveAll(patients);





    }


    public void resetPatientsTable() {






        System.out.println(" talon table updated");
    }

}
