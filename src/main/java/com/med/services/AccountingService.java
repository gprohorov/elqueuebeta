package com.med.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.balance.ProcedureZonesSum;
import com.med.model.balance.ReceiptToday;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.model.statistics.dto.patient.DebetorDTO;
import com.med.repository.AccountingRepository;

@Service
public class AccountingService {
	
	@Autowired
	CashBoxService cashBoxService;

    @Autowired
    PatientService patientService;

    @Autowired
    AccountingRepository repository;

    public Accounting createAccounting(Accounting accounting) {
        repository.save(accounting);
        Patient patient = patientService.getPatient(accounting.getPatientId());
        patient.setBalance(patient.getBalance() + accounting.getSum());
        patientService.savePatient(patient);
        return accounting;
    }

    public List<Accounting> saveAll(List<Accounting> accountings) {
        for (Accounting accounting:accountings) {
            this.createAccounting(accounting);
        }
        return accountings;
    }

    public Accounting getAccounting(String accountingId) {
        return repository.findById(accountingId).orElse(null);
    }

    public List<Accounting> getAll() {
        return repository.findAll();
    }

    public void deleteAll() {
    	repository.deleteAll();
	}

    public List<Accounting> getByPatientId(String patientId) {
        return repository.findByPatientId(patientId);
    }

    public void deleteAll(List<Accounting> accountings) {
        repository.deleteAll(accountings);
    }

    public List<Accounting> getAllIncomesForPatientFromTo(
		String patientId, LocalDate start, LocalDate finish) {
    	return repository.findByPatientIdAndDateBetween(patientId, start, finish);
	}

    public Long getASum() {
        return repository.findAll().stream().mapToLong(Accounting::getSum).sum();
    }

    public List<Accounting> getAllForDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Accounting> getByDateBetween(LocalDate start, LocalDate finish) {
        return repository.findByDateBetween(start, finish);
    }

    public Long getSumForDateTotal(LocalDate date){
        return this.getAllForDate(date).stream()
            .filter(accounting -> accounting.getSum() > 0)
            .filter(accounting -> !accounting.getPayment().equals(PaymentType.DISCOUNT))
            .mapToLong(Accounting::getSum).sum();
    }

    public Long getSumForDateProcedures(LocalDate date){
        return this.getAllForDate(date).stream()
            .filter(accounting -> accounting.getSum() > 0)
            .filter(accounting -> !accounting.getPayment().equals(PaymentType.PROC))
            .mapToLong(Accounting::getSum).sum();
    }

    public Long getSumForDateDiscount(LocalDate date){
        return this.getAllForDate(date).stream()
            .filter(accounting -> accounting.getPayment().equals(PaymentType.DISCOUNT))
            .mapToLong(Accounting::getSum).sum();
    }


    public Long getSumForDateCash(LocalDate date) {
        return this.getAllForDate(date).stream()
            .filter(accounting -> accounting.getSum() > 0)
            .filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
            .mapToLong(Accounting::getSum).sum();
    }

    public Long getSumForDateCard(LocalDate date) {
        return this.getAllForDate(date).stream()
            .filter(accounting -> accounting.getSum() > 0)
            .filter(accounting -> accounting.getPayment().equals(PaymentType.CARD))
            .mapToLong(Accounting::getSum).sum();
    }

    public AvailableexecutedPart getCurrentReport() {

        List<Accounting> today = this.getAllForDate(LocalDate.now());

        AvailableexecutedPart report = new AvailableexecutedPart();

        int payed = (int) today.stream()
            .filter(accounting -> accounting.getSum()>0)
            .filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
            .mapToLong(Accounting::getSum).sum();
        report.setPayed(payed);

        int available = cashBoxService.getCashBox();
        report.setAvailable(available);
        
        report.setGiven(payed - available);

        int executed = (int) today.stream()
            .filter(accounting -> accounting.getSum() < 0)
            .mapToLong(Accounting::getSum).sum();
        report.setExecuted(executed);

        int people = patientService.getAllForToday().size();
        long gameover =  patientService.getAllForToday().stream()
            .filter(patient -> patient.getActivity().equals(Activity.GAMEOVER)).count();

        int part = (int) (gameover * 100) / people;

        report.setPercentage(part);

        return report;
    }

    public List<Accounting> getAllFrom(LocalDate date) {
        return this.getAll().stream()
            .filter(accounting -> accounting.getDateTime().toLocalDate().isAfter(date))
            .collect(Collectors.toList());
    }

    public List<DebetorDTO> getDebetorsExt(LocalDate start, LocalDate finish) {
        List<Accounting> acs = repository.findByDateBetween(start.minusDays(1), finish.plusDays(1));
        List<String> uniquePatientIds = acs.stream()
            .map(accounting -> accounting.getPatientId())
            .distinct().collect(Collectors.toList());
        List<Patient> debetors = uniquePatientIds.stream()
            .map(id-> patientService.getPatient(id))
            .filter(patient -> patient.getBalance() < 0)
            .collect(Collectors.toList());

        List<DebetorDTO> list = new ArrayList<>();
        List<Accounting> accountings;

        for (Patient debetor:debetors) {

            DebetorDTO dto = new DebetorDTO();
            dto.setPatient(debetor);

            accountings = repository.findByPatientId(debetor.getId());

            dto.setStart(start);
            dto.setFinish(finish);

            Accounting paymentAccounting = accountings.stream()
                .filter( accounting -> accounting.getPayment().equals(PaymentType.CASH)
                    || accounting.getPayment().equals(PaymentType.CARD))
                .max(Comparator.comparing(Accounting::getDate)).orElse(null);
            LocalDate lastPayment = (paymentAccounting==null) ? null : paymentAccounting.getDate();
            dto.setLastPaymentDate(lastPayment);

            Integer bill = accountings.stream().filter(accounting ->
                    accounting.getPayment().equals(PaymentType.PROC)
                 || accounting.getPayment().equals(PaymentType.HOTEL))
                .mapToInt(Accounting::getSum).sum();
            dto.setBill(bill);

            Integer payment = accountings.stream().filter(accounting ->
                    accounting.getPayment().equals(PaymentType.CASH)
                 || accounting.getPayment().equals(PaymentType.CARD))
                .mapToInt(Accounting::getSum).sum();
            dto.setPayment(payment);

            dto.setDebt(bill + payment);
            list.add(dto);
        }

        return list.stream().sorted(Comparator.comparing(DebetorDTO::getDebt))
            .collect(Collectors.toList());
    }

    public ReceiptToday getTodayReceipt(String patientId) {
        ReceiptToday receipt = new ReceiptToday();
        Patient patient = patientService.getPatient(patientId);
        List<ProcedureZonesSum> list = new ArrayList<>();
        List<Accounting> accountings = this.getAllIncomesForPatientFromTo(
    		patientId, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        receipt.setDate(LocalDate.now());
        receipt.setPatientId(patientId);
        receipt.setPatientName(patient.getPerson().getFullName());
        accountings.stream().filter(accounting -> accounting.getSum() < 0).forEach(accounting -> {
            list.add( new ProcedureZonesSum(accounting.getDesc(), accounting.getSum()));
        });
        receipt.setList(list);
        int sum = list.stream().mapToInt(ProcedureZonesSum::getSum).sum();
        receipt.setProceduresSum(sum);
        receipt.setPayed(accountings.stream().filter(accounting -> accounting.getSum() > 0)
    		.mapToInt(Accounting::getSum).sum());
        receipt.setBalance(patient.getBalance());
    	receipt.setDiscount(patient.getDiscount());
        return receipt;
    }
}