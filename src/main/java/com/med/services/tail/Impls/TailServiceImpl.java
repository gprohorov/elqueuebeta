package com.med.services.tail.Impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.interfaces.ITailService;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.user.UserService;
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
public class TailServiceImpl implements ITailService {

    private HashMap<Integer, Boolean> semafor = new HashMap();

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TailServiceImpl.class);


    @PostConstruct
    void init() {

        procedureService.getAll().stream().forEach(pr -> semafor.put(pr.getId(), false));
        //this.setSemaforSignal(1,true);
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

    public void setSemaforSignal(int doctorId, int procedureId, boolean sign) {

    }

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

        logger.info(">>>>  talons for today   >>>>>>>> " + (System.currentTimeMillis() - start));

        List<Tail> tails = talonsForToday.stream().filter(talon ->
                talon.getActivity().equals(Activity.ACTIVE)
                ||
                talon.getActivity().equals(Activity.ON_PROCEDURE)
            )
            .collect(Collectors.groupingBy(Talon::getProcedure)).entrySet().stream()
            .map(tail -> new Tail(
                tail.getKey().getId(),
                tail.getKey().getName(),
                tail.getKey().getProcedureType(),
                talonService.toPatientList(tail.getValue()).stream()
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

      //  Patient patient =
        
        this.setAllSemafors(talonsForToday);

        logger.info(">>>>  tails for all procedures >>>>>>>> " + (System.currentTimeMillis() - start));

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


}
