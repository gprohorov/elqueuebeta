package com.med.services.workplace.impls;

import com.med.model.*;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
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

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    DoctorServiceImpl doctorService;



    public Patient getFirstFromTail(int procedureId){

        return tailService.getTail(procedureId).getPatients().stream()
                .findFirst().orElse(null);
    }

    public Talon start(String patientId, int procedureId, int doctorId) {

        Patient patient = patientService.getPatientWithTalons(patientId);

       Talon talon = patient.getTalons().stream()
                .filter(tal->tal.getProcedure().getId()==procedureId)
                .filter(tal->tal.getActivity().equals(Activity.ACTIVE))
                .findFirst().orElse(null);

        Tail tail = tailService.getTail(procedureId);

        Doctor doctor = doctorService.getDoctor(doctorId);

        talon.setActivity(Activity.ON_PROCEDURE);
        talon.setStart(LocalDateTime.now());
        talon.setDoctor(doctor);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(patient);
        tail.setVacant(false);

        tailService.setSemaforSignal(procedureId, false);
        return talonService.saveTalon(talon);
    }


    public Talon execute(String patientId, String desc) {

        Talon talon = talonService.getTalonByPatient(patientId, Activity.ON_PROCEDURE);
        Procedure procedure = talon.getProcedure();
        Tail tail= tailService.getTail(procedure.getId());
        Patient patient = patientService.getPatient(patientId);

        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());
        talon.setDesc(talon.getDate() + desc);
        int price = this.getPrice(patient, procedure);
        int sum= -1 * ( procedure.isZoned()? price*talon.getZones(): price);
        talon.setSum(sum);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);

        return talonService.saveTalon(talon);
    }


    int getPrice(Patient patient, Procedure procedure){
        int price ;
        switch (patient.getStatus()){

            case SOCIAL: price= procedure.getSOCIAL();
            break;

            case VIP: price= procedure.getVIP();
            break;

            case ALL_INCLUSIVE: price= procedure.getALL_INCLUSIVE();
            break;

            case BUSINESS: price= procedure.getBUSINESS();
            break;

            case FOREIGN:    price=procedure.getFOREIGN();
            break;

            default:price=procedure.getSOCIAL();
            break;
        }
    return price;
    }


    public Talon cancel(String patientId,  String desc) {

        Talon talon = talonService.getTalonByPatient(patientId , Activity.ON_PROCEDURE);
        Tail tail= tailService.getTail(talon.getProcedure().getId());

        talon.setActivity(Activity.TEMPORARY_NA);
        talon.setDoctor(null);
        talon.setDesc(talon.getDate() + desc);

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);

        return talonService.saveTalon(talon);
    }

    public boolean switchSemafor(int procedureId, boolean signal){

        tailService.setSemaforSignal(procedureId, signal);
        return true;
    }


}
