package com.med.datastorage;

import com.med.model.Procedure;
import com.med.model.Therapy;
import com.med.repository.therapy.TherapyRepository;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 07.05.18.
 */

@Component
public class TherapyStorage {

    private List<Therapy> therapies;

    private Procedure registration;
    private Procedure diagnostics ;
    private Procedure manual ;
    private Procedure pulling ;
    private Procedure mechmassasge;
    private Procedure massage ;
    private Procedure ultrasound ;

    List<Procedure> procedures;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TherapyRepository therapyRepository;






    @PostConstruct
     void init() {
/*        registration = procedureService.getProcedure(1);
        diagnostics = procedureService.getProcedure(2);
        manual = procedureService.getProcedure(3);
     pulling = procedureService.getProcedure(4);

        massage = procedureService.getProcedure(6);
        ultrasound= procedureService.getProcedure(7);
     */
        mechmassasge= procedureService.getProcedure(5);


        procedures = new ArrayList<>(
               Arrays.asList(mechmassasge)
        );

        therapies = new ArrayList<>(
            // 27 -
            Arrays.asList(
                    new Therapy(22, LocalDateTime.now().minusDays(1)
                            , null, "Diag", 23, "notes "
                            ,"pic", procedures, 3) ,

                    new Therapy(23, LocalDateTime.now().minusDays(1)
                            , null, "Diag", 23, "notes "
                            ,"pic", procedures, 3)
            )
    );
/*

        therapyRepository.deleteAll();
        therapyRepository.saveAll(therapies);
*/

}
/*
private Talon resetTestTalon(Talon talon){

    talon.setDate(LocalDate.now());
    talon.setDuration(0);
    talon.setExecutionTime(null);
    talon.setDoctor(null);
    talon.setSum(0);

        return talon;

}
 public void rewriteTestTalons(){
        this.talons = repository.findAll();
        talons.stream().forEach(talon ->this.rewriteTestTalons());
        repository.saveAll(talons);


 }*/


}
