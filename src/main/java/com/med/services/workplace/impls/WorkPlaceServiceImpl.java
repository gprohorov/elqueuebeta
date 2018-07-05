package com.med.services.workplace.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.services.accounting.impls.AccountingServiceImpl;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    AccountingServiceImpl accountingService;



    public Patient getFirstFromTail(int procedureId){

        return tailService.getTail(procedureId).getPatients().stream()
                .findFirst().orElse(null);
    }


    //////////////////////////////START ///////////////////////////////
    public Talon start(String talonId, int doctorId) {

        Talon talon = talonService.getTalon(talonId);

        Patient patient = patientService.getPatientWithTalons(talon.getPatientId());

        Tail tail = tailService.getTail(talon.getProcedure().getId()                         );

        Doctor doctor = doctorService.getDoctor(doctorId);

        talon.setActivity(Activity.ON_PROCEDURE);
        talon.setStart(LocalDateTime.now());
        talon.setDoctor(doctor);
        talon.setZones(1);

        String desc = doctor.getFullName() + ", "
        		+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - процедуру розпочато.<br/><br/>";
        talon.setDesc(desc);
       
        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(patient);
        tail.setVacant(false);

        tailService.setSemaforSignal(talon.getProcedure().getId(), false);
        return talonService.saveTalon(talon);
    }

//////////////////////////////////  EXECUTE //////////////////////////
    public Talon execute(String talonId, int doctorId) {

        Talon talon = talonService.getTalon(talonId);

      if (talon == null){
          return null;}
        Procedure procedure = talon.getProcedure();
        Tail tail= tailService.getTail(procedure.getId());
        Patient patient = patientService.getPatient(talon.getPatientId());

        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());

        Doctor doctor = doctorService.getDoctor(doctorId);
        String desc = doctor.getFullName() + ", "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - процедуру завершено.<br/><br/>";
        talon.setDesc(talon.getDesc() + desc);
        talon.setStatus(patient.getStatus());

        int price = this.getPrice(patient, procedure);
        int sum = procedure.isZoned()? price*talon.getZones(): price;
        talon.setSum(sum);


        patient.setLastActivity(LocalDateTime.now());
       // patient.setBalance(patient.getBalance()-sum);
        patientService.savePatient(patient);

        tail.setPatientOnProcedure(null);
        this.setBusy(procedure.getId());

        String descr = procedure.getName() + " x" + talon.getZones();
        Accounting accounting = new Accounting(doctor.getId()
                , patient.getId()
                , LocalDateTime.now()
                , talon.getId()
                , (-1* sum)
                , PaymentType.PROC
                , descr);
        accountingService.createAccounting(accounting);


 /////////////////  0000000000000000000000000


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


    public Talon cancel(String talonId,  String desc) {

        Talon talon = talonService.getTalon(talonId);
        Tail tail= tailService.getTail(talon.getProcedure().getId());

        talon.setActivity(Activity.TEMPORARY_NA);
        talon.setDoctor(null);
        Doctor doctor = userService.getCurrentUserInfo();
        talon.setDesc( doctor.getFullName() + "cancelled "
                + LocalDateTime.now().toString()  +" : " + desc);

        tail.setPatientOnProcedure(null);
        tail.setVacant(true);

        return talonService.saveTalon(talon);
    }

    public void setReady(int procedureId) {
        tailService.setSemaforSignal(procedureId, true);
    }


    public void setBusy(int procedureId) {
        tailService.setSemaforSignal(procedureId, false);
    }

    private List<Talon> hotTalons(int doctorId){

        List<Integer> procedureIds = doctorService.getDoctor(doctorId).getProcedureIds();
        List<Talon> hotTalons = talonService.getTalonsForToday().stream()
                .filter(talon -> talon.getActivity().equals(Activity.ON_PROCEDURE))
                .filter(talon -> procedureIds.contains(talon.getProcedure().getId()))
                .collect(Collectors.toList());

        return hotTalons;
    }

    public List<Patient> hotPatients(int doctorId){

        return this.hotTalons(doctorId).stream().map(talon ->
                    patientService.getPatient(talon.getPatientId()))
                .collect(Collectors.toList());
    }

    public Talon assist(int doctorId, String patientId){



        return null;
    }



    //////////////////////////////
    public List<Tail> getTailsForDoctor(int doctorId) {

        List<Tail> tails = new ArrayList<>();


        List<Integer> procedureIds = userService.getCurrentUserInfo().getProcedureIds();
        tails = tailService.getTails().stream()
                .filter(tail -> procedureIds.contains(tail.getProcedureId()))
                .collect(Collectors.toList());

        // inject into the tail thecfirst active patient and all on procedure
    tails.stream().forEach(tail -> {

        Patient first = tail.getPatients().stream()
                .filter(patient -> patient.getActivity().equals(Activity.ACTIVE))
                .findFirst().orElse(null);

            List<Patient> patients = tail.getPatients()
                        .stream().filter(patient -> patient.getActivity().equals(Activity.ON_PROCEDURE))
                        .collect(Collectors.toList());

            // switch semafor
            if (patients.size()< procedureService.getProcedure(tail.getProcedureId()).getNumber())
            {tail.setVacant(true);}
            else {tail.setVacant(false);}

            // first and on procedure -> together
        if (first != null) {patients.add(first);}

       // clean patients from extra talons
/*
        patients.stream().forEach(patient -> {

           List<Talon>  talons = patient.getTalons()
                    .stream().filter(tl->tl.getProcedure().getId()==tail.getProcedureId())
                    .collect(Collectors.toList());
           patient.setTalons(talons);
        });
*/


        tail.setPatients(patients);

            }

    );

        return tails;
    }
////////////////////////////getTailsForDoctor  - the end

    public Patient getTalonAndPatient(String talonId) {

        Talon talon = talonService.getTalon(talonId);
        Patient patient = patientService.getPatient(talon.getPatientId());
        patient.getTalons().clear();
        patient.getTalons().add(talon);

        return patient;
    }

    public TalonPatient getTalonPatient(String patientId, int procedureId) {

        Patient patient = patientService.getPatient(patientId);

        Talon talon = talonService.getTalonsForToday().stream()
                .filter(tl->tl.getPatientId().equals(patientId))
                .filter(tl->tl.getProcedure().getId()==procedureId)
                .filter(tl->(tl.getActivity().equals(Activity.ACTIVE)
                || tl.getActivity().equals(Activity.ON_PROCEDURE)
                ))
                .findFirst().orElse(null);


        return new TalonPatient(talon,patient);
    }

    public Talon commentTalon(String talonId, String text) {

        Talon talon = talonService.getTalon(talonId);

        String desc = talon.getDesc()
                + userService.getCurrentUserInfo().getFullName() + ", "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ": <br/>"
                + text + "<br/><br/>";
        talon.setDesc(desc);

        return talonService.saveTalon(talon);
    }


    public Talon addZoneTalon(String talonId) {
        Talon talon = talonService.getTalon(talonId);
        talon.setZones(talon.getZones()+1);
        Doctor doctor = userService.getCurrentUserInfo();
        talon.setDesc(talon.getDesc() +  doctor.getFullName()
                + ", " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
        + " - добавлено зону.<br/><br/>");
        return talonService.saveTalon(talon);
    }
}
