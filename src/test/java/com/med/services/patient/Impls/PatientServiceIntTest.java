package com.med.services.patient.Impls;

import com.med.model.Patient;
import com.med.model.Person;
import com.med.model.Status;
import com.med.model.Talon;
import com.med.model.Therapy;
import com.med.repository.accounting.AccountingRepository;
import com.med.repository.patient.PatientRepository;
import com.med.repository.person.PersonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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

        Talon DEFAULT_TALON_1 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_1, null, 1, null,
            null, null, 1);
        Talon DEFAULT_TALON_2 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_2, null, 1, null,
            null, null, 1);
        Talon DEFAULT_TALON_3 = new Talon(null, TestConstants.DEFAULT_ID, TALON_DATE_3, null, 1, null,
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
    public void setUpCorrect() {
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isNotNull();
        assertThat(patient.getId()).isEqualTo(TestConstants.DEFAULT_ID);
    }

    @Test
    public void passNullToSaveAndCatchThrowable() {
        final Throwable thrown = catchThrowable(() -> service.savePatient(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void savePatientAndRegistrationIsNotNull() {
        final Optional<Patient> maybePatient = Optional.ofNullable(service.savePatient(patient));

        assertThat(maybePatient).isPresent();
        assertThat(maybePatient.orElse(patient).getRegistration()).isNotNull();
        assertThat(maybePatient.orElse(patient).getRegistration()).isBefore(LocalDateTime.now());
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

    @TestConfiguration
    static class PatientServiceTestContextConfiguration {
        @Bean
        public PatientServiceImpl patientService() {
            return new PatientServiceImpl();
        }
    }
}