package com.med.services.tail.Impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.interfaces.ITailService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class TailServiceImpl implements ITailService {


   private List<Patient> empty = new ArrayList<>();

   @Autowired
    ProcedureServiceImpl procedureService;

   @Autowired
   PatientServiceImpl patientService;

   @Autowired
   TalonServiceImpl talonService;

   public Tail getTail(int procedureId){
      return this.getTails().stream()
              .filter(tail -> tail.getProcedureId()==procedureId)
              .findFirst().orElse(null);
   }

   public List<Tail> getTails() {

    return talonService.getTalonsForToday().stream()
      .filter(talon ->
              talon.getActivity().equals(Activity.ACTIVE)
              ||
              talon.getActivity().equals(Activity.ON_PROCEDURE)
              )
      .collect(Collectors.groupingBy(Talon::getProcedure)).entrySet().stream()
      .map(x -> new Tail(x.getKey().getId(), x.getKey().getName(),
              talonService.toPatientList(x.getValue()).stream()
              .filter(patient -> patient.getActivity().equals(Activity.ACTIVE))
               .collect(Collectors.toList())
              )
      ).collect(Collectors.toList());
   }

   public Patient getFirstPatient(int procedureId) {
       return this.getTail(procedureId).getPatients().stream()
               .findFirst().orElse(null);
   }

/*

   void temp() {
       procedureService.getAll()
               .stream().forEach(procedure
               -> tails.add(new Tail(procedure.getId(), procedure.getName()) ));
       talonService.getTalonsForToday().stream()
               .filter(talon -> talon.getActivity().equals(Activity.ACTIVE))
               .collect(Collectors.groupingBy(Talon::getProcedure))
               .entrySet().stream()
               .forEach(entry-> {
                   Tail tail = this.getTail(entry.getKey());
                   tail.setPatients(talonService.toPatientList(entry.getValue()).stream()
                           .filter(patient -> patient.getActivity().equals(Activity.ACTIVE))
                           .collect(Collectors.toList()));                ;
               });
       return tails;
   }

   @PostConstruct
   void init() {
     this.initTails();

   }


   private void initTails(){
       procedureService.getAll()
               .stream().forEach(procedure
               -> tails.add(new Tail(procedure.getId(), procedure.getName()) ));
   }

   private void resetTails(){
       tails.stream().forEach(tail -> tail.setPatients(empty));
   }



   public Tail getTail(Procedure procedure){

      return tails.stream()
              .filter(tail -> tail.getProcedureId()==procedure.getId())
              .findFirst().orElse(null);
   }


   public Tail getTail(String procedureName){

      return tails.stream()
              .filter(tail -> tail.getProcedureName().equals(patientService))
              .findFirst().orElse(null);
   }



*/

}
