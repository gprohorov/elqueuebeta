package com.med.datastorage;

import com.med.model.Activity;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

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

//    @PostConstruct
//    void init(){

//       List<Record> records = new ArrayList<>(
//               Arrays.asList(
//                       new Record(patientService.getAll("").get(0).getId(),
//                               "",
//                               koikaService.getAll().get(0),
//                               100,
//                               LocalDateTime.now(),
//                               LocalDateTime.now().plusDays(5),
//                               State.OCCUP)
//                                               ,
//                       new Record(patientService.getAll("").get(1).getId(),
//                               "",
//                               koikaService.getAll().get(0),
//                               90,
//                               LocalDateTime.now().plusDays(6),
//                               LocalDateTime.now().plusDays(9),
//                               State.BOOK)
//                                              ,
//                       new Record(patientService.getAll("").get(2).getId(),
//                               "",
//                               koikaService.getAll().get(1),
//                               150,
//                               LocalDateTime.now().plusDays(3),
//                               LocalDateTime.now().plusDays(8),
//                               State.BOOK)
//                                              ,
//                       new Record(patientService.getAll("").get(3).getId(),
//                               "",
//                               koikaService.getAll().get(3),
//                               85,
//                               LocalDateTime.now(),
//                               LocalDateTime.now().plusDays(4),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(8).getId(),
//                               "",
//                               koikaService.getAll().get(12),
//                               70,
//                               LocalDateTime.now(),
//                               LocalDateTime.now().plusDays(1),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(6).getId(),
//                               "",
//                               koikaService.getAll().get(11),
//                               75,
//                               LocalDateTime.now().plusDays(2),
//                               LocalDateTime.now().plusDays(4),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(7).getId(),
//                               "",
//                               koikaService.getAll().get(10),
//                               85,
//                               LocalDateTime.now(),
//                               LocalDateTime.now().plusDays(1),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(5).getId(),
//                               "",
//                               koikaService.getAll().get(9),
//                               55,
//                               LocalDateTime.now().plusDays(7),
//                               LocalDateTime.now().plusDays(8),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(4).getId(),
//                               "",
//                               koikaService.getAll().get(8),
//                               100,
//                               LocalDateTime.now().minusDays(4),
//                               LocalDateTime.now().plusDays(5),
//                               State.OCCUP),
//                       new Record(patientService.getAll("").get(4).getId(),
//                               "",
//                               koikaService.getAll().get(8),
//                               0,
//                               LocalDateTime.now().minusDays(1),
//                               LocalDateTime.now().plusDays(2),
//                               State.PAUSED),
//                       new Record(patientService.getAll("").get(10).getId(),
//                               "",
//                               koikaService.getAll().get(7),
//                               70,
//                               LocalDateTime.now(),
//                               LocalDateTime.now().plusDays(3),
//                               State.OCCUP)
//               )
//       );
//        recordService.saveAll(records);

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

     //   talonRepository.deleteAll();
  //      accountingService.deleteAll();
    //    List<Patient> patients = patientService.getAll("");
    //    patients.stream().forEach(
     //           patient -> {
     //               patient.setStartActivity(null);
     //               patient.setLastActivity(null);
     //               patient.setBalance(0);
     //           }
    //    ); Hope1234
    //    patientService.saveAll(patients);



      List<Talon> talons=  talonRepository.findByDate(LocalDate.now());

      talons.stream()
                .forEach(talon -> {

                    if (talon.getActivity().equals(Activity.ON_PROCEDURE)){
                        talon.setActivity(Activity.NON_ACTIVE);
                    }
                });
      talonRepository.saveAll(talons);
    }



    public void resetPatientsTable() {
        System.out.println(" talon table updated");
    }
}
