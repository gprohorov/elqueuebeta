package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Accrual;
import com.med.model.Activity;
import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.model.TalonPatient;
import com.med.model.Therapy;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;

@Service
public class WorkPlaceService {

    @Autowired
    TailService tailService;

    @Autowired
    TalonService talonService;

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    AccountingService accountingService;

    @Autowired
    TherapyService therapyService;

    @Autowired
    AccrualService accrualService;

    public Patient getFirstFromTail(int procedureId) {
        return tailService.getTail(procedureId).getPatients().stream().findFirst().orElse(null);
    }

    ////////////////////////////// START ///////////////////////////////
    public Talon start(String talonId, int doctorId) {

        Talon talon = talonService.getTalon(talonId);

        Patient patient = patientService.getPatientWithTalons(talon.getPatientId());
        if (! (patient.calcActivity().equals(Activity.ACTIVE)
            || patient.calcActivity().equals(Activity.INVITED))) return null;

        Doctor doctor = doctorService.getDoctor(doctorId);

        talon.setActivity(Activity.ON_PROCEDURE);
        talon.setStart(LocalDateTime.now());

        talon.setDoctor(doctor);
        talon.setZones(1);

        talon.setDesc(doctor.getFullName() + ", "
    		+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            + " - процедуру розпочато.<br/><br/>");
       
        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);

        return talonService.saveTalon(talon);
    }

    ////////////////////////////////// EXECUTE //////////////////////////
    public Talon execute(String talonId, int zones, int doctorId) {

        Talon talon = talonService.getTalon(talonId);
        if (talon == null) return null;
        
        Procedure procedure = talon.getProcedure();
        Patient patient = patientService.getPatient(talon.getPatientId());

        talon.setActivity(Activity.EXECUTED);
        talon.setExecutionTime(LocalDateTime.now());
        talon.setZones(zones);

        Doctor doctor = doctorService.getDoctor(doctorId);
        talon.setDesc(talon.getDesc() + doctor.getFullName() + ", "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            + " - процедуру завершено.<br/><br/>");
        talon.setStatus(patient.getStatus());
        if (talon.getZones() == 0) talon.setZones(1);

        int price = this.getPrice(patient, procedure.getId());
        int sum = procedure.isZoned() ? price * talon.getZones(): price;
        talon.setSum(sum);
        talonService.saveTalon(talon);

        patient.setLastActivity(LocalDateTime.now());
        patientService.savePatient(patient);
        
        this.setBusy(procedure.getId());

        // create accounting
        String descr = procedure.getName() + " x" + talon.getZones();
        Accounting accounting = new Accounting(doctor.getId()
                , patient.getId()
                , LocalDateTime.now()
                , talon.getId()
                , (-1 * sum)
                , PaymentType.PROC
                , descr);
        accountingService.createAccounting(accounting);

        Accrual accrual = new Accrual();
        accrual.setDateTime(LocalDateTime.now());
        accrual.setTalonId(talon.getId());
        accrual.setDoctorId(talon.getDoctor().getId());
        accrual.setDesc(descr);
        accrual.setSum(talon.getProcedure().getSOCIAL() / 10);
        accrualService.createAccrual(accrual);

        /////////////////  cancelling and activating approp. talons
        this.cancelTalonsByCard(procedure,patient.getId());
        this.activateTalonsByCard(procedure,patient.getId());

        // TODO: Remove hardcoded value!!!
        ////////////////////// if YXT   -  comment to therapy
        if (procedure.getId() == 9) {
            Therapy therapy = therapyService.findTheLastTherapy(talon.getPatientId());
            therapy.setNotes(
        		therapy.getNotes() + "<br>" + " : YXT " + LocalDate.now() + " " + zones + "zn.");
            therapyService.saveTherapy(therapy);
        }

        return null;
    }

    private int getPrice(Patient patient, int procedureId) {
        Procedure procedure = procedureService.getProcedure(procedureId);
        switch (patient.getStatus()) {
            case SOCIAL: return procedure.getSOCIAL();
            case VIP: return procedure.getVIP();
            case ALL_INCLUSIVE: return procedure.getALL_INCLUSIVE();
            case BUSINESS: return procedure.getBUSINESS();
            case FOREIGN: return procedure.getFOREIGN();
            default: return procedure.getSOCIAL();
        }
    }

    public Talon cancel(String talonId, String desc) {
        Talon talon = talonService.getTalon(talonId);
        Tail tail = tailService.getTail(talon.getProcedure().getId());

        talon.setActivity(Activity.TEMPORARY_NA);
        talon.setExecutionTime(LocalDateTime.now());
        talon.setDesc(userService.getCurrentUserInfo().getFullName() + "cancelled "
        		+ LocalDateTime.now().toString() + " : " + desc);

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

    public List<Tail> getTailsForDoctor(int doctorId) {

        List<Tail> tails = tailService.getTails().stream().filter(
    		tail -> userService.getCurrentUserInfo().getProcedureIds().contains(tail.getProcedureId()))
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
	
	            List<Patient> patients = tail.getPatients().stream()
            		.filter(patient -> patient.getActivity().equals(Activity.ON_PROCEDURE))
                    .collect(Collectors.toList());
	
	            // switch semafor
	            tail.setVacant((patients.size() < procedureService.getProcedure(
            		tail.getProcedureId()).getNumber()));
	
	            // first and on procedure -> together
	            if (invited != null) patients.add(invited);
	
	            // first and on procedure -> together
	            if (first != null) patients.add(first);
	            
	            tail.setPatients(patients);
	            tail.setFreeChoice(false);
	        }
        });
        return tails;
    }

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
	        .filter(tl -> tl.getPatientId().equals(patientId))
	        .filter(tl -> tl.getProcedure().getId() == procedureId)
	        .filter(tl -> (tl.getActivity().equals(Activity.ACTIVE)
	        || tl.getActivity().equals(Activity.ON_PROCEDURE)
	        || tl.getActivity().equals(Activity.INVITED) )).findFirst().orElse(null);

        return new TalonPatient(talon, patient);
    }

    public Talon commentTalon(String talonId, String text) {
        Talon talon = talonService.getTalon(talonId);
        String desc = userService.getCurrentUserInfo().getFullName() + ", "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            + ": <br/>" + text + "<br/><br/>";
        talon.setDesc(talon.getDesc() + desc);
        Therapy therapy = therapyService.findTheLastTherapy(talon.getPatientId());
        therapy.setNotes(therapy.getNotes() + desc);
        therapyService.saveTherapy(therapy);
        return talonService.saveTalon(talon);
    }

    public Talon subZone(String talonId) {
        Talon talon = talonService.getTalon(talonId);
        if (talon.getZones() > 1) {
	        talon.setZones(talon.getZones() - 1);
	        talon.setDesc(talon.getDesc() + userService.getCurrentUserInfo().getFullName() + ", "
        		+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
        		+ " - скасовано зону.<br/><br/>");
        }
        return talonService.saveTalon(talon);
    }
    
    public Talon addZone(String talonId) {
    	Talon talon = talonService.getTalon(talonId);
    	talon.setZones(talon.getZones() + 1);
    	talon.setDesc(talon.getDesc() + userService.getCurrentUserInfo().getFullName() + ", " 
			+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
			+ " - додано зону.<br/><br/>");
    	return talonService.saveTalon(talon);
    }

    ///////////////////////////////////////// Logic ////////////////////////////////////////

    // cancelation after execution : some talons may be canceled when the procedure is done
    // i.e ultrasound must be canceled if laser has been done
    private void cancelTalonsByCard(Procedure procedure, String patientId) {
		// List<Integer> proceduresToClose = cardService
    	//	.getCardByProcedureId(procedure.getId()).getCloseAfter();

    	// List<Integer> proceduresToClose = procedure.getCard().getCloseAfter();
        List<Integer> proceduresToClose = procedureService.getProcedure(procedure.getId())
            .getCard().getCloseAfter();
        patientService.getPatientWithTalons(patientId).getTalons().stream()
            .filter(talon -> !talon.getActivity().equals(Activity.EXECUTED))
            .forEach(talon -> {
	            if (proceduresToClose.contains(Integer.valueOf(talon.getProcedure().getId()))){
	                talon.setActivity(Activity.CANCELED);
	                talonService.saveTalon(talon);
	            }
	        });
    }
    
    //   activation after execution : some talons may be activated ONLY when the procedure is done
    // i.e. i.e massage after water-pulling. Because of gell.
    private void activateTalonsByCard(Procedure procedure, String patientId) {
        // List<Integer> proceduresToActivate = procedure.getCard().getActivateAfter();
        List<Integer> proceduresToActivate = procedureService.getProcedure(procedure.getId())
            .getCard().getActivateAfter();

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
              // && permission
                ) {
                talon.setActivity(Activity.ACTIVE);
                talonService.saveTalon(talon);
            }
        });
    }
}