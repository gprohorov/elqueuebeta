package com.med.datastorage;

import com.med.model.*;
import com.med.repository.patient.PatientRepository;
import com.med.repository.person.PersonRepository;
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
public class PatientStorage {

    private List<Patient> patients;

    private List<Procedure> schema1;
    private List<Procedure> schema2;
    private List<Procedure> schema3;
    private List<Procedure> schema4;

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
    PersonRepository personRepository;

    @Autowired
    PatientRepository repository;




    @PostConstruct
     void init() {

        List<Person> persons = personRepository.findAll();

       registration = procedureService.getProcedure(1);
        diagnostics = procedureService.getProcedure(2);
        manual = procedureService.getProcedure(3);
        pulling = procedureService.getProcedure(4);
        mechmassasge= procedureService.getProcedure(5);
        massage = procedureService.getProcedure(6);
        ultrasound= procedureService.getProcedure(7);

       schema1 = new ArrayList<>(Arrays.asList(diagnostics));
       schema2 = new ArrayList<>(Arrays.asList(manual,massage));
       schema3 = new ArrayList<>(Arrays.asList(manual,ultrasound,pulling));
       schema4 = new ArrayList<>(Arrays.asList(massage));



        patients = new ArrayList<>(

            Arrays.asList(
                    new Patient(persons.get(11), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(8)
                            , LocalDateTime.now().minusMinutes(112),0, Activity.ACTIVE),
                    new Patient(persons.get(12), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(12)
                            , LocalDateTime.now().minusMinutes(111),0, Activity.ACTIVE)

                    ,  new Patient(persons.get(13), null, schema1
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(2)
                            ,LocalDateTime.now().minusHours(2), 0, Activity.ACTIVE)
                    ,  new Patient(persons.get(14), null, schema3
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(31)
                            , LocalDateTime.now().minusMinutes(222),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(15), null, schema4
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(4)
                            , LocalDateTime.now().minusMinutes(156),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(16), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(46)
                            , LocalDateTime.now().minusMinutes(241),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(17), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(3)
                            , LocalDateTime.now().minusMinutes(218),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(18), null, schema1
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(13)
                            , LocalDateTime.now().minusMinutes(180),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(19), null, schema4
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(61)
                            , LocalDateTime.now().minusMinutes(199),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(20), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(22)
                            , LocalDateTime.now().minusMinutes(211),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(21), null, schema3
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(43)
                            , LocalDateTime.now().minusMinutes(232),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(22), null, schema4
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(23)
                            , LocalDateTime.now().minusMinutes(312),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(23), null, schema4
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(23)
                            , LocalDateTime.now().minusMinutes(195),0, Activity.ACTIVE)
                    ,  new Patient(persons.get(24), null, schema2
                            , Status.SOCIAL, LocalDateTime.now().minusMinutes(22)
                            , LocalDateTime.now().minusMinutes(123),0, Activity.ACTIVE)


            )


    );
      //  repository.deleteAll();
       // repository.saveAll(patients);

}


}
