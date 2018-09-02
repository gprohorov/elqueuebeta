package com.med.services.accounting.impls;

import com.med.model.Patient;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.accounting.AvailableexecutedPart;
import com.med.repository.accounting.AccountingRepository;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.med.services.accounting.impls.AccountingServiceImplTest.TestConstants.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountingServiceImplTest {
    interface TestConstants {
        List<Accounting> EMPTY_LIST = new ArrayList<>();
        Patient PATIENT = new Patient("id", null, null, Collections.singletonList(new Talon()),
                LocalDateTime.of(2018, Month.SEPTEMBER, 2, 17, 15, 52),
                LocalDateTime.of(2018, Month.SEPTEMBER, 2, 17, 15, 52), Status.FOREIGN);
        Long SUM = 5L;
        String PATIENT_ID = "patientId";
        Accounting ACCOUNTING = new Accounting(null, PATIENT_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 17, 15, 52),
                null, SUM.intValue(), null, null);
        List<Accounting> ACCOUNTINGS = Collections.singletonList(
                new Accounting(null, null, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 17, 15, 52), null, SUM.intValue(),
                        PaymentType.CASH, null));
        String ACCOUNTING_ID = "accountingId";
    }

    @MockBean
    PatientServiceImpl patientService;
    @MockBean
    TalonServiceImpl talonService;
    @MockBean
    AccountingRepository repository;
    @Autowired
    AccountingServiceImpl accountingService;

    @Before
    public void setUp() {
        when(repository.save(any(Accounting.class))).then(returnsFirstArg());
        when(repository.saveAll(any())).thenReturn(ACCOUNTINGS);
        ACCOUNTING.setId(ACCOUNTING_ID);
    }

    @Test
    public void testCreateAccounting() {
        when(patientService.savePatient(any(Patient.class))).then(returnsFirstArg());
        when(patientService.getPatient(anyString())).thenReturn(PATIENT);

        Accounting result = accountingService.createAccounting(ACCOUNTING);
        Accounting expected = ACCOUNTING;
        Assert.assertEquals(expected.getPatientId(), result.getPatientId());
    }

    @Test
    public void testSaveAll() {
        List<Accounting> result = accountingService.saveAll(ACCOUNTINGS);
        Assert.assertEquals(ACCOUNTINGS, result);
    }

    @Test
    public void testGetAccounting() {
        when(repository.findById(ACCOUNTING_ID)).thenReturn(Optional.of(ACCOUNTING));
        Accounting result = accountingService.getAccounting(ACCOUNTING_ID);
        Assert.assertEquals(ACCOUNTING, result);
    }

    @Test
    public void testGetAll() {
        when(repository.findAll()).thenReturn(ACCOUNTINGS);
        List<Accounting> result = accountingService.getAll();
        Assert.assertEquals(ACCOUNTINGS, result);
    }

    @Test
    public void testDeleteAll() {
        accountingService.deleteAll();
    }

    @Test
    public void testGetAllIncomesForPatientFromTo() {
        when(repository.findByPatientIdAndDateBetween(anyString(), any(), any())).thenReturn(ACCOUNTINGS);

        List<Accounting> result = accountingService.getAllIncomesForPatientFromTo(PATIENT_ID,
                LocalDate.of(2018, Month.SEPTEMBER, 2), LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(ACCOUNTINGS, result);
    }

    @Test
    public void testGetASum() {
        Accounting accounting = new Accounting();
        accounting.setSum(SUM.intValue());
        when(repository.findAll()).thenReturn(Collections.singletonList(accounting));
        Long result = accountingService.getASum();
        Assert.assertEquals(SUM, result);
    }

    @Test
    public void testGetAllForDate() {
        List<Accounting> expected = Collections.singletonList(
                new Accounting(0, PATIENT_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 17, 15, 52), "talonId", 0,
                        PaymentType.CASH, "desc"));
        when(repository.findByDate(any())).thenReturn(expected);

        List<Accounting> result = accountingService.getAllForDate(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(expected.size(), result.size());
    }

    @Test
    public void testGetSumForEmptyDateTotal() {
        when(repository.findByDate(any())).thenReturn(EMPTY_LIST);

        Long result = accountingService.getSumForDateTotal(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(Long.valueOf(0), result);
    }

    @Test
    public void testGetSumForValidDateTotal() {
        when(repository.findByDate(any())).thenReturn(ACCOUNTINGS);

        Long result = accountingService.getSumForDateTotal(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(SUM, result);
    }

    @Test
    public void testGetSumForDateCash() {
        when(repository.findByDate(any())).thenReturn(ACCOUNTINGS);

        Long result = accountingService.getSumForDateCash(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(SUM, result);
    }

    @Test
    public void testGetCurrentReport() {
        when(patientService.getAllForToday()).thenReturn(Collections.singletonList(PATIENT));
        when(repository.findByDate(any())).thenReturn(ACCOUNTINGS);

        AvailableexecutedPart result = accountingService.getCurrentReport();
        AvailableexecutedPart expected = new AvailableexecutedPart(5L, 0L, 0);
        Assert.assertEquals(expected.getAvailable(), result.getAvailable());
    }

    @Test
    public void testGetSumFrom() {
        Integer result = accountingService.getSumlFrom(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testGetSumForPatientFrom() {
        Integer result = accountingService.getSumlForPatientFrom(PATIENT_ID, LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testGetSumForPatient() {
        Integer result = accountingService.getSumlForPatient(PATIENT_ID);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @TestConfiguration
    static class AccountingServiceTestContextConfiguration {
        @Bean
        public AccountingServiceImpl accountingService() {
            return new AccountingServiceImpl();
        }
    }
}

