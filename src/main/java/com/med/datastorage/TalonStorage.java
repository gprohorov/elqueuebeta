package com.med.datastorage;

import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.repository.patient.PatientRepository;
import com.med.repository.talon.TalonRepository;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 07.05.18.
 */
@Component
public class TalonStorage {


    private List<Talon> talons;
    private List<Patient> patients;
    private Procedure registration;
    private Procedure diagnostics;
    private Procedure manual;
    private Procedure pulling;
    private Procedure mechmassasge;
    private Procedure massage;
    private Procedure ultrasound;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TalonRepository repository;


    @Autowired
    PatientRepository patientRepository;


    @PostConstruct
    void init() {
        registration = procedureService.getProcedure(1);
        diagnostics = procedureService.getProcedure(2);
        manual = procedureService.getProcedure(3);
        pulling = procedureService.getProcedure(4);
        mechmassasge = procedureService.getProcedure(5);
        massage = procedureService.getProcedure(6);
        ultrasound = procedureService.getProcedure(7);

        patients = patientRepository.findAll();


        talons = new ArrayList<>(

                Arrays.asList(

                        /// tail to registrature
                        new Talon(patients.get(16).getId(), registration),
                        new Talon(patients.get(17).getId(), registration),
                        new Talon(patients.get(14).getId(), registration),
                        new Talon(patients.get(15).getId(), registration),

                        //// tail to diagnostics
                        new Talon(patients.get(12).getId(), diagnostics),
                        new Talon(patients.get(13).getId(), diagnostics),
                        new Talon(patients.get(14).getId(), diagnostics),
                        new Talon(patients.get(15).getId(), diagnostics),


                        // massage therapy
                        new Talon(patients.get(13).getId(), massage),
                        new Talon(patients.get(14).getId(), massage),
                        new Talon(patients.get(15).getId(), massage),
                        new Talon(patients.get(16).getId(), massage),

                        // puling
                        new Talon(patients.get(15).getId(), pulling),
                        new Talon(patients.get(16).getId(), pulling),
                        new Talon(patients.get(17).getId(), pulling),
                        new Talon(patients.get(18).getId(), pulling),


                        //// tail to manual
                        new Talon(patients.get(17).getId(),manual),
                        new Talon(patients.get(18).getId(), manual),
                        new Talon(patients.get(19).getId(), manual),
                        new Talon(patients.get(20).getId(), manual),

                        //// tail to Ultra Sound
                        new Talon(patients.get(19).getId(), ultrasound),
                        new Talon(patients.get(20).getId(), ultrasound),
                        new Talon(patients.get(21).getId(), ultrasound),
                        new Talon(patients.get(22).getId(), ultrasound)


                )


        );
        //  System.out.println(talons.get(0).getDate());
     //   repository.deleteAll();
     //    repository.saveAll(talons);
        // System.out.println(repository.findAll().get(1).getDate());
/*
    }

private Talon resetTestTalon(Talon talon){

    talon.setDate(LocalDate.now());
    talon.setStart(null);
    talon.setExecutionTime(null);
    talon.setDoctor(null);
    talon.setSum(0);

        return talon;

}
 public void rewriteTestTalons(){
        this.talons = repository.findAll();
        talons.stream().forEach(talon ->this.rewriteTestTalons());
        repository.saveAll(talons);


 }

*/

    }
}