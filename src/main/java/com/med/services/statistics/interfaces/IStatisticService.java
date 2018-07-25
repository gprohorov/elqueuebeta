package com.med.services.statistics.interfaces;

import com.med.model.Patient;
import com.med.model.statistics.dto.doctor.DoctorProcedureZoneFee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 20.07.18.
 */
public interface IStatisticService {
    Long getCashAvailable();
    List<DoctorProcedureZoneFee> getDoctorsProceduresFromTo(LocalDate start, LocalDate finish);
    Long getAllProceduresCount();
    Long getAllPatientsCount();
    List<Patient> getAllDebtors();
    Long getTotalCash();
    Long getPatientTotalSum(String patientId);


}
