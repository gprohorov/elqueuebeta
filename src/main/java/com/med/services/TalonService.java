package com.med.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.med.model.Activity;
import com.med.model.Assignment;
import com.med.model.Doctor;
import com.med.model.LastTalonInfo;
import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.model.Therapy;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.balance.Receipt;
import com.med.model.statistics.dto.procedure.ProcedureReceipt;
import com.med.repository.TalonRepository;

import javax.annotation.PostConstruct;

@Component
public class TalonService {

    @Autowired
    TalonRepository repository;

    @Autowired
    PatientService patientService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    TherapyService therapyService;

    @Autowired
    AccountingService accountingService;

    @PostConstruct
    void init() {
       // inject();

    }

    public Talon createTalon(String patientId, int procedureId, int days) {
        return repository.save(new Talon(patientId, procedureService.getProcedure(procedureId), days));
    }

    public Talon saveTalon(Talon talon) {
        return repository.save(talon);
    }

    public Talon createTalon(String patientId, int procedureId, LocalDate date) {
        return repository.save(new Talon(patientId, procedureService.getProcedure(procedureId), date));
    }
    
    public Talon createTalon(String patientId, int procedureId, LocalDate date, int time) {
        return repository.save(new Talon(patientId, procedureService.getProcedure(procedureId), date));
    }
    
    public Talon createActiveTalon(
		String patientId, int procedureId, LocalDate date, int time, Boolean activate, Boolean schema) {
    	Talon talon = new Talon(patientId, procedureService.getProcedure(procedureId), date, time);
    	if (activate) {
    		talon.setActivity(Activity.ACTIVE);
    		Patient patient = patientService.getPatient(patientId);
    		if (patient.getStartActivity() == null) {
	    		patient.setStartActivity(LocalDateTime.now());
	    		patient.setLastActivity(LocalDateTime.now());
	    		patientService.savePatient(patient);
    		}
    	}
    	if (schema) {
    		Therapy therapy = therapyService.findTheLastTherapy(patientId);
    		List<Assignment> assignments = therapy.getAssignments();
    		ArrayList<ArrayList<Object>> picture = assignments.get(0).getPicture();
    		assignments.add(new Assignment(procedureId, "", picture));
    		therapy.setAssignments(assignments);
    		therapyService.saveTherapy(therapy);
    	}
        return repository.save(talon);
    }

    public List<Talon> getAll() {
        return repository.findAll();
    }

    public Talon getTalon(String id) {
        return repository.findById(id).orElse(null);
    }

    public Talon getTalonByProcedure(String patientId, int procedureId, Activity activity) {
    	return this.getTalonsForToday().stream()
            .filter(tal -> tal.getProcedure().getId() == procedureId)
            .filter(tal -> tal.getPatientId() == patientId)
            .filter(tal -> tal.getActivity().equals(activity))
            .findFirst().orElse(null);
    }

    public Talon getTalonByPatient(String patientId, Activity activity) {
        return this.getTalonsForToday().stream().filter(tal -> tal.getPatientId().equals(patientId))
            .filter(tal -> tal.getActivity().equals(activity)).findFirst().orElse(null);
    }

    public Talon getTalonByDoctor(String patientId, int doctorId, Activity activity) {
        return this.getTalonsForToday().stream().filter(tal -> tal.getDoctor().getId() == doctorId)
            .filter(tal -> tal.getPatientId() == patientId)
            .filter(tal -> tal.getActivity().equals(activity)).findFirst().orElse(null);
    }

    public List<Talon> getTalonsForToday() {
        return repository.findByDate(LocalDate.now());
    }

    public Talon getTalonForTodayForPatientForProcedure(String patientId, int procedureId) {
        return repository.findByDateAndPatientId(LocalDate.now(), patientId)
            .stream().filter(talon -> ( talon.getActivity().equals(Activity.ACTIVE)
	        		|| talon.getActivity().equals(Activity.ON_PROCEDURE) )
	            && talon.getProcedure().getId() == procedureId).findFirst().get();
    }

    public List<Talon> getAllTalonsForPatient(String patientId) {
        return repository.findByPatientId(patientId);
    }

    public List<Talon> getTalonsForDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Talon> getAllTallonsBetween(LocalDate start, LocalDate finish) {
        return repository.findByDateBetween(start.minusDays(1), finish.plusDays(1));
    }

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
        if (talon != null && !activities.contains(talon.getActivity())) {
            Activity former = talon.getActivity();
            talon.setActivity(activity);
            if (former == Activity.NON_ACTIVE && activity == Activity.ACTIVE) {
                Patient patient = patientService.getPatient(talon.getPatientId());
                LocalDateTime start = patient.getStartActivity();
                if (start == null || start.toLocalDate().isBefore(LocalDate.now())) {
                    patient.setStartActivity(LocalDateTime.now());
                    patient.setLastActivity(LocalDateTime.now());
                }
                patientService.savePatient(patient);
            }
        }
        return repository.save(talon);
    }

    public void setAllActivity(String patientId, Activity activity) {

    	List<Talon> talons = repository.findByDateAndPatientId(LocalDate.now(), patientId);
    	Patient patient = patientService.getPatient(patientId);
    	
    	if (activity.equals(Activity.ACTIVE)) {
    		//patient.setDays(this.calculateDays(patientId));
    		patientService.savePatient(patient);
    		List<Integer> free = procedureService.getFreeProcedures();
    		talons.stream().forEach(talon -> {
				if (free.contains(Integer.valueOf(talon.getProcedureId()))) {
					this.setActivity(talon.getId(), Activity.ACTIVE);
				}
    		});
		} else {
			talons.stream().forEach(talon -> this.setActivity(talon.getId(), activity));
		}
	
		if (activity.equals(Activity.NON_ACTIVE)) {
		    patient.setStartActivity(null);
		    patient.setLastActivity(null);
		    patientService.savePatient(patient);
		}
    }
    
    public List<Patient> toPatientList(List<Talon> talons) {
        List<Patient> patients = new ArrayList<>();
        talons.stream().forEach(talon -> {
            Patient patient = patientService.getPatient(talon.getPatientId());
            patient.setActivity(talon.getActivity());
            patient.setTherapy(null);
            patient.setTalon(talon);
            patients.add(patient);
        });
        return patients;
    }

    public List<Talon> saveTalons(List<Talon> talons) {
        return repository.saveAll(talons);
    }

    public List<Talon> getAllExecutedTalonsForPatientFromTo(
    		String patientId, LocalDate start, LocalDate finish) {
        return getAll().stream()
            .filter(talon -> talon.getPatientId().equals(patientId))
            .filter(talon -> talon.getDate().isAfter(start.minusDays(0)))
            .filter(talon -> talon.getDate().isBefore(finish.plusDays(1)))
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .collect(Collectors.toList());
    }

    public void deleteAll(List<Talon> talons) {
        repository.deleteAll(talons);
    }

    public List<Procedure> getFilledProcedures() {
        List<Procedure> procedures = procedureService.getAll();
        List<Talon> talons = this.getTalonsForToday();
        procedures.stream().forEach(procedure -> {
            procedure.setToday((int) talons.stream()
        		.filter(talon -> talon.getProcedure().equals(procedure)).count());
        });
        return procedures;
    }

    public Talon setOutOfTurn(String talonId, boolean out) {
        Talon talon = this.getTalon(talonId);
        if (talon.getActivity().equals(Activity.ON_PROCEDURE)) return null;
        talon.setActivity(out ? Activity.INVITED : Activity.ACTIVE);
        return repository.save(talon);
    }

    /////////// without appointment 9.00 by default
    public List<Talon> createTalonsForPatientToDate(String patientId, LocalDate date) {
        List<Talon> talons = new ArrayList<>();
        this.removeTalonsForPatientToDate(patientId, date);
        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        if (therapy != null) {
        	talons = therapyService.generateTalonsByTherapyToDate(therapy, date);
        } else {
            // TODO: Remove hardcoded diagnostic, use procedure type instead
            talons.add(new Talon(patientId, procedureService.getProcedure(2), date));
        }
        return repository.saveAll(talons);
    }

    ///////////  with appointment
    public List<Talon> createTalonsForPatientToDate(String patientId, LocalDate date, int time) {
        Patient patient = patientService.getPatientWithTalons(patientId);
        List<Talon> talons = new ArrayList<>();
        this.removeTalonsForPatientToDate(patientId, date);
        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        if (therapy != null) {
        	talons = therapyService.generateTalonsByTherapyToDate(therapy, date);
        	talons.stream().forEach(talon -> {
	            talon.setAppointed(time);
	            if (date.equals(LocalDate.now())) {
	                patient.setStartActivity(LocalDateTime.now());
	                patient.setLastActivity(LocalDateTime.now());
	                patientService.savePatient(patient);
	            }
        	});
         
	    	// TODO: Remove hardcoded procedure, use procedure type instead (make it first)
	        // recall the udarno-wave therapy last time date - KOSTIL
    		Talon talon = talons.stream()
				.filter(tl -> tl.getProcedure().getId() == 9).findFirst().orElse(null);
	        if (talon != null) talon.setLast(this.getLastExecuted(talon));
         } else {
        	// TODO: Remove hardcoded diagnostic, use procedure type instead
            talons.add(new Talon(patientId, procedureService.getProcedure(2), date, time));
        }
        return repository.saveAll(talons);
    }

    // TODO: Make it by MongoRepository
    ///////// remove all talons for date in order to create new ones
    public void removeTalonsForPatientToDate(String patientId, LocalDate date) {
        repository.deleteAll(repository.findByDateAndPatientId(date, patientId));
    }

    public Talon quickExecute(String talonId) {

        Talon talon = repository.findById(talonId).orElse(null);
        Procedure procedure = talon.getProcedure();

        // TODO: Remove hardcoded procedure, use procedure type instead (make it first)
        //  3 - means that this procedure is Manual Therapy
        if (procedure.getId() != 3) return null;

        Patient patient = patientService.getPatient(talon.getPatientId());
        patient.setLastActivity(LocalDateTime.now());

        //TODO: 1 means that this doctor is Nechay
        //TODO: 5 means that this doctor is Zakhlivniak
        Doctor doctor = doctorService.getDoctor(1);

        talon.setActivity(Activity.EXECUTED);
        talon.setStart(LocalDateTime.now());
        talon.setExecutionTime(LocalDateTime.now());
        talon.setZones(1);
        talon.setDoctor(doctor);

        talon.setDesc(talon.getDesc() + "Адміністратор, "
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            + " - процедуру завершено.<br/><br/>");
        talon.setStatus(patient.getStatus());

        int price = this.getPrice(patient, procedure.getId());

        int sum = procedure.isZoned() ? price * talon.getZones() : price;
        talon.setSum(sum);

        // create accounting
        Accounting accounting = new Accounting(doctor.getId()
            , patient.getId()
            , LocalDateTime.now()
            , talon.getId()
            , (-1 * sum)
            , PaymentType.PROC
            , procedure.getName() + " x" + talon.getZones());
        accountingService.createAccounting(accounting);

        return repository.save(talon);
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

    private LastTalonInfo getLastExecuted(Talon talon) {
        Talon tln = repository.findByPatientIdAndDateGreaterThan(
    		talon.getPatientId(), LocalDate.now().minusDays(10)).stream()
        		// TODO: Remove hardcoded procedure, use procedure type instead (make it first)
                .filter(tal -> tal.getProcedure().getId() == 9 )
                .filter(tal -> tal.getActivity().equals(Activity.EXECUTED))
                .sorted(Comparator.comparing(Talon::getDate).reversed())
                .findFirst().orElse(null);
        return (tln == null) ? null : new LastTalonInfo(tln.getDate(), tln.getZones(), tln.getDoctor());
    }

    // 27 aug  receipt means check
    public Receipt createReceipt(String patientId, LocalDate from, LocalDate to) {
        List<Talon> talons = repository.findByPatientIdAndDateGreaterThan(patientId, from.minusDays(15))
            .stream().filter(talon -> talon.getDate().isBefore(to.plusDays(1)))
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED)).collect(Collectors.toList());
        List<ProcedureReceipt> list = new ArrayList<>();
        Receipt receipt = new Receipt();
        receipt.setPatient(patientService.getPatient(patientId));
        talons.stream().collect(Collectors.groupingBy(Talon::getProcedure)).forEach((key, value) -> {
            ProcedureReceipt procedureReceipt = new ProcedureReceipt();
            procedureReceipt.setName(key.getName());
            procedureReceipt.setAmount(value.size());
            int zones = value.stream().mapToInt(Talon::getZones).sum();
            procedureReceipt.setZones(zones);
            int sum = value.stream().mapToInt(Talon::getSum).sum();
            procedureReceipt.setSum(sum);
            procedureReceipt.setPrice((zones == 0 ) ? 0 : sum / zones);
            list.add(procedureReceipt);
        });
        receipt.setList(list);
        receipt.setSum((int) list.stream().mapToLong(ProcedureReceipt::getSum).sum());

        List<Accounting> accountings = accountingService.getAllIncomesForPatientFromTo(
    		patientId, from.minusDays(15), to.plusDays(1));
        int discount = accountings.stream().filter(ac -> ac.getPayment().equals(PaymentType.DISCOUNT))
           .mapToInt(Accounting::getSum).sum();
        receipt.setDiscount(discount);

        int hotel = accountings.stream().filter(ac -> ac.getPayment().equals(PaymentType.HOTEL))
           .mapToInt(Accounting::getSum).sum();

        receipt.setHotel(-1 * hotel);
        return receipt;
    }

    //// 11 Sept
    private int calculateDays(String patientId) {
        List<Talon> talons = this.getAllExecutedTalonsForPatientFromTo(
    		patientId, LocalDate.now().minusDays(21), LocalDate.now()).stream()
        		// TODO: Remove hardcoded procedure, use procedure type instead
        		.filter(talon -> talon.getProcedure().getId() != 2).collect(Collectors.toList());
        if (talons.isEmpty()) return 0;
        return Period.between(
    		talons.stream().min(Comparator.comparing(Talon::getDate)).get().getDate(),
    		LocalDate.now()).getDays() + 1;
    }

    private Patient setDaysToPatientOfToday(Patient patient) {
        List<Talon> talons = this.getAllExecutedTalonsForPatientFromTo(
    		patient.getId(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)
		).stream().filter(talon -> talon.getProcedureId() > 3).collect(Collectors.toList());

        if (talons.size() > 0) {
            patient.setDays(patient.getDays() + 1);
            return patientService.savePatient(patient);
        }
        return null;
    }

    // В 21.00 каждый день, каждому сегодняшнему пациенту добавляем день
    @Scheduled(cron = "0 0 21 * * *")
    private void incrementDaysForAllPatientsOfToday() {
    	patientService.getAllForToday().stream().forEach(patient -> this.setDaysToPatientOfToday(patient));
    }

    public void inject(){

        System.out.println("Injection");
        List<Talon> talons =
                //this.getAllTallonsBetween(LocalDate.now(), LocalDate.now().minusDays(2))
        repository.findByDate(LocalDate.now().minusDays(1))
                .stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .filter(talon -> talon.getDoctor().getId()==12)
                .sorted(Comparator.comparing(Talon::getStart))
                .collect(Collectors.toList());
        talons.forEach(talon -> System.out.println(talon.getStart()));
        System.out.println(talons.size());

    }
}
