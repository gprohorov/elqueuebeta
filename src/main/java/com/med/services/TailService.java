package com.med.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Tail;
import com.med.model.Talon;

@Service
public class TailService {

    private HashMap<Integer, Boolean> semafor = new HashMap<Integer, Boolean>();

    @Autowired
    ProcedureService procedureService;

    @Autowired
    PatientService patientService;

    @Autowired
    TalonService talonService;

    @Autowired
    UserService userService;

    @PostConstruct
    void init() {
        procedureService.getAll().stream().forEach(pr -> semafor.put(pr.getId(), false));
    }

    public void refreshProcedures() {
        procedureService.getAll().stream().forEach(pr -> semafor.put(pr.getId(), false));

    }
    public HashMap<Integer, Boolean> setAllSemafors(List<Talon> talonsForToday) {
	    semafor.entrySet().stream().forEach(entry-> {
	    	entry.setValue(talonsForToday.stream()
				.filter(talon -> talon.getProcedure().getId() == entry.getKey())
				.filter(talon -> talon.getActivity().equals(Activity.ON_PROCEDURE))
				.count() < procedureService.getProcedure(entry.getKey()).getNumber()
			);
		});
	    return semafor;
    }

    public void setSemaforSignal(int procedureId, boolean signal) {
        this.semafor.put(procedureId, signal);
    }

    public boolean getSemaforSignal(int procedureId) {
        return this.semafor.get(procedureId);
    }

    public Tail getTail(int procedureId) {
        List<Tail> tails = this.getTails();
        return (tails == null) ? null 
        		: tails.stream().filter(tail -> tail.getProcedureId() == procedureId).findFirst().orElse(null);
    }

    // Form the procedures queues with patients, who are active and on this procedure
    public List<Tail> getTails() {

        List<Talon> talonsForToday = talonService.getTalonsForToday();

        List<Tail> tails = talonsForToday.stream().filter(talon ->
            talon.getActivity().equals(Activity.ACTIVE)
         || talon.getActivity().equals(Activity.ON_PROCEDURE)
         || talon.getActivity().equals(Activity.INVITED) )
            .collect(Collectors.groupingBy(Talon::getProcedure)).entrySet().stream()
            .filter(tail -> tail.getKey() != null)
                .map(tail -> new Tail(
                tail.getKey().getId(),
                tail.getKey().getName(),
                tail.getKey().getProcedureType(),
                talonService.toPatientList(tail.getValue()).stream()
                	.filter(patient -> patient.getDelta() != null)
                    .sorted(
                        ( Comparator.comparing(Patient::getActivityLevel)
                              .thenComparing(Patient::getStatusLevel)
                              .thenComparing(Patient::getDelta) ).reversed()
                ).collect(Collectors.toList()),
                getSemaforSignal(tail.getKey().getId())
            ) ).collect(Collectors.toList());

        this.setAllSemafors(talonsForToday);
        
        ///////////////////// extract ON_PROCEDURE patients from another tails
        List<Talon> talonsOnProcedure = talonsForToday.stream()
            .filter(talon -> (talon.getActivity().equals(Activity.ON_PROCEDURE)
            		|| talon.getActivity().equals(Activity.INVITED) ) )
            .collect(Collectors.toList());

        talonsOnProcedure.stream().forEach(talon -> {
            Patient patient = patientService.getPatient(talon.getPatientId());
            tails.stream().forEach(tail -> {
                if (tail.getProcedureId() != talon.getProcedure().getId()
            		&& tail.getPatients().contains(patient)) {
                    tail.getPatients().remove(patient);
                }
            });
        });
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
            if (talon.getDoctor().getId() == userService.getCurrentUserInfo().getId()) return patient;
        }
        if (tail.isVacant()) return patient;
        return null;
    }
}