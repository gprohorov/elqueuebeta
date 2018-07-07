package com.med.services.talon.impls;

import com.med.model.*;
import com.med.repository.talon.TalonRepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.interfaces.ITalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TalonServiceImpl implements ITalonService {


    @Autowired
    TalonRepository repository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    DoctorServiceImpl doctorService;


    @PostConstruct
    void init(){

        System.out.println("--------------------------------");
        System.out.println(this.getFilledProcedures().get(12));
    }



    @Override
    public Talon createTalon(String patientId, int procedureId, int days) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure,days);

        return repository.save(talon);
    }

    public Talon saveTalon(Talon talon){
        return repository.save(talon);
    }

   @Override
    public Talon createTalon(String patientId, int procedureId, LocalDate date) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure, date);

        return repository.save(talon);
    }

    @Override
    public List<Talon> getAll() {
        return repository.findAll();
    }

    @Override
    public Talon getTalon(String id) {

        return repository.findById(id).orElse(null);
    }

    public Talon getTalonByProcedure(String patientId, int procedureId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getProcedure().getId()==procedureId)
                .filter(tal -> tal.getPatientId()==patientId)
                .filter(tal -> tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public List<Talon> findAll() {
        return repository.findAll();
    }

    public Talon getTalonByPatient(String patientId,  Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getPatientId().equals(patientId))
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public Talon getTalonByDoctor(String patientId, int doctorId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getDoctor().getId()==doctorId)
                .filter(tal->tal.getPatientId()==patientId)
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    @Override
    public List<Talon> getTalonsForToday() {
        return this.getAll().stream()
                .filter(talon -> talon.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }



    public List<Talon> getAllTalonsForPatient(String patientId) {
        return this.getAll().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Talon> getTalonsForDate(LocalDate date) {
        return this.getAll().stream()
                .filter(talon -> talon.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public Talon setActivity(String talonId, Activity activity) {
        List<Activity> activities = new ArrayList<>(
            Arrays.asList(
                Activity.EXECUTED,
                Activity.EXPIRED,
                Activity.CANCELED,
                Activity.ON_PROCEDURE
            )
        );
        Talon talon = this.getTalon(talonId);
        if(talon != null && !activities.contains(talon.getActivity())){
            Activity former = talon.getActivity();
            talon.setActivity(activity);
            if (former==Activity.NON_ACTIVE && activity==Activity.ACTIVE) {
                Patient patient = patientService.getPatient(talon.getPatientId());
                LocalDateTime sa = patient.getStartActivity();
                if (sa == null || sa.toLocalDate().isBefore(LocalDate.now())) {
                    patient.setStartActivity(LocalDateTime.now());
                    patient.setLastActivity(LocalDateTime.now());
                }
                patientService.savePatient(patient);
            }
        }
        return repository.save(talon);
    }


    public List<Talon> setAllActivity(String patientId, Activity activity) {
/*

        List<Activity> activities = new ArrayList<>(
                Arrays.asList(Activity.E, Activity.NON_ACTIVE, Activity.TEMPORARY_NA)
        );
*/

        List<Talon> talons = this.getTalonsForToday().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
             //   .filter(talon -> !activities.contains(talon.getActivity()))
                .collect(Collectors.toList());

        talons.stream().forEach(talon -> this.setActivity(talon.getId(), activity));

        return null;
    }

    public List<Patient> toPatientList(List<Talon> talons){
        List<Patient> patients = new ArrayList<>();

        talons.stream().forEach(talon -> patients
                .add(patientService.getPatientWithTalons(talon.getPatientId()))
        );

        return patients.stream().collect(Collectors.toList());
    }

    public List<Talon> saveTalons(List<Talon> talons) {
        return repository.saveAll(talons);
    }


    public List<Talon> getAllExecutedTalonsForPatientFromTo(
            String patientId
            , LocalDate start
            , LocalDate finish){

        return getAll().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
                .filter(talon -> talon.getDate().isAfter(start.minusDays(0)))
                .filter(talon -> talon.getDate().isBefore(finish.plusDays(1)))
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());
    }


    public void deleteAll(List<Talon> talons){
        repository.deleteAll(talons);
    }

    public List<Procedure> getFilledProcedures() {

        List<Procedure> procedures =procedureService.getAll();
        procedures.stream().forEach(procedure -> {

            int nmbr = (int) this.getTalonsForToday().stream()
                    .filter(talon -> talon.getProcedure().equals(procedure)).count();
            procedure.setToday(nmbr);
        });

        return procedures;
    }





}
