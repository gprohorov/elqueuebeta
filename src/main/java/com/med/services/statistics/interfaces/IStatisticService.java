package com.med.services.statistics.interfaces;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by george on 20.07.18.
 */
public interface IStatisticService {
    Long getCashAvailable();
    Map<String, Integer> getDoctorsProceduresFromTo(LocalDate start, LocalDate finish);
    Long getAllProceduresCount();
    Long getAllPatientsCount();
    Long getAllDebtors();
    Long getTotalCash();


}
