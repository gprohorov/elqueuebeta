package com.med.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.med.model.statistics.dto.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.general.GeneralStatisticsDTO;
import com.med.model.statistics.dto.patient.DebetorDTO;
import com.med.model.statistics.dto.patient.PatientDTO;
import com.med.model.statistics.dto.procedure.ProcedureStatistics;

@Service
public class StatisticService {

    @Autowired
    DoctorService doctorService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    PatientService patientService;

    @Autowired
    TalonService talonService;

    @Autowired
    AccountingService accountingService;

    @Autowired
    SettingsService settings;

    public Long getCashAvailable() {
        return accountingService.getSumForDateCash(LocalDate.now());
    }

    public List<DoctorProcedureZoneFee> getDoctorsProceduresFromTo(LocalDate start, LocalDate finish) {

        List<Talon> talons = talonService.getAllTallonsBetween(start, finish);

        List<DoctorProcedureZoneFee> result = new ArrayList<>();

        Map<String, List<Talon>> map = talons.stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .collect(Collectors.groupingBy(talon->talon.getDoctor().getFullName()));

        map.entrySet().stream().forEach(entry -> {
            DoctorProcedureZoneFee item = new DoctorProcedureZoneFee();
            item.setName(entry.getKey());
            item.setProceduraCount(entry.getValue().size());
            item.setZonesCount(entry.getValue().stream().mapToLong(Talon::getZones).sum());
            item.setFee(entry.getValue().stream().mapToLong(Talon::getSum).sum());
            result.add(item);
        });
        return result;
    }

    // TODO: Why we need this: "Long before = 0L;" if we use "Long.valueOf"
    public Long getAllProceduresCount() {
        Long before = 0L;
        return Long.valueOf(talonService.getAllTallonsBetween(
    		LocalDate.now().minusYears(10), LocalDate.now()).size()) + before;
    }

    public Long getAllPatientsCount() {
        return Long.valueOf(patientService.getAll("").size());
    }

    public List<Patient> getAllDebtors() {
        return patientService.getDebetors();
    }

    public List<DebetorDTO> getAllDebtorsExt(LocalDate start, LocalDate finish) {
        return accountingService.getDebetorsExt(start, finish);
    }

    public Long getTotalCash() {
        return accountingService.getAll().stream().filter(accounting -> accounting.getSum() > 0)
            .filter(accounting -> !accounting.getPayment().equals(PaymentType.DISCOUNT))
            .mapToLong(Accounting::getSum).sum();
    }

    public Long getPatientTotalSum(String patientId) {
        return null;
    }

    // TODO: I don't know yet how, but this shit must be refactored. 
    public List<ProcedureStatistics> getProceduresStatistics(LocalDate start, LocalDate finish) {

	    List<ProcedureStatistics> list = new ArrayList<>();
	    List<Talon> talons = talonService.getAllTallonsBetween(start, finish);
	    final List<Procedure> procedures = procedureService.getAll();
	
	    procedures.stream().forEach(procedure -> {
	        ProcedureStatistics statistics = new ProcedureStatistics();
	        statistics.setProcedureId(procedure.getId());
	        statistics.setName(procedure.getName());
	        
	        Long assigned = talons.stream()
                .filter(talon -> talon.getProcedure().getId() == procedure.getId()).count();
	
	        Long executed = talons.stream()
                .filter(talon -> talon.getProcedure().getId() == procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED)).count();
	
	        Long cancelled = talons.stream()
                .filter(talon -> talon.getProcedure().getId() == procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.CANCELED)).count();
	
	        Long expired = assigned - executed - cancelled;
	
	        Long zones = (long) talons.stream()
                .filter(talon -> talon.getProcedure().getId() == procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .mapToInt(Talon::getZones).sum();
	
	        Long fee = (long) talons.stream()
                .filter(talon -> talon.getProcedure().getId() == procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .mapToInt(Talon::getSum).sum();
	
	        statistics.setAssigned(assigned);
	        statistics.setExecuted(executed);
	        statistics.setCancelled(cancelled);
	        statistics.setExpired(expired);
	        statistics.setZones(zones);
	        statistics.setFee(fee);

	        list.add(statistics);
	     });
	     return  list;
    }

    public List<DoctorPercent> getProcedureStatisticsByDoctor(
		LocalDate start, LocalDate finish, int procedureId) {

    	final List<Doctor> doctors = doctorService.getAllActive().stream()
			.filter(doctor -> doctor.getProcedureIds().contains(procedureId)).collect(Collectors.toList());

        final List<Talon> talons = talonService.getAllTallonsBetween(start, finish).stream()
            .filter(talon -> talon.getProcedure().getId() == procedureId)
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED)).collect(Collectors.toList());

        final Long all = Long.valueOf(talons.size());

        List<DoctorPercent> list = new ArrayList<>();

        if (all != 0) {
           doctors.stream().forEach(doctor -> {
               Long executed = talons.stream().filter(talon -> talon.getDoctor().equals(doctor)).count();
               list.add(new DoctorPercent(doctor.getFullName(), 100 * executed / all));
           });
        }
        return list;
    }

    public GeneralStatisticsDTO getGeneralStatisticsDay(LocalDate date) {

        GeneralStatisticsDTO statisticsDTO = new GeneralStatisticsDTO();
        List<Talon> talons = talonService.getTalonsForDate(date);
        List<Accounting> accountings = accountingService.getAllForDate(date);

        statisticsDTO.setDate(date);

        int patients = (int) talons.stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .map(talon -> talon.getPatientId()).distinct().count();
        statisticsDTO.setPatients(patients);

        int doctors = (int) talons.stream()
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .map(talon -> talon.getDoctor().getId()).distinct().count();
        statisticsDTO.setDoctors(doctors);

        long cash = accountings.stream()
    		.filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setCash(cash);

        long card = accountings.stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.CARD))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setCard(card);

        long wired = accountings.stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.WIRED))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setWired(wired);

        long check = accountings.stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.CHECK))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setCheck(check);

        long dodatok = accountings.stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.DODATOK))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setDodatok(dodatok);

        long bill = accountings.stream()
            .filter(accounting -> accounting.getSum() < 0).mapToLong(Accounting::getSum).sum();
        statisticsDTO.setBill(bill);

        long discount = accountings.stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.DISCOUNT))
            .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setDiscount(discount);

        statisticsDTO.setDebt(bill + cash + card + wired + check + dodatok + discount);
        return statisticsDTO;
    }

    public List<GeneralStatisticsDTO> getGeneralStatisticsFromTo(LocalDate start, LocalDate finish) {
        List<GeneralStatisticsDTO> list = new ArrayList<>();
        LocalDate date = start;
        while (!date.equals(finish.plusDays(1))) {
            GeneralStatisticsDTO dto = new GeneralStatisticsDTO();
            dto = this.getGeneralStatisticsDay(date);
            list.add(dto);
            date = date.plusDays(1);
        }
    	return list;
    }

    /////////////////////////////////////// CURRENT DOCTOR
    public DoctorCurrentStatistics getOneDoctorCurrentStatistics(int doctorId, LocalDate date) {
        List<Talon> talons = talonService.getTalonsForDate(date).stream()
            .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)
                        || talon.getActivity().equals(Activity.ON_PROCEDURE)))
            .filter(talon -> talon.getDoctor().getId() == doctorId).collect(Collectors.toList());

        DoctorCurrentStatistics statistics = new DoctorCurrentStatistics();
        statistics.setName(this.getLastName(doctorService.getDoctor(doctorId).getFullName()));

        if (!talons.isEmpty()) {
          //  LocalDateTime start = talons.stream().map(talon -> talon.getStart()).findFirst().orElse(null);
            LocalDateTime start = talons.stream()
                    .sorted(Comparator.comparing(Talon::getStart)).findFirst()
                    .get().getStart();

            statistics.setStartWork(start);

            Talon lastTalon = talons.stream()
                .sorted(Comparator.comparing(Talon::getStart).reversed()).findFirst().orElse(null);
            
            if (lastTalon.getActivity().equals(Activity.ON_PROCEDURE)) {
                statistics.setLastActivity(lastTalon.getStart());
                Patient patient = patientService.getPatient(lastTalon.getPatientId());
                statistics.setCurrentPatient(this.getLastName(patient.getPerson().getFullName()));
            }

            if (lastTalon.getActivity().equals(Activity.EXECUTED)) {
                statistics.setLastActivity(lastTalon.getExecutionTime());
                statistics.setCurrentPatient("");
            }

            Long zones = (long) talons.stream().filter(talon -> 
            	talon.getActivity().equals(Activity.EXECUTED)).mapToInt(Talon::getZones).sum();
            statistics.setZonesCount(zones);

            Long fee = (long) talons.stream().filter(talon -> 
            	talon.getActivity().equals(Activity.EXECUTED)).mapToInt(Talon::getSum).sum();
            statistics.setFee(fee);

            List<String> names = new ArrayList<>();

            talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .forEach(talon -> {
	                names.add(this.getLastName(patientService.getPatient(
                		talon.getPatientId()).getPerson().getFullName()));
                });
            statistics.setPatients(names.stream().distinct().collect(Collectors.toList()));

            List<ProcedureCount> map = new ArrayList<>();
            talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.groupingBy(tal -> tal.getProcedure(), Collectors.counting()))
                .forEach((key, value) -> {
                    map.add(new ProcedureCount(key.getName(), value));
                });
            statistics.setProcedureMap(map);
        }
        return statistics;
    }

    public List<DoctorCurrentStatistics> getDoctorsListCurrentStatictics(LocalDate date) {
        List<DoctorCurrentStatistics> list = new ArrayList<>();
        doctorService.getAllActiveDoctors().stream().forEach(doctor -> {
            DoctorCurrentStatistics statistics = this.getOneDoctorCurrentStatistics(doctor.getId(), date);
            list.add(statistics);
        });
        return list;
    }

    private String getLastName(String fullName) {
        return fullName.split(" ")[0];
    }

    public PatientDTO getPatientStatistics(String patientId, LocalDate begin, LocalDate end) {

        PatientDTO statistics = new PatientDTO();
        statistics.setName(patientService.getPatient(patientId).getPerson().getFullName());
        statistics.setBalance(patientService.getPatient(patientId).getBalance());

        List<Talon> talons = talonService.getAllTalonsForPatient(patientId).stream()
            .filter(talon -> talon.getProcedure().getId() > 3)
            .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
            .collect(Collectors.toList());

        LocalDate start = talons.stream().map(talon -> talon.getDate())
            .min(Comparator.comparing(LocalDate::toEpochDay)).orElse(null);
        statistics.setStart(start);

        LocalDate finish = talons.stream().map(talon -> talon.getDate())
            .max(Comparator.comparing(LocalDate::toEpochDay)).orElse(null);
        statistics.setFinish(finish);

        int days = (int) talons.stream().filter(talon -> talon.getProcedure().getId() > 3)
            .map(talon -> talon.getDate()).distinct().count();
        statistics.setDays(days);

        int procedures = (int) talons.size();
        statistics.setProcedures(procedures);

        int zones = talons.stream().mapToInt(talon -> talon.getZones()).sum();
        statistics.setZones(zones);

        List<Accounting> accountings = patientService
            .getUltimateBalance(patientId, begin.minusDays(1), end.plusDays(1));

        int bill = accountings.stream().filter(accounting -> (
        		accounting.getPayment().equals(PaymentType.PROC)
             || accounting.getPayment().equals(PaymentType.HOTEL) ))
                .mapToInt(Accounting::getSum).sum();
        statistics.setBill(bill*(-1));

        int cash = accountings.stream().filter(accounting ->
        	accounting.getPayment().equals(PaymentType.CASH))
                .mapToInt(Accounting::getSum).sum();
        statistics.setCash(cash);

        int card = accountings.stream().filter(accounting -> 
        	accounting.getPayment().equals(PaymentType.CARD) )
            .mapToInt(Accounting::getSum).sum();
        statistics.setCard(card);

        int discount = accountings.stream().filter(accounting ->
            accounting.getPayment().equals(PaymentType.DISCOUNT) )
            .mapToInt(Accounting::getSum).sum();
        statistics.setDiscount(discount);

        statistics.setDebt(cash + card + discount + bill);

        return statistics;
    }

    public List<PatientDTO> getPatientsStatistics( LocalDate start, LocalDate finish) {

    	List<PatientDTO> list = new ArrayList<>();

        List<Accounting> acs =
            accountingService.getByDateBetween(start.minusDays(1), finish.plusDays(1));
        
        List<String> uniquePatientIds = acs.stream()
            .map(accounting -> accounting.getPatientId())
            .distinct().collect(Collectors.toList());
        
        List<Patient> patients = uniquePatientIds.stream()
            .map(id-> patientService.getPatient(id)).collect(Collectors.toList());

        for (Patient patient:patients) {
            list.add(this.getPatientStatistics(patient.getId(), start, finish));
        }

        return list;
    }
}
