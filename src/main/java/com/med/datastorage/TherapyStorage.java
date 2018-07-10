package com.med.datastorage;

import org.springframework.stereotype.Component;

/**
 * Created by george on 07.05.18.
 */
@Component
public class TherapyStorage {
/*
    private List<Therapy> therapies;
    private List<Assignment> assignments;

    private Procedure registration;
    private Procedure diagnostics ;
    private Procedure manual ;
    private Procedure pulling ;
    private Procedure mechmassasge;
    private Procedure massage ;
    private Procedure ultrasound ;
    private Procedure magnet ;
    private Procedure laser ;

    List<Procedure> procedures;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    TherapyRepository therapyRepository;

    @PostConstruct
     void init() {
        registration = procedureService.getProcedure(1);
        diagnostics = procedureService.getProcedure(2);
        manual = procedureService.getProcedure(3);
        pulling = procedureService.getProcedure(4);
        mechmassasge = procedureService.getProcedure(5);
        massage = procedureService.getProcedure(6);
        ultrasound= procedureService.getProcedure(7);
        magnet = procedureService.getProcedure(8);
        laser = procedureService.getProcedure(13);


      Assignment usd = new Assignment(ultrasound, "","");
      Assignment mgn = new Assignment(magnet, "","");
      Assignment lsr = new Assignment(laser, "","");
      assignments = new ArrayList<>();
      assignments.add(usd);
      assignments.add(mgn);
      assignments.add(lsr);
      String dsc ="";
      String pic = "";

      therapies = new ArrayList<>(

              Arrays.asList(

                     new Therapy("5af88504f36336116701205c", assignments,5) ,
                     new Therapy("5af88504f36336116701205c", assignments,5) ,
                     new Therapy("5af88504f36336116701205c", assignments,5)
              )
      );

      therapyRepository.deleteAll();
        therapyRepository.saveAll(therapies);

}


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


 }


*/

}
