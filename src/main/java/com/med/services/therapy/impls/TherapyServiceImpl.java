package com.med.services.therapy.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.repository.therapy.TherapyRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
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

	@Autowired
	ProcedureServiceImpl procedureService;

	@Autowired
	AccountingServiceImpl accountingService;



	public Therapy createTherapy(Therapy therapy) {
		return repository.save(therapy);
	}

	public Therapy saveTherapy(Therapy therapy) {
		return repository.save(therapy);
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

	// TODO: Human way
	// DONE!
	public Therapy findTheLastTherapy(String patientId) {

		// return
		// this.getAll().stream().filter(th->th.getPatientId().equals(patientId))
		return repository.findByPatientId(patientId).stream().sorted(Comparator.comparing(Therapy::getStart).reversed())
				.findFirst().orElse(null);
	}

	public PatientTalonTherapy getPatientTalonTherapy(String patientId) {
		Talon talon = talonService.findAll().stream().filter(tal -> tal.getPatientId().equals(patientId))
				.filter(tal -> tal.getProcedure().getProcedureType().equals(ProcedureType.DIAGNOSTIC))
				.filter(tal -> (tal.getActivity().equals(Activity.ACTIVE)
						|| tal.getActivity().equals(Activity.ON_PROCEDURE)))
				.findFirst().orElse(null);
		Patient patient = patientService.getPatient(patientId);
		Therapy therapy = this.findTheLastTherapy(patientId);

		return new PatientTalonTherapy(patient, talon, therapy);
	}

	public void startProcedure(String talonId) {

		Talon talon = talonService.getTalon(talonId);
		Patient patient = patientService.getPatientWithTalons(talon.getPatientId());

		Tail tail = tailService.getTail(talon.getProcedure().getId());

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
		Tail tail = tailService.getTail(talon.getProcedure().getId());
		talon.setActivity(Activity.TEMPORARY_NA);
		Doctor doctor = userService.getCurrentUserInfo();
		talon.setDesc(talon.getDesc() + "/n" + doctor.getFullName() + "cancelled " + LocalDateTime.now().toString());

		tail.setPatientOnProcedure(null);
		tail.setVacant(true);

		talonService.saveTalon(talon);
	}

	public void executeProcedure(String talonId, Therapy therapy) {
		Talon talon = talonService.getTalon(talonId);

		if (talon == null) {
			return;
		}
		Procedure procedure = talon.getProcedure();
		Tail tail = tailService.getTail(procedure.getId());
		Patient patient = patientService.getPatient(talon.getPatientId());

		talon.setActivity(Activity.EXECUTED);
		talon.setExecutionTime(LocalDateTime.now());

		String desc = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
				+ " - процедуру завершено.<br/><br/>";
		talon.setDesc(desc);
		talon.setStatus(patient.getStatus());
		talon.setZones(0);

		patient.setLastActivity(LocalDateTime.now());
		patientService.savePatient(patient);

		tail.setPatientOnProcedure(null);
		tail.setVacant(true);

		therapy.setPatientId(patient.getId());
		therapy.setStart(LocalDateTime.now());

		if (therapy.getManualTherapy()) {

			////////////////////// manual Therapy Talon
			Talon manualTherapyTalon = new Talon();
			manualTherapyTalon.setActivity(Activity.EXECUTED);
			manualTherapyTalon.setStart(talon.getStart());
			manualTherapyTalon.setExecutionTime(LocalDateTime.now());

			// TODO: Remove hardcoded value!!!
			Procedure manual = procedureService.getProcedure(3);
			
			manualTherapyTalon.setProcedure(manual);
			manualTherapyTalon.setPatientId(patient.getId());
			manualTherapyTalon.setSum(manual.getSOCIAL());
			manualTherapyTalon.setDate(LocalDate.now());
			manualTherapyTalon.setZones(1);
			manualTherapyTalon.setDoctor(talon.getDoctor());
			manualTherapyTalon.setStatus(patient.getStatus());
			talonService.saveTalon(manualTherapyTalon);

			////////////////// accounting manual

			String descr = manual.getName();
			Accounting accounting = new Accounting(manualTherapyTalon.getDoctor().getId(), patient.getId(),
					LocalDateTime.now(), talon.getId(), (-1 * manualTherapyTalon.getSum()), PaymentType.PROC, descr);
			accountingService.createAccounting(accounting);
		}

		repository.save(therapy);
		talonService.saveTalon(talon);
		List<Talon> toDelete = talonService.getAllTalonsForPatient(patient.getId())
                .stream()
                .filter(tal->(tal.getActivity().equals(Activity.NON_ACTIVE)
                            ||tal.getActivity().equals(Activity.ACTIVE)))
                        .collect(Collectors.toList());
		talonService.deleteAll(toDelete);

		talonService.saveTalons(this.generateTalonsByTherapy(therapy));

	}

	//////////// 02.07.18 ///////////////////////////
	public List<Talon> generateTalonsByTherapy(Therapy therapy) {

		int days = therapy.getDays();

		List<Procedure> procedures = new ArrayList<>();
		List<Talon> talons = new ArrayList<>();

		therapy.getAssignments().stream().forEach(ass ->

		{
			Procedure procedure = procedureService.getProcedure(ass.getProcedureId());
			procedures.add(procedure);
		});

		for (Procedure procedure : procedures) {
            int jump = procedure.getCard().getDays();
			for (int i = 0; i < days; i++) {
				if (i % jump==0) {
                    talons.add(new Talon(therapy.getPatientId(), procedure, LocalDate.now().plusDays(i)));
                }
			}
		}
		//////////////////////  kostil'
		Talon manualToday = talons.stream()
                .filter(talon -> talon.getProcedure().getId()==3)
                .filter(talon -> talon.getActivity().equals(Activity.NON_ACTIVE))
                .filter(talon -> talon.getDate().equals(LocalDate.now()))
                .findFirst().orElse(null);
		if (manualToday!=null){

		    //int index = talons.indexOf(manualToday);
		    talons.remove(manualToday);
        }



		return talons;
	}


	//////////    13.08 18
	public List<Talon> generateTalonsByTherapyToDate(Therapy therapy, LocalDate date) {



		List<Procedure> procedures = new ArrayList<>();
		List<Talon> talons = new ArrayList<>();

		therapy.getAssignments().stream().forEach(ass ->

		{
			Procedure procedure = procedureService.getProcedure(ass.getProcedureId());
			procedures.add(procedure);
		});

		procedures.stream().forEach(procedure -> {
			talons.add(new Talon(therapy.getPatientId(), procedure, date));
		});

		return talons;
	}

}
