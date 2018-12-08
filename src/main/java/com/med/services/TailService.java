package com.med.services;

import com.med.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Service
public class TailService {

    private HashMap<Integer, Boolean> semafor = new HashMap();

    @Autowired
    ProcedureService procedureService;

    @Autowired
    PatientService patientService;

    @Autowired
    TalonService talonService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TailService.class);

    @PostConstruct
    void init() {
        procedureService.getAll().stream().forEach(pr -> semafor.put(pr.getId(), false));
        // this.setSemaforSignal(1, true);
    }

    public HashMap<Integer, Boolean> setAllSemafors(List<Talon> talonsForToday) {
	    semafor.entrySet().stream().forEach(entry-> {
	    	if (talonsForToday.stream()
	             .filter(talon -> talon.getProcedure().getId() == entry.getKey())
	             .filter(talon -> talon.getActivity().equals(Activity.ON_PROCEDURE))
	             .count() <  procedureService.getProcedure(entry.getKey()).getNumber()
	            ) {
	    		entry.setValue(true);
	    	} else {
	    		entry.setValue(false) ;
	       }
		});
	
	    return semafor;
    }

    public void setSemaforSignal(int procedureId, boolean signal) {
        this.semafor.put(procedureId, signal);
    }

    public void setSemaforSignal(int doctorId, int procedureId, boolean sign) {}

    public boolean getSemaforSignal(int procedureId) {
        return this.semafor.get(procedureId);
    }

    public Tail getTail(int procedureId) {
        List<Tail> tails = this.getTails();
        if (tails != null) {
            return tails.stream().filter(tail -> tail.getProcedureId() == procedureId)
                  .findFirst().orElse(null);
        }
        return null;
    }

    /*
    * Form the procedures queues with patients, who are active and on this procedure
    */
    public List<Tail> getTails() {

    	long start = System.currentTimeMillis();
    	
        List<Talon> talonsForToday = talonService.getTalonsForToday();

    //    logger.info(">>>>  talons for today   >>>>>>>> " + (System.currentTimeMillis() - start));

        List<Tail> tails = talonsForToday.stream().filter(talon ->
                talon.getActivity().equals(Activity.ACTIVE)
                ||
                talon.getActivity().equals(Activity.ON_PROCEDURE)
                ||
                talon.getActivity().equals(Activity.INVITED)
            )
            .collect(Collectors.groupingBy(Talon::getProcedure)).entrySet().stream()
            .filter(tail -> tail.getKey()!=null)
                .map(tail -> new Tail(
                tail.getKey().getId(),
                tail.getKey().getName(),
                tail.getKey().getProcedureType(),
                talonService.toPatientList(tail.getValue()).stream()
     /*kostil*/         .filter(patient -> patient.getDelta()!=null)
                 //   .map(patient -> patient.setTherapy(null))
//                        .filter(patient ->
//                    patient.getActivity().equals(Activity.ACTIVE)
//                    ||
//                    patient.getTalons().stream().filter(talonTest ->
//                        talonTest.getProcedure().getId() == tail.getKey().getId()
//                        &&
//                        talonTest.getActivity().equals(Activity.ON_PROCEDURE)
//                    ).findFirst().isPresent()
//                )
                        .sorted(
                        ( Comparator.comparing(Patient::getActivityLevel)
                              .thenComparing(Patient::getStatusLevel)
                              .thenComparing(Patient::getDelta) ).reversed()
                ).collect(Collectors.toList()),
                getSemaforSignal(tail.getKey().getId())
            ) ).collect(Collectors.toList());

     //   logger.info(">>>>  grouping >>>>>>>> " + (System.currentTimeMillis() - start));
/*
        *//* another way of grouping*//*
        procedureService.getAll().stream().forEach(procedure -> {

            logger.info("-------- " + procedure.getId() + " ------"  );


            long startGrouping = System.currentTimeMillis();
            Tail tail = new Tail();
            tail.setProcedureId(procedure.getId());
            tail.setProcedureName(procedure.getName());
            tail.setProcedureType(procedure.getProcedureType());
            List<Talon> talons = talonsForToday.stream()
                    .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                    .filter(talon ->
                            talon.getActivity().equals(Activity.ACTIVE)
                                    ||
                                    talon.getActivity().equals(Activity.ON_PROCEDURE))
                    .collect(Collectors.toList());

            logger.info(">>>>  grouping  >>>>>>>> "  + ((System.currentTimeMillis() - startGrouping)));

            long startSorting = System.currentTimeMillis();
            List<Patient> patients = talonService.toPatientList(talons).stream()
                    .sorted(
                            ( Comparator.comparing(Patient::getActivityLevel)
                                    .thenComparing(Patient::getStatusLevel)
                                    .thenComparing(Patient::getDelta) ).reversed()
                    ).collect(Collectors.toList());
            tail.setPatients(patients);

            logger.info(">>>>  sorting   >>>>>>>> "  +  ((System.currentTimeMillis() - startSorting)));


        });
          *//**//*
          *
          *

 */
        this.setAllSemafors(talonsForToday);

   //   Hope1234  logger.info(">>>>  semafores >>>>>>>>>> " + (System.currentTimeMillis() - start));


        ///////////////////// extract ON_PROCEDURE patients from another tails
        List<Talon> talonsOnProcedure = talonsForToday.stream()
                .filter(talon ->(
                                talon.getActivity().equals(Activity.ON_PROCEDURE)
                ||
                                talon.getActivity().equals(Activity.INVITED)
                                )
                )
                .collect(Collectors.toList());

        talonsOnProcedure.stream().forEach(talon -> {
            Patient patient = patientService.getPatient(talon.getPatientId());
            tails.stream().forEach(tail -> {
                if (tail.getProcedureId() != talon.getProcedure().getId()
                        &&
                     tail.getPatients().contains(patient)   ){

                    tail.getPatients().remove(patient);
                }

            });

        });

        logger.info(">>>>  tails created >>>>> " + (System.currentTimeMillis() - start));

        return tails;
    }

    public Patient getFirstPatient(int procedureId) {
        Tail tail = this.getTail(procedureId);
        if (tail == null) return null;

        Patient patient = tail.getPatients().stream().findFirst().orElse(null);
        if (patient == null) return null;

        if (patient.getActivity().equals(Activity.ON_PROCEDURE)) {
            Talon talon = patient.getTalons().stream()
                .filter(t -> t.getActivity().equals(Activity.ON_PROCEDURE))
                .findFirst().orElse(null);
            if (talon == null) return null;
            if (talon.getDoctor().getId() == userService.getCurrentUserInfo().getId()) {
                return patient;
            }
        }
        if (tail.isVacant()) {
            return patient;
        }
        return null;
    }

    // TODO: Move this to models
private class ProcedurePatient{

    private Procedure procedure;
    private String patientId;

    public ProcedurePatient() {
    }

    public ProcedurePatient(Procedure procedure, String patientId) {
        this.procedure = procedure;
        this.patientId = patientId;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}


}


