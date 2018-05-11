package com.med.datastorage;

import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 07.05.18.
 */

@Component
public class TalonStorage   {

    private List<Talon> talons;
    private Procedure registration;
    private Procedure diagnostics ;
    private Procedure manual ;
    private Procedure pulling ;
    private Procedure mechmassasge;
    private Procedure massage ;
    private Procedure ultrasound ;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TalonRepository repository;




    @PostConstruct
     void init() {
     registration = procedureService.getProcedure(1);
        diagnostics = procedureService.getProcedure(2);
        manual = procedureService.getProcedure(3);
        pulling = procedureService.getProcedure(4);
        mechmassasge= procedureService.getProcedure(5);
        massage = procedureService.getProcedure(6);
        ultrasound= procedureService.getProcedure(7);

        talons = new ArrayList<>(

            Arrays.asList(

                    /// tail to registrature
                    new Talon(16,registration),
                    new Talon(17,registration),
                    new Talon(14,registration),
                    new Talon(15,registration),

                    //// tail to diagnostics
                 new Talon(12,diagnostics),
                 new Talon(13,diagnostics),
                  new Talon(14,diagnostics),
                  new Talon(15,diagnostics),


                    // manual therapy
                    new Talon(13,manual),
                    new Talon(14,manual),
                    new Talon(15,manual),
                    new Talon(16,manual),

                    // puling
                    new Talon(17,pulling),
                    new Talon(18,pulling),
                    new Talon(19,pulling),
                    new Talon(20,pulling),

                    //// tail to massage
                    new Talon(19,massage),
                    new Talon(20,massage),
                    new Talon(21,massage),
                    new Talon(22,massage),

                    //// tail to Ultra Sound
                    new Talon(21,ultrasound),
                    new Talon(22,ultrasound),
                    new Talon(23,ultrasound),
                    new Talon(24,ultrasound),
                    new Talon(25,ultrasound)

            )


    );
      //  System.out.println(talons.get(0).getDate());
      //  repository.deleteAll();
      //  repository.saveAll(talons);
       // System.out.println(repository.findAll().get(1).getDate());

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


}
