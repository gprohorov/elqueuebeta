package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.med.model.Activity;
import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.PatientTalonTherapy;
import com.med.model.Procedure;
import com.med.model.ProcedureType;
import com.med.model.Status;
import com.med.model.Tail;
import com.med.model.Talon;
import com.med.model.Therapy;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.repository.TherapyRepository;

@Component
public class TherapyService {

	@Autowired
	TherapyRepository repository;

	@Autowired
	TalonService talonService;

	@Autowired
	PatientService patientService;

	@Autowired
	TailService tailService;

	@Autowired
	UserService userService;

	@Autowired
	ProcedureService procedureService;

	@Autowired
	AccountingService accountingService;

	public Therapy saveTherapy(Therapy therapy) {
		return repository.save(therapy);
	}

	public Therapy getTherapy(String id) {
		return repository.findById(id).orElse(null);
	}

	public List<Therapy> getAll() {
		return repository.findAll();
	}

	public Therapy finishTherapy(String therapyId) {
		Therapy therapy = this.getTherapy(therapyId);
		therapy.setFinish(LocalDateTime.now());
		return this.saveTherapy(therapy);
	}

	public Therapy findTheLastTherapy(String patientId) {
		return repository.findByPatientId(patientId).stream()
			.sorted(Comparator.comparing(Therapy::getStart).reversed()).findFirst().orElse(null);
	}

	// TODO: Remove harcoded value 
	public PatientTalonTherapy getPatientTalonTherapy(String patientId) {
		Talon talon = talonService.getAllTalonsForPatient(patientId).stream()
			.filter(tal -> tal.getProcedure().getProcedureType().equals(ProcedureType.DIAGNOSTIC))
			.findFirst().orElse(this.talonService.createTalon(patientId, 2, 0));
		Patient patient = patientService.getPatient(patientId);
		Therapy therapy = this.findTheLastTherapy(patientId);
		return new PatientTalonTherapy(patient, talon, therapy);
	}

	public void startProcedure(String talonId) {
		Talon talon = talonService.getTalon(talonId);
		Patient patient = patientService.getPatientWithTalons(talon.getPatientId());

		Tail tail = tailService.getTail(talon.getProcedure().getId());
		if (tail != null) {
			tail.setPatientOnProcedure(patient);
			tail.setVacant(false);
		}

		Doctor doctor = userService.getCurrentUserInfo();

		talon.setActivity(Activity.ON_PROCEDURE);
		talon.setStart(LocalDateTime.now());
		talon.setDoctor(doctor);

		talon.setDesc(doctor.getFullName() + ", "
			+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
			+ " - дiaгностику  розпочато.<br/><br/>");

		patient.setLastActivity(LocalDateTime.now());
		patientService.savePatient(patient);

		tailService.setSemaforSignal(talon.getProcedure().getId(), false);
		talonService.saveTalon(talon);
	}

	public void cancelProcedure(String talonId) {
		Talon talon = talonService.getTalon(talonId);
		Tail tail = tailService.getTail(talon.getProcedure().getId());
		talon.setActivity(Activity.TEMPORARY_NA);
		Doctor doctor = userService.getCurrentUserInfo();
		talon.setDesc(talon.getDesc() + "/n" + doctor.getFullName() + "cancelled "
			+ LocalDateTime.now().toString());
		tail.setPatientOnProcedure(null);
		tail.setVacant(true);
		talonService.saveTalon(talon);
	}

	public void executeProcedure(String talonId, Therapy therapy) {
		Talon talon = talonService.getTalon(talonId);
		if (talon == null) return;

		Tail tail = tailService.getTail(talon.getProcedure().getId());
		if (tail != null) {
			tail.setPatientOnProcedure(null);
			tail.setVacant(true);
		}
		
		Patient patient = patientService.getPatient(talon.getPatientId());

		talon.setActivity(Activity.EXECUTED);
		talon.setExecutionTime(LocalDateTime.now());

		talon.setDesc(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
			+ " - процедуру завершено.<br/><br/>");
		talon.setStatus(patient.getStatus());
		talon.setZones(0);

		patient.setLastActivity(LocalDateTime.now());
		patientService.savePatient(patient);

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
			manualTherapyTalon.setSum(this.getPrice(patient.getStatus()));
			manualTherapyTalon.setDate(LocalDate.now());
			manualTherapyTalon.setZones(1);
			manualTherapyTalon.setDoctor(talon.getDoctor());
			manualTherapyTalon.setStatus(patient.getStatus());
			talonService.saveTalon(manualTherapyTalon);

			// accounting manual
			accountingService.createAccounting(new Accounting(
				manualTherapyTalon.getDoctor().getId(),
				patient.getId(),
				LocalDateTime.now(),
				talon.getId(),
				(-1 * manualTherapyTalon.getSum()),
				PaymentType.PROC,
				manual.getName()
			));
		}

		repository.save(therapy);
		
		talonService.saveTalon(talon);

		// TODO: Remove hardcoded value!!!
		accountingService.createAccounting(new Accounting(
				talon.getDoctor().getId(),
				patient.getId(),
				LocalDateTime.now(),
				talon.getId(),
				procedureService.getProcedure(2).getSOCIAL() * (-1),
				PaymentType.PROC,
				"Діагностика "
		));

		
		List<Talon> toDelete = talonService.getAllTalonsForPatient(patient.getId()).stream()
            .filter(tal -> (tal.getActivity().equals(Activity.NON_ACTIVE)
                || tal.getActivity().equals(Activity.ACTIVE))).collect(Collectors.toList());
		talonService.deleteAll(toDelete);

		talonService.saveTalons(this.generateTalonsByTherapy(therapy));
	}

	//////////// 02.07.18 ///////////////////////////
	public List<Talon> generateTalonsByTherapy(Therapy therapy) {

		List<Procedure> procedures = new ArrayList<>();
		List<Talon> talons = new ArrayList<>();

		therapy.getAssignments().stream().forEach(ass -> {
			procedures.add(procedureService.getProcedure(ass.getProcedureId()));
		});

		for (Procedure procedure : procedures) {
			talons.add(new Talon(therapy.getPatientId(), procedure, LocalDate.now()));
		}

		// TODO: Remove hardcoded value!!!
		Talon manualToday = talons.stream()
            .filter(talon -> talon.getProcedure().getId() == 3)
            .filter(talon -> talon.getActivity().equals(Activity.NON_ACTIVE))
            .filter(talon -> talon.getDate().equals(LocalDate.now())).findFirst().orElse(null);
		if (manualToday != null) talons.remove(manualToday);

 		talons.stream().forEach(talon -> {
 			if (talon.getProcedure().getCard().isAnytime()) talon.setActivity(Activity.ACTIVE);
		});
 		
		return talons;
	}

	////////// 13.08 18
	public List<Talon> generateTalonsByTherapyToDate(Therapy therapy, LocalDate date) {

		List<Procedure> procedures = new ArrayList<>();
		List<Talon> talons = new ArrayList<>();

		therapy.getAssignments().stream().forEach(ass -> {
			procedures.add(procedureService.getProcedure(ass.getProcedureId()));
		});

		procedures.stream().forEach(procedure -> {
			Talon talon = new Talon(therapy.getPatientId(), procedure, date);
			if (procedure.getCard().isAnytime() && date.equals(LocalDate.now())) {
				talon.setActivity(Activity.ACTIVE);
			}
			talons.add(talon);
		});

		return talons;
	}

	// TODO: Remove hardcoded value
	private int getPrice(Status status) {
		Procedure procedure = procedureService.getProcedure(3);
		switch (status) {
			case SOCIAL: return procedure.getSOCIAL();
			case VIP: return procedure.getVIP();
			case ALL_INCLUSIVE: return procedure.getALL_INCLUSIVE();
			case BUSINESS: return procedure.getBUSINESS();
			case FOREIGN: return procedure.getFOREIGN();
			default: return procedure.getSOCIAL();
		}
	}
}