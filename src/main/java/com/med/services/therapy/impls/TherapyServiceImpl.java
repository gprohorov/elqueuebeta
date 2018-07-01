package com.med.services.therapy.impls;

import com.med.model.*;
import com.med.repository.therapy.TherapyRepository;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.interfaces.ITherapyService;
import com.med.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TherapyServiceImpl implements ITherapyService {

    private List<Therapy> therapys = new ArrayList<>();

    @Autowired
    TherapyRepository repository;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    UserService userService;

    public Therapy createTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy saveTherapy(Therapy therapy) {return repository.save(therapy);
    }

    public Therapy updateTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy getTherapy(String id) {
        return repository.findById(id).orElse(null);
    }

    public Therapy deleteTherapy(String id) {
        return null;
    }

    public List<Therapy> getAll() {
        return repository.findAll();
    }


    public Therapy finishTherapy(String therapyId) {
       Therapy therapy = this.getTherapy(therapyId);
       therapy.setFinish(LocalDateTime.now());

       return this.saveTherapy(therapy);
    }


    ///////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    // TODO:  Human way
    // DONE!
    public Therapy findTheLastTherapy(String patientId){
     return repository.findByPatientId(patientId).stream()
                .sorted(Comparator.comparing(Therapy::getStart).reversed())
                .findFirst().orElse(null);
    }
    /////////////////////   Get Patient Talon Therapy for Diagnostics procedure
    public PatientTalonTherapy getPatientTalonTherapy(String patientId) {
        Talon talon = talonService.findByActivity(Activity.ACTIVE)
                .stream().filter(tal->tal.getPatientId().equals(patientId))
                .filter(tal->tal.getProcedure().getProcedureType().equals(ProcedureType.DIAGNOSTIC))
                .findFirst().orElse(null);
        Patient patient = patientService.getPatient(patientId);
        Therapy therapy = this.findTheLastTherapy(patientId);

        return new PatientTalonTherapy(patient, talon, therapy);
    }



    public void startProcedure(String talonId) {

        Talon talon = talonService.getTalon(talonId);
        Patient patient = patientService.getPatientWithTalons(talon.getPatientId());

        Tail tail = tailService.getTail(talon.getProcedure().getId()                         );

        Doctor doctor = userService.getCurrentUserInfo();

        talon.setActivity(Activity.ON_PROCEDURE);
        talon.setStart(LocalDateTime.now());
        talon.setDoctor(doctor);

        String desc = doctor.getFullName() + ", "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - дiaгностику  розпочато.<br/><br/>";
        talon.setDesc(desc);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(patient);
        tail.setVacant(false);

        tailService.setSemaforSignal(talon.getProcedure().getId(), false);
        talonService.saveTalon(talon);



    }

    public void cancelProcedure(String talonId) {

        Talon talon = talonService.getTalon(talonId);
        Tail tail= tailService.getTail(talon.getProcedure().getId());
        talon.setActivity(Activity.TEMPORARY_NA);
        Doctor doctor = userService.getCurrentUserInfo();
        talon.setDesc( talon.getDesc() + "/n" + doctor.getFullName() + "cancelled "
                + LocalDateTime.now().toString()  );

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);

        talonService.saveTalon(talon);
    }




    public void executeProcedure(String talonId, Therapy therapy) {

        repository.save(therapy);

        Talon talon = talonService.getTalon(talonId);

        if (talon == null){return; }
        Procedure procedure = talon.getProcedure();
        Tail tail= tailService.getTail(procedure.getId());
        Patient patient = patientService.getPatient(talon.getPatientId());

        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());

        String desc =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - процедуру завершено.<br/><br/>";
        talon.setDesc(desc);
        talon.setStatus(patient.getStatus());
        talon.setZones(0);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);
        
       talonService.saveTalon(talon);


    }

    public List<Talon> createTalonsForAllDays(Therapy therapy){

        int days = therapy.getDays();
        List<Talon> talons = new ArrayList<>();
        List<Procedure> procedures= therapy.getAssignments().stream()
                .map(Assignment::getProcedure).collect(Collectors.toList());

        for(int i =0; i<days; i++){
            for (Procedure procedure:procedures) {

                talons.add(new Talon(therapy.getPatientId()
                        , procedure, LocalDate.now().plusDays(i)));
            }
        }

        return talonService.saveTalons(talons);
    }

    public List<Talon> createTalonsForToday(Therapy therapy){
        List<Procedure> procedures= therapy.getAssignments().stream()
                .map(Assignment::getProcedure).collect(Collectors.toList());
        List<Talon> talons = new ArrayList<>();
        procedures.stream().forEach(procedure -> {
            talons.add(new Talon(therapy.getPatientId()
                    , procedure, LocalDate.now()));
        });
        return talonService.saveTalons(talons);
    }




    }
