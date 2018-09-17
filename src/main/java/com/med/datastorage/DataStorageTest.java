package com.med.datastorage;

import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by george on 3/9/18.
 */
@Configuration
public class DataStorageTest {

    @Autowired
    TalonRepository talonRepository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ChamberServiceImpl chamberService;

    @Autowired
    KoikaServiceImpl koikaService;

    @Autowired
    RecordServiceImpl recordService;

    @Autowired
    AccountingServiceImpl accountingService;

    @Autowired
    ProcedureServiceImpl procedureService;

   @PostConstruct
    void init(){



   }


//        recordService.closeRecord(patientService.getAll("").get(4).getId(), PaymentType.CARD);

//        List<Koika> koikas = recordService.getFreeKoikasForDay(LocalDateTime.now().plusDays(1));
//        for (Koika k : koikas){
//            System.out.println(k);
//        }

//        recordService.relocatePatient(
//                new Record(patientService.getAll("").get(4).getId(),
//                        "",
//                        koikaService.getAll().get(12),
//                        10,
//                        LocalDateTime.now(),
//                        LocalDateTime.now().plusDays(2),
//                        State.OCCUP), PaymentType.CARD);

//        List<KoikaLine> koikaLines = recordService.getLines(1);
//        for (KoikaLine kl : koikaLines){
//            System.out.println("\n---------------");
//            System.out.println(kl.getKoika().toString());
//            List<HotelDay> hotelDays = kl.getLine();
//            for (HotelDay hd : hotelDays){
//                System.out.println(hd.toString());
//            }
//        }


//        List<Koika> koikas = new ArrayList<>(
//                Arrays.asList(
//                        new Koika(1,"1/1", chamberService.getChamber(1),450)
//                        ,
//                        new Koika(9,"2/5", chamberService.getChamber(5),300)
//                        ,
//                        new Koika(2,"1/2", chamberService.getChamber(2),300)
//                        ,
//                        new Koika(11,"2/6", chamberService.getChamber(6),300)
//                        ,
//                        new Koika(3,"2/2", chamberService.getChamber(2),300)
//                        ,
//                        new Koika(4,"1/3", chamberService.getChamber(3),350)
//                        ,
//                        new Koika(5,"2/3", chamberService.getChamber(3),350)
//                        ,
//                        new Koika(13,"2/7", chamberService.getChamber(7),350)
//                        ,
//                        new Koika(6,"1/4", chamberService.getChamber(4),300)
//                        ,
//                        new Koika(7,"2/4", chamberService.getChamber(4),300)
//                        ,
//                        new Koika(8,"1/5", chamberService.getChamber(5),300)
//                        ,
//                        new Koika(10,"1/6", chamberService.getChamber(6),300)
//                        ,
//                        new Koika(12,"1/7", chamberService.getChamber(7),350)
//                        ,
//                        new Koika(14,"1/9", chamberService.getChamber(9),450)
//                )
//        );
//        koikaService.saveAll(koikas);
//        System.out.println("----------------- koiki updated------------------------------");
//    }

    public void reset(){

        recordService.deleteAll();


     /*     List<Talon> talons= talonRepository.findAll().stream()
             //   .filter(talon -> talon.getDate().isAfter(LocalDate.now()))
                .filter(talon -> talon.getDate().equals(LocalDate.now()))
                .filter(talon -> talon.getActivity().equals(Activity.ACTIVE))
                .collect(Collectors.toList());
        talonRepository.deleteAll(talons);



      talonRepository.findByDate(LocalDate.now());


      talons.stream()
                .forEach(talon -> {

                    if (talon.getActivity().equals(Activity.ON_PROCEDURE)){
                        talon.setActivity(Activity.NON_ACTIVE);
                    }
                    if (talon.getProcedure()==null){
                        talon.setProcedure(procedureService.getProcedure(2));
                    }
                });
      talonRepository.saveAll(talons);

     List<Patient> patients = patientService.getAll("");

     patients.stream().forEach(patient -> {

         if (patient.getRegistration()==null){patient.setRegistration(LocalDateTime.now().minusDays(15));}
     });
     patientService.saveAll(patients);
*/

    }



    public void resetPatientsTable() {
        System.out.println(" talon table updated");
    }
}
