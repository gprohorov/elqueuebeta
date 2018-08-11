package com.med.services.talon.impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.card.impls.CardServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.junit.Assert;
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

import static com.med.services.talon.impls.TalonServiceImplTest.TestConstants.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

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
    CardServiceImpl cardService;

    @Autowired
    TalonServiceImpl talonService;

    interface TestConstants {
        String PATIENT_ID = "AaaAaaA";
        int VALID_PROCEDURE_ID = 1;
        int WRONG_PROCEDURE_ID = 9999;
        int DAYS = 2;
        LocalDate DATE = LocalDate.parse("2018-08-13");
    }

    @Before
    public void setUp() throws Exception {
        Procedure validProcedure = new Procedure("UKOL");
        Procedure badProcedure = null;
        //valid id of procedure
        Mockito.when(procedureService.getProcedure(VALID_PROCEDURE_ID))
               .thenReturn(validProcedure);
        //not valid id of procedure
        Mockito.when(procedureService.getProcedure(WRONG_PROCEDURE_ID))
               .thenReturn(badProcedure);

        //save any Talon and return it
        Mockito.when(repository.save(any(Talon.class)))
               .then(returnsFirstArg());

        Patient patient = new Patient();
        patient.setId(PATIENT_ID);
        Mockito.when(patientService.getPatient(PATIENT_ID))
               .thenReturn(patient);
        Mockito.when(patientService.savePatient(any(Patient.class)))
               .then(returnsFirstArg());
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

        Talon actualResult = talonService.createActiveTalon(PATIENT_ID, VALID_PROCEDURE_ID, DATE, true);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createActiveTalonForValidPatientAndNotValidProcedure() {
        Procedure wrongProcedure = procedureService.getProcedure(WRONG_PROCEDURE_ID);
        Talon expectedResult = new Talon(PATIENT_ID, wrongProcedure, DATE);
        expectedResult.setActivity(Activity.ACTIVE);

        Talon actualResult = talonService.createActiveTalon(PATIENT_ID, WRONG_PROCEDURE_ID, DATE, true);

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

    //TODO setActivity(..)
    //TODO setOutOfTurn(..)
    //TODO setAllActivity(..)
    //TODO skip talonService.createTalonsForPatientToDate() because not understand logic of it)
    @TestConfiguration
    static class TalonServiceTestContextConfiguration {
        @Bean
        public TalonServiceImpl talonService() {
            return new TalonServiceImpl();
        }
    }

}