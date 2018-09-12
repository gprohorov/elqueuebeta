package com.med.services.patient.Impls;

import com.med.model.*;
import com.med.repository.accounting.AccountingRepository;
import com.med.repository.patient.PatientRepository;
import com.med.repository.person.PersonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
public class PatientServiceIntTest {

    @MockBean
    private PatientRepository repository;

    @MockBean
    private AccountingRepository accountingRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private TalonServiceImpl talonService;

    @MockBean
    private AccountingServiceImpl accountingService;

    @MockBean
    private RecordServiceImpl recordService;

    @MockBean
    private TherapyServiceImpl therapyService;

    @MockBean
    private ProcedureServiceImpl procedureService;

    @Autowired
    private PatientServiceImpl service;

    private Patient patient;

    private interface TestConstants {

        String BAD_ID = "-1";

        String DEFAULT_ID = "1";

        Person DEFAULT_PERSON = new Person(
            "Yaroslav Zhyravov", "+380666666666", "Ivano-Frankivs'k", "N/A", true, LocalDate.parse("2018-11-12"));

        Therapy DEFAULT_THERAPY = null;

        List<Talon> DEFAULT_TALONS = new ArrayList<>();

        Status DEFAULT_STATUS = Status.VIP;

        LocalDate TALON_DATE_1 = LocalDate.now();

        LocalDate TALON_DATE_2 = LocalDate.now().minusMonths(2);

        LocalDate TALON_DATE_3 = LocalDate.now().plusDays(3);

        Procedure DEFAULT_PROCEDURE = new Procedure();

        Talon DEFAULT_TALON_1 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_1, DEFAULT_PROCEDURE, 1, null,
            null, null, 1);

        Talon DEFAULT_TALON_2 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_2, DEFAULT_PROCEDURE, 1, null,
            null, null, 1);

        Talon DEFAULT_TALON_3 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_3, DEFAULT_PROCEDURE, 1, null,
            null, null, 1);
    }

    public static Patient createEntity() {
        return new Patient(TestConstants.DEFAULT_ID, TestConstants.DEFAULT_PERSON, TestConstants.DEFAULT_THERAPY,
            TestConstants.DEFAULT_TALONS, null, null, TestConstants.DEFAULT_STATUS);
    }

    @Before
    public void setUp() throws Exception {
        patient = PatientServiceIntTest.createEntity();

        Mockito.when(repository.save(any(Patient.class)))
            .then(returnsFirstArg());

        Mockito.when(repository.findById(TestConstants.DEFAULT_ID))
            .thenReturn(Optional.of(patient));

        Mockito.when(talonService.getTalonsForToday())
            .thenReturn(Lists.list(TestConstants.DEFAULT_TALON_1, TestConstants.DEFAULT_TALON_2,
                TestConstants.DEFAULT_TALON_3));

        Mockito.when(talonService.getTalonForTodayForPatientForProcedure(eq(TestConstants.DEFAULT_ID), any(Integer.class)))
            .thenReturn(TestConstants.DEFAULT_TALON_1);

        repository.save(patient);
    }

    @Test
    public void setUpIsCorrect() {
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isNotNull();
        assertThat(patient.getId()).isEqualTo(TestConstants.DEFAULT_ID);
    }

    @Test
    public void registerPatientIsCorrect() {
        final Optional<Patient> resultActivated
            = Optional.ofNullable(service.registratePatient(patientToRegDto(this.patient, true)));
        final Optional<Patient> resultNotActivated
            = Optional.ofNullable(service.registratePatient(patientToRegDto(this.patient, false)));

        assertThat(resultActivated).isPresent();
        assertThat(resultActivated.get().getRegistration()).isNotNull();
        assertThat(resultActivated.get().getRegistration()).isBeforeOrEqualTo(LocalDateTime.now());

        assertThat(resultNotActivated).isPresent();
        assertThat(resultNotActivated.get().getRegistration()).isNotNull();
        assertThat(resultNotActivated.get().getRegistration()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    /**
     * It is just an example how to catch and assert Throwable.
     *
     * For now, you have the worst service layer logic I've seen ever.
     * So if you don't want to be confused why the project cannot be built, don't remove `@Ignore`.
     *
     * And yes, it fails until you fix your service layer checkings.
     */
    @Ignore
    @Test
    public void passNullToSaveAndCatchThrowable() {
        final Throwable thrown = catchThrowable(() -> service.savePatient(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void getPatientWithBadIdAndGetNull() {
        final Optional<Patient> maybePatient = Optional.ofNullable(service.getPatient(TestConstants.BAD_ID));

        assertThat(maybePatient).isNotPresent();
    }

    @Test
    public void getPatientAndItIsNotNullWithRightId() {
        final Optional<Patient> maybePatient = Optional.ofNullable(service.getPatient(TestConstants.DEFAULT_ID));

        assertThat(maybePatient).isPresent();
        assertThat(maybePatient.orElse(patient).getId()).isEqualTo(TestConstants.DEFAULT_ID);
    }

    @Test
    public void getPatientWithTalonsAndTalonsAreNotEmpty() {
        final Optional<Patient> maybePatient = Optional.ofNullable(service.getPatientWithTalons(TestConstants.DEFAULT_ID));

        assertThat(maybePatient).isPresent();
        assertThat(maybePatient.orElse(patient).getTalons()).isNotNull();
        assertThat(maybePatient.orElse(patient).getTalons()).isNotEmpty();
    }

    @Test
    public void getPatientWithOneTalonAndItEqualsDefaultTalon1() {
        final Optional<Patient> maybePatient =
            Optional.ofNullable(service.getPatientWithOneTalon(TestConstants.DEFAULT_ID, 0));

        assertThat(maybePatient).isPresent();
        assertThat(maybePatient.orElse(patient).getTalons()).isNotNull();
        assertThat(maybePatient.orElse(patient).getTalons()).isNotEmpty();
        assertThat(maybePatient.orElse(patient).getTalons().get(0)).isEqualTo(TestConstants.DEFAULT_TALON_1);
    }

    @Test
    public void patientByeIsReallyBye() {
        patient.getTalons().addAll(Arrays.asList(
            TestConstants.DEFAULT_TALON_1,
            TestConstants.DEFAULT_TALON_2,
            TestConstants.DEFAULT_TALON_3));
        repository.save(patient);

        final Optional<Patient> byedPatient = Optional.ofNullable(service.patientBye(TestConstants.DEFAULT_ID));

        assertThat(byedPatient).isPresent();
        assertThat(byedPatient.get().getLastActivity()).isNull();
        assertThat(byedPatient.get().getStartActivity()).isNull();
        assertThat(byedPatient.get().getTalons()).isNotEmpty();
        byedPatient.get().getTalons().forEach(talon -> assertThat(Activity.CANCELED).isEqualTo(talon.getActivity()));
    }

    private PatientRegDTO patientToRegDto(final Patient patient, boolean isActivate) {
        if (patient == null) {
            return null;
        }
        return new PatientRegDTO(patient.getPerson(), 0, "2018-09-07", isActivate, 0);
    }

    @TestConfiguration
    static class PatientServiceTestContextConfiguration {

        @Bean
        public PatientServiceImpl patientService() {
            return new PatientServiceImpl();
        }
    }
}