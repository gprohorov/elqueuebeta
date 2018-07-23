package com.med.services.statistics.impls;

import com.med.model.Activity;
import com.med.model.Patient;
import com.med.model.Talon;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.DoctorProcedureZoneFee;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.statistics.interfaces.IStatisticService;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Logger logger = LoggerFactory.getLogger(TailServiceImpl.class);

    @Override
    public Long getCashAvailable() {

        return accountingService.getSumForDateCash(LocalDate.now());
    }

    @Override
    public List<DoctorProcedureZoneFee> getDoctorsProceduresFromTo(LocalDate start, LocalDate finish) {

        List<Talon> talons = talonService.getAllTallonsBetween(start,finish);
        System.out.println(" all talons for the interval" + talons.size());
        List<DoctorProcedureZoneFee> result = new ArrayList<>();

        Map<String, List<Talon>> map =  talons.stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.groupingBy(talon->talon.getDoctor().getFullName()));
        logger.info(">>>>  map size  >>>>>>>> " + map.size());




    //    talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
     //           .collect(Collectors.groupingBy(Talon::getDoctor))
            map    .entrySet().stream().forEach(entry->{

            DoctorProcedureZoneFee item = new DoctorProcedureZoneFee();
            item.setName(entry.getKey());
            item.setProceduraCount(entry.getValue().size());
            item.setZonesCount(entry.getValue().stream().mapToLong(Talon::getZones).sum());
            item.setFee(entry.getValue().stream().mapToLong(Talon::getSum).sum());
            result.add(item);

        });

        logger.info(">>>>  doctors summary   >>>>>>>> " + result.size());
                ;

        return result;
    }

    @Override
    public Long getAllProceduresCount() {
        Long before =0L;
        return Long.valueOf(talonService.getAllTallonsBetween(LocalDate.now().minusYears(10), LocalDate.now()).size()) + before;
    }

    @Override
    public Long getAllPatientsCount() {
        return Long.valueOf(patientService.getAll("").size());
    }

    @Override
    public List<Patient> getAllDebtors() {

        return patientService.getDebetors();
    }

    @Override
    public Long getTotalCash() {

        return accountingService.getAll().stream().filter(accounting -> accounting.getSum()>0)
                .filter(accounting -> !accounting.getPayment().equals(PaymentType.DISCOUNT))
                .mapToLong(Accounting::getSum).sum();
    }

    @Override
    public Long getPatientTotalSum(String patientId) {
        return null;
    }
}
