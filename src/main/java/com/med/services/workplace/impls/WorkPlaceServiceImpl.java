package com.med.services.workplace.impls;

import com.med.model.*;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.user.UserService;
import com.med.services.workplace.interfaces.IWorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 17.05.18.
 */
@Service
public class WorkPlaceServiceImpl implements IWorkPlaceService {

    private List<Workplace> workplaces = new ArrayList<>();

    @Autowired
    TailServiceImpl tailService;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    UserService userService;


    public Talon start(String patientId, int procedureId) {

        Talon talon = talonService.getTalon(patientId, procedureId, Activity.ACTIVE);
        Tail tail= tailService.getTail(procedureId);
        Patient patient = patientService.getPatient(patientId);
        Doctor doctor = userService.getCurrentUserInfo();


        talon.setActivity(Activity.ON_PROCEDURE);
        talon.setStart(LocalDateTime.now());
        talon.setDoctor(doctor);
        talonService.saveTalon(talon);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(patient);
        tail.setVacant(false);


        return talon;
    }






    public Talon execute(String patientId, int procedureId, String desc) {

        Talon talon = talonService.getTalon(patientId, procedureId, Activity.ON_PROCEDURE);
        Tail tail= tailService.getTail(procedureId);
        Patient patient = patientService.getPatient(patientId);
        Doctor doctor = userService.getCurrentUserInfo();


        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());
        talon.setDesc(desc);
        talonService.saveTalon(talon);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);


        return talon;
    }




}
