package com.med.services.statistics.impls;

import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.statistics.interfaces.IStatisticService;
import com.med.services.talon.impls.TalonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by george on 20.07.18.
 */



@Service
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    TalonServiceImpl talonService;

    @Autowired
    AccountingServiceImpl accountingService;

    @Override
    public Long getCashAvailable() {




        return null;
    }

    @Override
    public Map<String, Integer> getDoctorsProceduresFromTo(LocalDate start, LocalDate finish) {
        return null;
    }

    @Override
    public Long getAllProceduresCount() {
        return null;
    }

    @Override
    public Long getAllPatientsCount() {
        return null;
    }

    @Override
    public Long getAllDebtors() {
        return null;
    }

    @Override
    public Long getTotalCash() {
        return null;
    }
}
