package com.med.services.statistics.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.doctor.DoctorPercent;
import com.med.model.statistics.dto.doctor.DoctorProcedureZoneFee;
import com.med.model.statistics.dto.procedure.ProcedureStatistics;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
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
    ProcedureServiceImpl procedureService;

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

        List<DoctorProcedureZoneFee> result = new ArrayList<>();

        Map<String, List<Talon>> map =  talons.stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.groupingBy(talon->talon.getDoctor().getFullName()));

        map.entrySet().stream().forEach(entry->{

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


public List<ProcedureStatistics> getProceduresStatistics(LocalDate start, LocalDate finish){

    List<ProcedureStatistics> list = new ArrayList<>();

     List<Talon> talons = talonService.getAllTallonsBetween(start, finish);

    final List<Procedure> procedures = procedureService.getAll();

    procedures.stream().forEach(procedure -> {

        ProcedureStatistics statistics = new ProcedureStatistics();

        statistics.setName(procedure.getName());

        Long assigned =  talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .count();

        Long executed =  talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .count();

        Long cancelled =  talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.CANCELED))
                .count();

/*        Long expired =  talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.CANCELED))
                .count();
        */

        Long expired = assigned - executed - cancelled;

        Long zones = (long) talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .mapToInt(Talon::getZones).sum();

         Long fee = (long) talons.stream()
                .filter(talon -> talon.getProcedure().getId()==procedure.getId())
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .mapToInt(Talon::getSum).sum();


         statistics.setAssigned(assigned);
         statistics.setExecuted(executed);
         statistics.setCancelled(cancelled);
         statistics.setExpired(expired);
         statistics.setZones(zones);
         statistics.setFee(fee);

        list.add(statistics);

    });


    return  list;
}


    public List<DoctorPercent> getProcedureStatisticsByDoctor(LocalDate start, LocalDate finish, int procedureId) {

       final List<Doctor> doctors = doctorService.getAll().stream()
               .filter(doctor -> doctor.getProcedureIds().contains(procedureId))
               .collect(Collectors.toList());


       final List<Talon> talons = talonService.getAllTallonsBetween(start, finish).stream()
                .filter(talon -> talon.getProcedure().getId()==procedureId)
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());

       final Long all = Long.valueOf(talons.size());

        List<DoctorPercent>  list = new ArrayList<>();

       if (all!=0) {

           doctors.stream().forEach(doctor ->{

               Long executed = talons.stream()
                       .filter(talon -> talon.getDoctor().equals(doctor))
                       .count();
               DoctorPercent item = new DoctorPercent(doctor.getFullName()
                       , 100* executed/all);

               list.add(item);
                   }



           );





       }














    return list;

}
}
