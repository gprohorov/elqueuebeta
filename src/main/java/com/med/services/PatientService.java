package com.med.services;

import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.PatientRegDTO;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.Therapy;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.repository.AccountingRepository;
import com.med.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	PatientRepository repository;

	@Autowired
	TalonService talonService;

	@Autowired
	AccountingService accountingService;

	@Autowired
	AccountingRepository accountingRepository;

	@Autowired
	TherapyService therapyService;

	public void deleteAll(List<Patient> patients) {
		repository.deleteAll(patients);
	}

	public Patient deletePatient(String id) {
		Patient patient = this.getPatient(id);
		talonService.deleteAll(talonService.getAllTalonsForPatient(id));
		accountingService.deleteAll(accountingService.getByPatientId(id));
		repository.deleteById(id);
		return patient;
	}

	public Patient registratePatient(PatientRegDTO data) {
		Patient patient = new Patient(data.getPerson());
		patient.setRegistration(LocalDateTime.now());
		if (data.isActivate()) {
			patient.setStartActivity(LocalDateTime.now());
			patient.setLastActivity(LocalDateTime.now());
		}
		repository.save(patient);
		if (data.isActivate() && data.getDate() != null 
			&& data.getAppointed() > 0 && data.getProcedureId() > 0) {
			talonService.createActiveTalon(patient.getId(), data.getProcedureId(),
				LocalDate.parse(data.getDate()), data.getAppointed(), data.isActivate(), false);
		}
		return patient;
	}

	public Patient savePatient(Patient patient) {
		return repository.save(patient);
	}

	public Patient getPatient(String patientId) {
		Patient patient = repository.findById(patientId).orElse(null);
		if (patient == null) System.out.println(patient);
		Therapy therapy = therapyService.findTheLastTherapy(patientId);
		System.out.println("Patient      "  + patientId + "  " + patient);
		if ( (patient!= null) && (therapy) != null) patient.setTherapy(therapy);
		return patient;
	}

	public Patient getPatientWithTalons(String id) {
		Patient patient = repository.findById(id).orElse(null);
		talonService.getTalonsForToday().stream().filter(talon -> talon.getPatientId().equals(id))
			.forEach(talon -> patient.getTalons().add(talon));
		return patient;
	}

	public Patient getPatientWithOneTalon(String patientId, int procedureId) {
		Patient patient = repository.findById(patientId).get();
		patient.getTalons().add(talonService.getTalonForTodayForPatientForProcedure(patientId, procedureId));
		return patient;
	}

	public List<Patient> getAll(String fullName) {
		Collator ukrCollator = Collator.getInstance(new Locale("uk", "UA"));
	    ukrCollator.setStrength(Collator.PRIMARY);
		return repository.findByThePersonFullName(fullName).stream().sorted((o1, o2) -> 
			ukrCollator.compare(o1.getPerson().getFullName(), o2.getPerson().getFullName())
		).limit(30).collect(Collectors.toList());
	}

	public List<Patient> getAllForToday() {
		return this.getAllForDate(LocalDate.now());
	}

	public List<Patient> saveAll(List<Patient> patients) {
		return repository.saveAll(patients);
	}

	public Patient setStatus(String patientId, Status status) {
		Patient patient = this.getPatient(patientId);
		patient.setStatus(status);
		return repository.save(patient);
	}
	
	public Patient updateDiscount(String patientId, int discount) {
		Patient patient = this.getPatient(patientId);
		patient.setDiscount(discount);
		return repository.save(patient);
	}

	public Accounting insertIncome(String patientId, int sum, String desc, PaymentType payment) {
		return accountingService.createAccounting(
			new Accounting(patientId, LocalDateTime.now(), sum, PaymentType.CASH, desc));
	}

	public Integer recalcBalance(String patientId) {
		return accountingRepository.findByPatientId(patientId).stream().mapToInt(Accounting::getSum).sum();
	}

	//////////// ULTIMATE BALANCE ///////////////
	public List<Accounting> getUltimateBalance(String patientId, LocalDate start, LocalDate finish) {
		return accountingRepository.findByPatientIdAndDateBetween(patientId, start, finish);
	}

	public List<Accounting> getUltimateBalanceShort(String patientId, int days) {
		return this.getUltimateBalance(
			patientId, LocalDate.now().minusDays(days + 1), LocalDate.now().plusDays(1));
	}

	public List<Accounting> getUltimateBalanceToday(String patientId) {
		LocalDate start = LocalDate.of(2018, Month.AUGUST, 28);
		return this.getUltimateBalance(
			patientId, start, LocalDate.now().plusDays(1));
	}

	public List<Accounting> getBalanceForCurrentTherapy(String patientId) {
		LocalDate start = therapyService.findTheLastTherapy(patientId).getStart().toLocalDate();
		return this.getUltimateBalance(patientId, start, LocalDate.now());
	}

	public List<Patient> getDebetors() {
		return repository.findByBalanceLessThan(0);
	}

	// !!! PATIENTS WITH TALONS FOR DATE !!!
	public List<Patient> getAllForDate(LocalDate date) {
		List<Patient> patients = new ArrayList<>();
	//	final long start = System.currentTimeMillis();
		List<Talon> talons = talonService.getTalonsForDate(date);
	//	System.out.println( System.currentTimeMillis() - start);
		talons.stream().collect(Collectors.groupingBy(Talon::getPatientId)).entrySet().stream()
			.forEach(entry -> {
				Patient patient = this.getPatient(entry.getKey());
				if (patient != null) {
					patient.setTalons(entry.getValue());
					patient.setActivity(patient.calcActivity());
					if (patient.getDelta() != null && patient.getDelta() > 300) {
						patient.setStartActivity(LocalDateTime.now());
						patient.setLastActivity(LocalDateTime.now());
						repository.save(patient);
					}
					patient.setTherapy(null);
					patients.add(patient);
				}
			});
		//System.out.println( System.currentTimeMillis() - start);
		return patients;
	}

	/////////////// assign to date //////////////////////////
	public List<Talon> assignPatientToDate(String patientId, LocalDate date, int time) {
		this.repository.save(this.patientBye(patientId));
		return talonService.createTalonsForPatientToDate(patientId, date, time);
	}

	public Patient patientBye(String patientId) {
		Patient patient = this.getPatientWithTalons(patientId);
		List<Talon> talons = patient.getTalons();
		talons.stream().forEach(talon -> {
			if (!talon.getActivity().equals(Activity.EXECUTED)) talon.setActivity(Activity.CANCELED);
		});
		talonService.saveTalons(talons);
		patient.setLastActivity(null);
		patient.setStartActivity(null);
		return repository.save(patient);
	}

	private LocalDate getLastVisit(Patient patient) {
		List<Talon> talons = talonService.getAllTalonsForPatient(patient.getId()).stream()
			.filter(talon -> talon.getActivity().equals(Activity.EXECUTED)).collect(Collectors.toList());
		if (talons.isEmpty()) return LocalDate.now().minusDays(100);
		return talons.stream().sorted(Comparator.comparing(Talon::getDate).reversed()).findFirst().get().getDate();
	}
	
	// Если не появлялся более 5 дней, days -> 0
	// проверка  в 22.00
	@Scheduled(cron = "0 0 22 * * *")
	private void setDaysToZero() {
		repository.findAll().stream().forEach(patient -> {
			if (this.getLastVisit(patient).isBefore(LocalDate.now().minusDays(5))) { 
				patient.setDays(0);
				repository.save(patient);
			}
		});
	}

	//@Scheduled(cron = "0 51 22 * * *")
	public void  showGrouppedByAge(){

		System.out.println("--------------------------------------");
		System.out.println(repository.findAll().size());

		List<Integer> list = repository.findAll().stream()
				.filter(patient -> patient.getPerson().getDateOfBirth() != null)
				.mapToInt(patient -> patient.getPerson().getDateOfBirth().getYear()).boxed()
				.collect(Collectors.toList());
		System.out.println(list.size());

		final Map<Integer, Long> groupped = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		groupped.entrySet().stream()
				.forEach(entry-> {
					System.out.println(entry.getKey() + "  " + entry.getValue());
				});


	}

}
