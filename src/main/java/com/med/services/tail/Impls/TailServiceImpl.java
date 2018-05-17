package com.med.services.tail.Impls;

import com.med.model.Activity;
import com.med.model.Procedure;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.interfaces.ITailService;
import com.med.services.talon.impls.TalonServiceImpl;
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
   PatientServiceImpl patientService;

   @Autowired
   TalonServiceImpl talonService;


   @PostConstruct
   void init() {
      procedureService.getAll()
               .stream().forEach(procedure
               -> tails.add(new Tail(procedure.getId(), procedure.getName()) ));

     //  this.setPatientsToAllTails();

    //  System.out.println("==================================================");
      // System.out.println(talonService.getTalonsForToday().size());
    //  System.out.println(this.getAllPatientsToTailByTalons(2).size());
   }

   public Tail getTail(Procedure procedure){

      return tails.stream()
              .filter(tail -> tail.getProcedureId()==procedure.getId())
              .findFirst().orElse(null);
   }


   public Tail getTail(int procedureId){

      return tails.stream()
              .filter(tail -> tail.getProcedureId()==procedureId)
              .findFirst().orElse(null);
   }


   public Tail getTail(String procedureName){

      return tails.stream()
              .filter(tail -> tail.getProcedureName().equals(patientService))
              .findFirst().orElse(null);
   }



   public List<Tail> getTails(){
      talonService.getTalonsForToday().stream()
              .filter(talon -> talon.getActivity().equals(Activity.ACTIVE))
              .collect(Collectors.groupingBy(Talon::getProcedure))
              .entrySet().stream()
              .forEach(entry-> {
                 Tail tail = this.getTail(entry.getKey());

                 tail.setPatients(talonService.toPatientList(entry.getValue()));                ;
              }
              );
     return  this.tails;

           //   .collect(Collectors.groupingBy(Talon::getProcedure));
   }



/*
   // collect all patients from general queue to ONE tail( procedure)
    private List<Patient> getPatientsForOneTailFromCrowd(int procedureId) {
              Tail tail = tails.stream()
                      .filter(tail1 -> tail1.getProcedureId()==procedureId)
                      .findAny().get();
              Patient pat = tail.getPatientOnProcedure();
              List<Patient> pats = patientService.getActivePatients()
                      .stream().filter(
              patient -> patient.getProceduresForToday().stream()
              .anyMatch(procedure ->
                      (procedure.getId()==procedureId)
                      &&
                     !procedure.isExecuted()
              )
      ).collect(Collectors.toList());

              if (pat !=null){
                  pat.setDelta();
                  pats.add(pat);}


        return pats.stream().sorted().collect(Collectors.toList());
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
       // this.setPatientsToAllTails();
        this.fillAllTailsByPatients();
        return tails;
    }

    @Override
    public Tail getTail(int procedureId) {
        return tails.stream().filter(tail -> tail.getProcedureId()==procedureId)
                .findFirst().orElse(null);
    }

    @Override
    public Tail getTailByProcedure(int procedureId) {
        return  tails.stream()
                .filter(tail1 -> tail1.getProcedureId()==procedureId)
                .findAny().get();
    }


    ////////////////////////BY TALONS /////////////////////////////////////////////////



    List<Patient> getAllPatientsToTailByTalons(int procedureId){

        Procedure procedure = procedureService.getProcedure(procedureId);

        List<Talon> talons = talonService.getTalonsForToday().stream()
                .filter(talon -> talon.getProcedure().equals(procedure))
                .filter(talon -> talon.getExecutionTime() == null)
                .collect(Collectors.toList());

        List<Patient> patients = new ArrayList<>();
        talons.stream().forEach(talon
                -> patients.add(patientService.getPatient(talon.getPatientId())));

        return patients.stream().filter(p->p.getActive().equals(Activity.ACTIVE))
                .sorted().collect(Collectors.toList());
    }


    Tail loadPatientstoTail(int procedureId){

        Tail tail = this.getTailByProcedure(procedureId);
        tail.getPatients().clear();
        tail.setPatients(this.getAllPatientsToTailByTalons(procedureId));

        return tail;

    }

    List<Tail> fillAllTailsByPatients(){
        this.tails.stream().forEach(tail
                -> loadPatientstoTail(tail.getProcedureId()));

        return tails;
    }

    List<Patient> getPatientsInTail(int procedureId){
        return this.getTail(procedureId).getPatients();
    }


    public Patient getFirstForProcedure(int procedureId){
        Tail tail = this.getTail(procedureId);

         if(tail.getPatientOnProcedure()!=null){
             return tail.getPatientOnProcedure();
         }

        Patient first = this.getPatientsInTail(procedureId).stream()
                .findFirst().orElse(null);
        tail.setPatientOnProcedure(first);
        tail.setVacant(false);
       // if(tail.getPatients().size()!=0){tail.getPatients().remove(0);}
        return first;
    }
*/

}
