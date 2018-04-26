package com.med.services.tail.Impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Tail;
import com.med.services.patient.Impls.PatientServiceIMongoImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.interfaces.ITailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class TailServiceImpl implements ITailService {

   private  List<Tail> tails = new ArrayList<>();

   @Autowired
    ProcedureServiceImpl procedureService;

   @Autowired
   PatientServiceIMongoImpl patientService;



   @PostConstruct
   void init(){
       procedureService.getAll()
               .stream().forEach(procedure
               -> tails.add(new Tail(procedure.getId(), procedure.getName()) ));

     //  this.setPatientsToAllTails();
   }

   // collect all patients from general queue to ONE tail( procedure)
    private List<Patient> getPatientsForOneTailFromCrowd(int procedureId) {

        return   patientService.getActivePatients().stream().filter(
              patient -> patient.getProceduresForToday().stream()
              .anyMatch(procedure ->
                      (procedure.getId()==procedureId)
                      &&
                     !procedure.isExecuted()
              )
      ).collect(Collectors.toList());

    }

    private void setPatientsToAllTails(){

        this.tails.stream().forEach(tail ->
       tail.setPatients(getPatientsForOneTailFromCrowd(tail.getProcedureId()))
       );
    }
////////////////////////////////  tails are filled ///////////////////
    // tails are filled and we want to get all patients for this tail(procedure)
    @Override
    public List<Patient> getPatients(int procedureId) {

        return this.getAll().stream().filter(tail -> tail.getProcedureId()==procedureId)
                .findAny().get().getPatients();
    }
    // similar to the above but filter for Active and On_procedure
    @Override
    public List<Patient> getAllPatientsActiveAndOnProcedure(int procedureId) {
        return this.getPatients(procedureId).stream()
                .filter(patient ->
                   //     patient.getActive().equals(Activity.ON_PROCEDURE)
                   //     ||
                        patient.getActive().equals(Activity.ACTIVE)
                )
                .collect(Collectors.toList());
    }


    @Override
    public Patient getFirstActive(int procedureId) {
        return this.getPatients(procedureId).stream()
                .filter(patient -> patient.getActive().equals(Activity.ACTIVE))
                .findFirst().get();
    }


    @Override
    public Patient getFirstActiveAndOnProcedure(int procedureId) {
        return this.getPatients(procedureId).stream()
                .filter(patient -> patient.getActive().equals(Activity.ACTIVE)
                       ||
                       patient.getActive().equals(Activity.ON_PROCEDURE)
                       )
                .findFirst().orElse(null);
    }

    @Override
    public List<Patient> getPatientsOnProcedure(int procedureId) {
        return this.getPatients(procedureId).stream()
                .filter(patient -> patient.getActive().equals(Activity.ON_PROCEDURE))
                .collect(Collectors.toList());
    }


    @Override
    public List<Tail> getAll() {
        this.setPatientsToAllTails();
        return tails;
    }

}
