package com.med.datastorage;

import com.med.model.Patient;
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


    public void reset(){
      // List<Talon> talons =  talonRepository.findAll();
/*
       talons.stream().forEach(talon -> {

                 if(   talon.getActivity().equals(Activity.ACTIVE)
                    || talon.getActivity().equals(Activity.TEMPORARY_NA)){

                     talon.setActivity(Activity.EXPIRED);
                 }
                });
       */

       // talonRepository.deleteAll(talons);
        talonRepository.deleteAll();


        List<Patient> patients = patientService.getAll("");

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
