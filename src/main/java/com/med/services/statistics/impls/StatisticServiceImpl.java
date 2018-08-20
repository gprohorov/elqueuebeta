package com.med.services.statistics.impls;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.doctor.DoctorCurrentStatistics;
import com.med.model.statistics.dto.doctor.DoctorPercent;
import com.med.model.statistics.dto.doctor.DoctorProcedureZoneFee;
import com.med.model.statistics.dto.doctor.ProcedureCount;
import com.med.model.statistics.dto.general.GeneralStatisticsDTO;
import com.med.model.statistics.dto.patient.PatientDTO;
import com.med.model.statistics.dto.procedure.ProcedureStatistics;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.statistics.interfaces.IStatisticService;
import com.med.services.tail.Impls.TailServiceImpl;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    TherapyServiceImpl therapyService;

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

        statistics.setProcedureId(procedure.getId());
        
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

    public PatientDTO getPatientStatistics(String patientId) {

        PatientDTO statistics = new PatientDTO();
        statistics.setPatient(patientService.getPatient(patientId));

        Therapy therapy = therapyService.findTheLastTherapy(patientId);
        LocalDate start = therapy.getStart().toLocalDate();
        statistics.setStart(start);

        List<Talon> talons = talonService.getAllTalonsForPatient(patientId).stream()
                .filter(talon -> talon.getDate().isAfter(start.minusDays(1)))
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .collect(Collectors.toList());
        if (talons.isEmpty()){return null;}

        LocalDate finish = talons.stream()

                .map(talon -> talon.getDate())
                .sorted(Comparator.reverseOrder()).findFirst().orElse(null);
        statistics.setFinish(finish);

        int days = (int) talons.stream().map(talon -> talon.getDate())
                .distinct().count();
        statistics.setDays(days);

        int procedures = (int) talons.size();
        statistics.setProcedures(procedures);

        int zones =  talons.stream().mapToInt(talon -> talon.getZones()).sum();
        statistics.setZones(zones);


       List<Accounting> accountings = patientService
               .getUltimateBalance(patientId,start.minusDays(1),finish.plusDays(1));

       int bill = accountings.stream()
               .filter(accounting ->(
                       accounting.getPayment().equals(PaymentType.PROC)
                         ||
                       accounting.getPayment().equals(PaymentType.HOTEL)
               ))
               .mapToInt(Accounting::getSum)
               .sum();
       statistics.setBill(bill*(-1));

       int cash = accountings.stream()
                .filter(accounting ->
                        accounting.getPayment().equals(PaymentType.CASH)
                )
                .mapToInt(Accounting::getSum)
                .sum();
        statistics.setCash(cash);

       int card = accountings.stream()
                .filter(accounting ->
                        accounting.getPayment().equals(PaymentType.CARD)
                )
                .mapToInt(Accounting::getSum)
                .sum();
        statistics.setCard(card);

        int discount = accountings.stream()
                .filter(accounting ->
                        accounting.getPayment().equals(PaymentType.DISCOUNT)
                )
                .mapToInt(Accounting::getSum)
                .sum();
        statistics.setDiscount(discount);

        statistics.setDebt(cash + card + discount + bill);

        return statistics; // of patient
    }

    public GeneralStatisticsDTO getGeneralStatisticsDay(LocalDate date) {

        GeneralStatisticsDTO statisticsDTO = new GeneralStatisticsDTO();
        List<Talon> talons = talonService.getTalonsForDate(date);
        List<Accounting> accountings = accountingService.getAllForDate(date);
   //     logger.info(" ---------- " + accountings.size() + " -----  ");
    //    logger.info(" ---------- " + date + " -----  ");

        statisticsDTO.setDate(date);

        int patients = (int) talons.stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .map(talon -> talon.getPatientId())
                .distinct()
                .count();
        statisticsDTO.setPatients(patients);

        int doctors = (int) talons.stream()
                .filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                .map(talon -> talon.getDoctor().getId())
                .distinct()
                .count();
        statisticsDTO.setDoctors(doctors);

      //  long cash =  accountingService.getSumForDateCash(date);
        long cash =  accountings.stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.CASH))
                .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setCash(cash);



        long card =  accountings.stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.CARD))
                .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setCard(card);



        long bill =  accountings.stream()
                .filter(accounting -> accounting.getSum()<0)
                .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setBill(bill);

        long discount =  accountings.stream()
                .filter(accounting -> accounting.getPayment().equals(PaymentType.DISCOUNT))
                .mapToLong(Accounting::getSum).sum();
        statisticsDTO.setDiscount(discount);

        long debt = bill + cash + card + discount;
        statisticsDTO.setDebt(debt);
        System.out.println("-----------------------------------");
        System.out.println(statisticsDTO.toString());


        return statisticsDTO;
    }

    public List<GeneralStatisticsDTO> getGeneralStatisticsFromTo(LocalDate start, LocalDate finish) {
        List<GeneralStatisticsDTO> list = new ArrayList<>();

        LocalDate date = start;

        while (!date.equals(finish)){
            GeneralStatisticsDTO dto = new GeneralStatisticsDTO();
            dto = this.getGeneralStatisticsDay(date);
            list.add(dto);

            date = date.plusDays(1);

        }

    return list;
    }

/////////////////////////////////////// CURRENT DOCTOR
    public DoctorCurrentStatistics getOneDoctorCurrentStatistics(int doctorId){

        List<Talon> talons = talonService.getTalonsForDate(LocalDate.now()).stream()

                .filter(talon -> (talon.getActivity().equals(Activity.EXECUTED)
                                || talon.getActivity().equals(Activity.ON_PROCEDURE)
                        ))
                .filter(talon -> talon.getDoctor().getId()==doctorId)
                .collect(Collectors.toList());

        DoctorCurrentStatistics statistics = new DoctorCurrentStatistics();

        if (!talons.isEmpty()) {

            statistics.setName(this.getLastName(doctorService.getDoctor(doctorId).getFullName()));

            LocalDateTime start = talons.stream().map(talon -> talon.getStart())
                    .findFirst().orElse(null);

            statistics.setStartWork(start);
//////////////////////////////////////////////////////////////

            Talon lastTalon = talons.stream()
                    .sorted(Comparator.comparing(Talon::getStart).reversed())
                    .findFirst().orElse(null);

            if (lastTalon.getActivity().equals(Activity.ON_PROCEDURE)) {
                statistics.setLastActivity(lastTalon.getStart());
                Patient patient = patientService.getPatient(lastTalon.getPatientId());
                statistics.setCurrentPatient(this.getLastName(patient.getPerson().getFullName()));
            }

            if (lastTalon.getActivity().equals(Activity.EXECUTED)) {
                statistics.setLastActivity(lastTalon.getExecutionTime());
                statistics.setCurrentPatient("");
            }

            Long zones = (long) talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                    .mapToInt(Talon::getZones).sum();
            statistics.setZonesCount(zones);

            Long fee = (long) talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                    .mapToInt(Talon::getSum).sum();
            statistics.setFee(fee);

            List<String> names = new ArrayList<>();

            talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                    .forEach(talon -> {
                String name = patientService.getPatient(talon.getPatientId()).getPerson().getFullName();
                names.add(this.getLastName(name));
            });
            statistics.setPatients(names);

            List<ProcedureCount> map = new ArrayList<>();
            talons.stream().filter(talon -> talon.getActivity().equals(Activity.EXECUTED))
                    .collect(Collectors.groupingBy(tal -> tal.getProcedure(), Collectors.counting()))
                    .forEach((key,value) -> {
                        ProcedureCount item = new ProcedureCount(key.getName(),value);
                        map.add(item);
                    });
            statistics.setProcedureMap(map);

        }
    return statistics;
    }


    public List<DoctorCurrentStatistics> getDoctorsListCurrentStatictics(){

        List<DoctorCurrentStatistics> list = new ArrayList<>();

        doctorService.getAll().stream().forEach(doctor -> {
            DoctorCurrentStatistics statistics = this.getOneDoctorCurrentStatistics(doctor.getId());
            list.add(statistics);
        });



    return list;
    }

     private String getLastName(String fullName){
         return fullName.split(" ")[0];
     }
}
