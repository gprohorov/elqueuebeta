package com.med.services.talon.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.balance.Receipt;
import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.card.impls.CardServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import com.med.services.workplace.impls.WorkPlaceServiceImpl;
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

import static com.med.services.talon.impls.TalonServiceImplTest.TestConstants.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
public class TalonServiceImplTest {

    @MockBean
    TalonRepository repository;

    @MockBean
    PatientServiceImpl patientService;

    @MockBean
    ProcedureServiceImpl procedureService;

    @MockBean
    DoctorServiceImpl doctorService;

    @MockBean
    TherapyServiceImpl therapyService;

    @MockBean
    WorkPlaceServiceImpl workPlaceService;

    @MockBean
    AccountingServiceImpl accountingService;

    @MockBean
    CardServiceImpl cardService;

    @Autowired
    TalonServiceImpl talonService;

    interface TestConstants {
        String TALON_ID = "id";
        String PATIENT_ID = "patientId";
        Therapy THERAPY = new Therapy(PATIENT_ID, "diag", "codeDiag", "notes", Collections.singletonList(
                new Assignment(0, "desc", new ArrayList<>(
                        Collections.singletonList(new ArrayList<>(Collections.singletonList("ArrayList")))))), 0);
        int VALID_PROCEDURE_ID = 1;
        int WRONG_PROCEDURE_ID = 9999;
        int DAYS = 2;
        LocalDate DATE = LocalDate.parse("2018-08-13");
        List<Doctor> DOCTORS = Collections.singletonList(
                new Doctor("fullName", "speciality", "cellPhone", Collections.singletonList(0), "userId"));
        Procedure PROCEDURE = new Procedure("name", 0, 0, 0, 0, 0, 0, true, 0,
                new Card(0, 0, "name", 0, true, Collections.singletonList(0), Collections.singletonList(0),
                        Collections.singletonList(0)), ProcedureType.COMMON);
        List<Procedure> PROCEDURES = Collections.singletonList(PROCEDURE);
        Patient PATIENT = new Patient(TALON_ID, null, null, null,
                LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20), null, Status.FOREIGN);
        Accounting ACCOUNTING = new Accounting(0, PATIENT_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20),
                "talonId", 0, PaymentType.CASH, "desc");
        Talon TALON = new Talon(TALON_ID, PATIENT_ID, LocalDate.of(2018, Month.SEPTEMBER, 2), PROCEDURE, 0, "desc",
                LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20),
                new Doctor("fullName", "speciality", "cellPhone", Collections.singletonList(0), "userId"), 0);
        Talon TALON1 = new Talon(null, PATIENT_ID, LocalDate.of(2018, Month.SEPTEMBER, 2), PROCEDURE, 1, "", null, null,
                0);
        Talon TALON2 = new Talon(null, PATIENT_ID, null, PROCEDURE, 0, null, null, null, 0);
        List<Talon> TALONS = Collections.singletonList(TALON);
        List<Patient> PATIENTS = Collections.singletonList(
                new Patient(TALON_ID, null, null, TALONS, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20),
                        LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20), Status.FOREIGN));
        List<Talon> TALONS_ON_PROCEDURE = Collections.singletonList(TALON2);

    }

    @Before
    public void setUp() {
        Procedure validProcedure = new Procedure("UKOL");
        Procedure badProcedure = null;
        //valid id of procedure
        when(procedureService.getProcedure(VALID_PROCEDURE_ID)).thenReturn(validProcedure);
        //not valid id of procedure
        when(procedureService.getProcedure(WRONG_PROCEDURE_ID)).thenReturn(badProcedure);

        TALON2.setActivity(Activity.ON_PROCEDURE);
        //save any Talon and return it
        when(repository.save(any(Talon.class))).then(returnsFirstArg());
        when(repository.saveAll(any())).thenReturn(TALONS);

        Patient patient = new Patient();
        patient.setId(PATIENT_ID);
        when(patientService.getPatient(PATIENT_ID)).thenReturn(patient);
        when(patientService.savePatient(any())).then(returnsFirstArg());
        when(repository.findAll()).thenReturn(TALONS);
        when(repository.findById(TALON_ID)).thenReturn(Optional.of(TALON));
        when(repository.findByDate(any())).thenReturn(TALONS);
        when(patientService.getPatient(anyString())).thenReturn(PATIENT);
    }

    @Test
    public void createTalonWithDaysAndValidProcedureId() {
        Procedure procedure = procedureService.getProcedure(VALID_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, procedure, DAYS);

        Talon actualResult = talonService.createTalon(PATIENT_ID, VALID_PROCEDURE_ID, DAYS);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createTalonWithDateAndValidProcedureId() {
        Procedure procedure = procedureService.getProcedure(VALID_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, procedure, DATE);

        Talon actualResult = talonService.createTalon(PATIENT_ID, VALID_PROCEDURE_ID, DATE);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createTalonWithDaysAndWrongProcedureId() {
        Procedure procedure = procedureService.getProcedure(WRONG_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, procedure, DAYS);

        Talon actualResult = talonService.createTalon(PATIENT_ID, WRONG_PROCEDURE_ID, DAYS);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createTalonWithDateAndWrongProcedureId() {
        Procedure procedure = procedureService.getProcedure(WRONG_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, procedure, DATE);

        Talon actualResult = talonService.createTalon(PATIENT_ID, WRONG_PROCEDURE_ID, DATE);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createActiveTalonForValidPatientAndValidProcedure() {
        Procedure validProcedure = procedureService.getProcedure(VALID_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, validProcedure, DATE);
        expectedResult.setActivity(Activity.ACTIVE);

        Talon actualResult = talonService.createActiveTalon(PATIENT_ID, VALID_PROCEDURE_ID, DATE, 1, true);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createActiveTalonForValidPatientAndNotValidProcedure() {
        Procedure wrongProcedure = procedureService.getProcedure(WRONG_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, wrongProcedure, DATE);
        expectedResult.setActivity(Activity.ACTIVE);

        Talon actualResult = talonService.createActiveTalon(PATIENT_ID, WRONG_PROCEDURE_ID, DATE, 1, true);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createNotActiveTalonForValidProcedure() {
        //Actually can be tested by this(same code)
        createTalonWithDateAndValidProcedureId();
    }

    @Test
    public void createNotActiveTalonForNotValidProcedure() {
        //Actually can be tested by this(same code)
        createTalonWithDateAndWrongProcedureId();
    }

    @Test
    public void testCreateTalon() {
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);

        Talon result = talonService.createTalon(PATIENT_ID, 0, 0);
        Assert.assertEquals(TALON1, result);
    }

    @Test
    public void testSaveTalon() {
        Talon result = talonService.saveTalon(TALON);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testCreateTalon2() {
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);

        Talon result = talonService.createTalon(PATIENT_ID, 0, LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(TALON1.getPatientId(), result.getPatientId());
    }

    @Test
    public void testCreateTalon3() {
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);

        Talon result = talonService.createTalon(PATIENT_ID, 0, LocalDate.of(2018, Month.SEPTEMBER, 2), 0);
        Assert.assertEquals(TALON1, result);
    }

    @Test
    public void testCreateActiveTalon() {
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);

        Talon result = talonService.createActiveTalon(PATIENT_ID, 0, LocalDate.of(2018, Month.SEPTEMBER, 2), 0,
                Boolean.TRUE);
        Talon expected = new Talon(null, PATIENT_ID, LocalDate.of(2018, Month.SEPTEMBER, 2), PROCEDURE, 1, "", null,
                null, 0);
        Assert.assertEquals(expected.getPatientId(), result.getPatientId());
    }

    @Test
    public void testGetAll() {
        List<Talon> result = talonService.getAll();
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetTalon() {
        Talon result = talonService.getTalon(TALON_ID);
        Assert.assertEquals(TALON.getId(), result.getId());
    }

    @Test
    public void testGetTalonByProcedure() {

        Talon result = talonService.getTalonByProcedure(PATIENT_ID, 0, Activity.NON_ACTIVE);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testFindAll() {
        List<Talon> result = talonService.findAll();
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetTalonByPatient() {
        Talon result = talonService.getTalonByPatient(PATIENT_ID, Activity.NON_ACTIVE);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testGetTalonByDoctor() {
        Talon result = talonService.getTalonByDoctor(PATIENT_ID, 0, Activity.NON_ACTIVE);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testGetTalonsForToday() {
        List<Talon> result = talonService.getTalonsForToday();
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetTalonForTodayForPatientForProcedure() {
        when(repository.findByDateAndPatientId(any(), anyString())).thenReturn(TALONS_ON_PROCEDURE);

        Talon result = talonService.getTalonForTodayForPatientForProcedure(PATIENT_ID, 0);
        Assert.assertEquals(TALON2, result);
    }

    @Test
    public void testGetAllTalonsForPatient() {
        when(repository.findByPatientId(anyString())).thenReturn(TALONS);

        List<Talon> result = talonService.getAllTalonsForPatient(PATIENT_ID);
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetTalonsForDate() {
        List<Talon> result = talonService.getTalonsForDate(LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetAllTallonsBetween() {
        when(repository.findByDateBetween(any(), any())).thenReturn(TALONS);

        List<Talon> result = talonService.getAllTallonsBetween(LocalDate.of(2018, Month.SEPTEMBER, 2),
                LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testSetActivity() {


        Talon result = talonService.setActivity(TALON_ID, Activity.NON_ACTIVE);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testSetAllActivity() {
        when(procedureService.getFreeProcedures()).thenReturn(Collections.singletonList(0));

        talonService.setAllActivity(PATIENT_ID, Activity.NON_ACTIVE);
        talonService.setAllActivity(PATIENT_ID, Activity.NON_ACTIVE);
    }

    @Test
    public void testToPatientList() {
        List<Patient> result = talonService.toPatientList(TALONS);
        Assert.assertEquals(PATIENTS, result);
    }

    @Test
    public void testSaveTalons() {
        List<Talon> result = talonService.saveTalons(TALONS);
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testGetAllExecutedTalonsForPatientFromTo() {
        List<Talon> result = talonService.getAllExecutedTalonsForPatientFromTo(PATIENT_ID,
                LocalDate.of(2017, Month.SEPTEMBER, 2), LocalDate.of(2019, Month.SEPTEMBER, 2));
        Assert.assertEquals(Collections.EMPTY_LIST, result);
    }

    @Test
    public void testDeleteAll() {
        talonService.deleteAll(TALONS);
    }

    @Test
    public void testGetFilledProcedures() {
        when(procedureService.getAll()).thenReturn(PROCEDURES);

        List<Procedure> result = talonService.getFilledProcedures();
        Assert.assertEquals(PROCEDURES, result);
    }

    @Test
    public void testSetOutOfTurn() {
        Talon result = talonService.setOutOfTurn(TALON_ID, true);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testCreateTalonsForPatientToDateEmpty() {
        when(repository.findByDateAndPatientId(any(), anyString())).thenReturn(TALONS);
        when(patientService.getPatientWithTalons(anyString())).thenReturn(PATIENT);
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);
        when(therapyService.findTheLastTherapy(anyString())).thenReturn(THERAPY);
        when(therapyService.generateTalonsByTherapyToDate(any(), any())).thenReturn(TALONS);

        List<Talon> result = talonService.createTalonsForPatientToDate(PATIENT_ID,
                LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testCreateTalonsForPatientToDate() {
        when(repository.findByDateAndPatientId(any(), anyString())).thenReturn(TALONS);
        when(repository.findByPatientIdAndDateGreaterThan(anyString(), any())).thenReturn(TALONS);
        when(patientService.getPatientWithTalons(anyString())).thenReturn(
                new Patient(null, null, null, null, LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20),
                        LocalDateTime.of(2018, Month.SEPTEMBER, 2, 19, 16, 20), null));
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);
        when(therapyService.findTheLastTherapy(anyString())).thenReturn(THERAPY);
        when(therapyService.generateTalonsByTherapyToDate(any(), any())).thenReturn(TALONS);

        List<Talon> result = talonService.createTalonsForPatientToDate(PATIENT_ID,
                LocalDate.of(2018, Month.SEPTEMBER, 2), 0);
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testRemoveTalonsForPatientToDate() {
        when(repository.findByDateAndPatientId(any(), anyString())).thenReturn(TALONS);

        List<Talon> result = talonService.removeTalonsForPatientToDate(PATIENT_ID,
                LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(TALONS, result);
    }

    @Test
    public void testQuickExecute() {
        when(procedureService.getProcedure(anyInt())).thenReturn(PROCEDURE);
        when(doctorService.getAll()).thenReturn(DOCTORS);
        when(accountingService.createAccounting(any())).thenReturn(ACCOUNTING);
        TALON.getProcedure()
             .setId(3);
        Talon result = talonService.quickExecute(TALON_ID);
        TALON.getProcedure()
             .setId(0);
        Assert.assertEquals(TALON, result);
    }

    @Test
    public void testCreateReceipt() {
        when(repository.findByPatientIdAndDateGreaterThan(anyString(), any())).thenReturn(TALONS);
        when(accountingService.getAllIncomesForPatientFromTo(anyString(), any(), any())).thenReturn(
                Collections.singletonList(ACCOUNTING));

        Receipt result = talonService.createReceipt(PATIENT_ID, LocalDate.of(2018, Month.SEPTEMBER, 2),
                LocalDate.of(2018, Month.SEPTEMBER, 2));
        Assert.assertEquals(new Receipt().getSum(), result.getSum());
    }

    @TestConfiguration
    static class TalonServiceTestContextConfiguration {
        @Bean
        public TalonServiceImpl talonService() {
            return new TalonServiceImpl();
        }
    }

}