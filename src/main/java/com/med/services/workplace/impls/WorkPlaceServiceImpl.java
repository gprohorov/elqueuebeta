package com.med.services.workplace.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.card.impls.CardServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import com.med.services.user.UserService;
import com.med.services.workplace.interfaces.IWorkPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    TherapyServiceImpl therapyService;

    private static final Logger logger = LoggerFactory.getLogger(TailServiceImpl.class);

    public Patient getFirstFromTail(int procedureId){

        return tailService.getTail(procedureId).getPatients().stream()
                .findFirst().orElse(null);
    }


    //////////////////////////////START ///////////////////////////////
    public Talon start(String talonId, int doctorId) {

        Talon talon = talonService.getTalon(talonId);

        Patient patient = patientService.getPatientWithTalons(talon.getPatientId());
        System.out.println(" pat act ---  "+patient.calcActivity());
        if (! (patient.calcActivity().equals(Activity.ACTIVE)
            || patient.calcActivity().equals(Activity.INVITED)
                )
            ) {
            return null;
        }

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

      // tail.setPatientOnProcedure(patient);
      // tail.setVacant(false);

      // tailService.setSemaforSignal(talon.getProcedure().getId(), false);
        return talonService.saveTalon(talon);
    }

//////////////////////////////////  EXECUTE //////////////////////////
    public Talon execute(String talonId, int zones, int doctorId) {

        Talon talon = talonService.getTalon(talonId);

      if (talon == null){
          return null;}
        Procedure procedure = talon.getProcedure();
        Tail tail= tailService.getTail(procedure.getId());
        Patient patient = patientService.getPatient(talon.getPatientId());

        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());
        talon.setZones(zones);

        Doctor doctor = doctorService.getDoctor(doctorId);
        String desc = doctor.getFullName() + ", "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                + " - процедуру завершено.<br/><br/>";
        talon.setDesc(talon.getDesc() + desc);
        talon.setStatus(patient.getStatus());
        if(talon.getZones()==0) talon.setZones(1);

        int price = this.getPrice(patient, procedure.getId());

        int sum = procedure.isZoned()? price*talon.getZones(): price;
        talon.setSum(sum);
        talonService.saveTalon(talon);

        patient.setLastActivity(LocalDateTime.now());
       // patient.setBalance(patient.getBalance()-sum);
        patientService.savePatient(patient);

//        tail.setPatientOnProcedure(null);
        this.setBusy(procedure.getId());

        // create accounting
        String descr = procedure.getName() + " x" + talon.getZones();
        Accounting accounting = new Accounting(doctor.getId()
                , patient.getId()
                , LocalDateTime.now()
                , talon.getId()
                , (-1* sum)
                , PaymentType.PROC
                , descr);
        accountingService.createAccounting(accounting);


 /////////////////  cancelling and activating approp. talons
        this.cancelTalonsByCard(procedure,patient.getId());
        this.activateTalonsByCard(procedure,patient.getId());

        return null;
    }


    private int getPrice(Patient patient, int procedureId){
        int price ;
        Procedure procedure = procedureService.getProcedure(procedureId);
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

        // inject into the tail the first active patient and all on procedure if not freechoice
    tails.stream().forEach(tail -> {

        if (!procedureService.getProcedure(tail.getProcedureId()).getFreeChoice()) {

            Patient first = tail.getPatients().stream()
                    .filter(patient -> patient.getActivity().equals(Activity.ACTIVE))
                    .findFirst().orElse(null);

            Patient invited = tail.getPatients().stream()
                    .filter(patient -> patient.getActivity().equals(Activity.INVITED))
                    .findFirst().orElse(null);

            List<Patient> patients = tail.getPatients()
                    .stream().filter(patient -> patient.getActivity().equals(Activity.ON_PROCEDURE))
                    .collect(Collectors.toList());

            // switch semafor
            if (patients.size() < procedureService.getProcedure(tail.getProcedureId()).getNumber()) {
                tail.setVacant(true);
            } else {
                tail.setVacant(false);
            }

            // first and on procedure -> together
            if (invited != null) {
                patients.add(invited);
            }

            // first and on procedure -> together
            if (first != null) {
                patients.add(first);
            }
            tail.setPatients(patients);
            tail.setFreeChoice(false);

        } // of if

        }

    );

        return tails;
    }
////////////////////////////getTailsForDoctor  - the end Hope1234














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
                || tl.getActivity().equals(Activity.INVITED)
                ))
                .findFirst().orElse(null);


        return new TalonPatient(talon,patient);
    }

    public Talon commentTalon(String talonId, String text) {

        Talon talon = talonService.getTalon(talonId);

        String desc = userService.getCurrentUserInfo().getFullName() + ", "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ": <br/>"
                + text + "<br/><br/>";
        talon.setDesc(talon.getDesc() + desc);
        Therapy therapy = therapyService.findTheLastTherapy(talon.getPatientId());
        therapy.setNotes( therapy.getNotes() + desc);
        therapyService.saveTherapy(therapy);

        return talonService.saveTalon(talon);
    }


    public Talon subZone(String talonId) {
        Talon talon = talonService.getTalon(talonId);
        if (talon.getZones() > 1) {
	        talon.setZones(talon.getZones()-1);
	        Doctor doctor = userService.getCurrentUserInfo();
	        talon.setDesc(talon.getDesc() +  doctor.getFullName()
	                + ", " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
	        + " - скасовано зону.<br/><br/>");
        }
        return talonService.saveTalon(talon);
    }
    
    public Talon addZone(String talonId) {
    	Talon talon = talonService.getTalon(talonId);
    	talon.setZones(talon.getZones()+1);
    	Doctor doctor = userService.getCurrentUserInfo();
    	talon.setDesc(talon.getDesc() +  doctor.getFullName()
    	+ ", " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
    	+ " - додано зону.<br/><br/>");
    	return talonService.saveTalon(talon);
    }


///////////////////////////////////////// Logic ////////////////////////////////////////

    //   cancelation after execution : some talons may be canceled when the procedure is done
    // i.e ultrasound must be canceled if laser has been done
    private void cancelTalonsByCard(Procedure procedure, String patientId){
/*
        List<Integer> proceduresToClose = cardService
                .getCardByProcedureId(procedure.getId()).getCloseAfter();
     */
        List<Integer> proceduresToClose = procedure.getCard().getCloseAfter();

        patientService.getPatientWithTalons(patientId).getTalons().stream()
                .filter(talon -> !talon.getActivity().equals(Activity.EXECUTED))
                .forEach(talon -> {
            if (proceduresToClose.contains(Integer.valueOf(talon.getProcedure().getId()))){
                talon.setActivity(Activity.CANCELED);
                logger.info("procedure " + procedure.getName() + " cancelled by card");
                talonService.saveTalon(talon);
            }
        });
    }
    //   activation after execution : some talons may be activated ONLY when the procedure is done
    // i.e. i.e massage after water-pulling. Because of gell.
    private void activateTalonsByCard(Procedure procedure, String patientId){

        List<Integer> proceduresToActivate = procedure.getCard().getActivateAfter();

        List<Talon> talons = patientService
                .getPatientWithTalons(patientId).getTalons().stream()
                .filter(talon -> talon.getActivity().equals(Activity.NON_ACTIVE))
                .collect(Collectors.toList());
/*

        List<Talon> talonsToBeDone = talons.stream()
                .filter(talon-> proceduresMustBeDoneByCard.contains(talon.getProcedure().getId()))
                .collect(Collectors.toList());

        List<Talon> talonsHasBeenDone = talons.stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());
      boolean permission = (talonsHasBeenDone.size() == talonsToBeDone.size()) ? true : false;
*/

        talons.stream().forEach(talon -> {
            if (proceduresToActivate.contains(Integer.valueOf(talon.getProcedure().getId()))
              //     &&permission
                    ){
                talon.setActivity(Activity.ACTIVE);
                talonService.saveTalon(talon);
            }
        });
    }





}
