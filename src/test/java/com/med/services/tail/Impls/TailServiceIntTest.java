package com.med.services.tail.Impls;

import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.repository.procedure.ProcedureRepository;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.patient.Impls.PatientServiceIntTest;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.user.UserService;
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
import sun.print.resources.serviceui;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 9/8/18 at 1:27 PM
 */
@RunWith(SpringRunner.class)
public class TailServiceIntTest {

    @MockBean
    ProcedureServiceImpl procedureService;

    @MockBean
    PatientServiceImpl patientService;

    @MockBean
    TalonServiceImpl talonService;

    @MockBean
    UserService userService;

    @Autowired
    TailServiceImpl service;

    private interface TestConstants {

        int DEFAULT_PROCEDURE_FOREIGN_ID = 1;

        Procedure DEFAULT_PROCEDURE_FOREIGN = new Procedure(DEFAULT_PROCEDURE_FOREIGN_ID, "Test1", 0, 1, 0, 0, 0, 0, false, null);

        int DEFAULT_PROCEDURE_VIP_ID = 2;

        Procedure DEFAULT_PROCEDURE_VIP = new Procedure(DEFAULT_PROCEDURE_VIP_ID, "Test1", 0, 0, 1, 0, 0, 0, false, null);

        int DEFAULT_PROCEDURE_BUSINESS_ID = 3;

        Procedure DEFAULT_PROCEDURE_BUSINESS = new Procedure(DEFAULT_PROCEDURE_BUSINESS_ID, "Test1", 0, 0, 0, 1, 0, 0, false, null);

        int DEFAULT_PROCEDURE_ALL_INCLUSIVE_ID = 4;

        Procedure DEFAULT_PROCEDURE_ALL_INCLUSIVE = new Procedure(DEFAULT_PROCEDURE_ALL_INCLUSIVE_ID, "Test1", 0, 0, 0, 0, 1, 0, false, null);

        int DEFAULT_PROCEDURE_ALL_SOCIAL_ID = 5;

        Procedure DEFAULT_PROCEDURE_ALL_SOCIAL = new Procedure(DEFAULT_PROCEDURE_ALL_SOCIAL_ID, "Test1", 0, 0, 0, 0, 0, 1, false, null);
    }

    @Before
    public void setUp() throws Exception {
        Mockito.when(procedureService.getAll())
            .thenReturn(Arrays.asList(
                TestConstants.DEFAULT_PROCEDURE_FOREIGN,
                TestConstants.DEFAULT_PROCEDURE_VIP,
                TestConstants.DEFAULT_PROCEDURE_BUSINESS,
                TestConstants.DEFAULT_PROCEDURE_ALL_INCLUSIVE,
                TestConstants.DEFAULT_PROCEDURE_ALL_SOCIAL));
    }

    @Test
    public void testSetSemaforSignal() {
        service.setSemaforSignal(TestConstants.DEFAULT_PROCEDURE_BUSINESS_ID, true);
        assertThat(service.getSemaforSignal(TestConstants.DEFAULT_PROCEDURE_BUSINESS_ID)).isTrue();

        service.setSemaforSignal(TestConstants.DEFAULT_PROCEDURE_VIP_ID, false);
        assertThat(service.getSemaforSignal(TestConstants.DEFAULT_PROCEDURE_VIP_ID)).isFalse();
    }

    @TestConfiguration
    static class TailServiceTestContextConfiguration {

        @Bean
        public TailServiceImpl tailService() {
            return new TailServiceImpl();
        }
    }
}