package com.med.datastorage;

import com.med.model.Patient;
import com.med.model.dto.HotelDay;
import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Record;
import com.med.model.hotel.State;
import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    @PostConstruct
    void init(){

//        recordService.getLines() test start

//        List<KoikaLine> koikaLines = recordService.getLines(10);
//        for (KoikaLine kl : koikaLines){
//            System.out.println("New line:\n---------------");
//            System.out.println(kl.getKoika().toString());
//            List<HotelDay> hotelDays = kl.getLine();
//            for (HotelDay hd : hotelDays){
//                System.out.println(hd.toString());
//            }
//        }

//        recordService.getLines() test end


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
//                               State.OCCUP)
//               )
//       );
//        recordService.saveAll(records);
//        List<Koika> koikas = new ArrayList<>(
//                Arrays.asList(
//
//                        new Koika(1,"1/2", chamberService.getChamber(1),15)
//                        ,
//                        new Koika(1,"1/1", chamberService.getChamber(1),18)
//                        ,
//                        new Koika(2,"1/9", chamberService.getChamber(9),18)
//                        ,
//                        new Koika(3,"1/3", chamberService.getChamber(3),15)
//                        ,
//                        new Koika(4,"2/3", chamberService.getChamber(3),15)
//                        ,
//                        new Koika(5,"1/7", chamberService.getChamber(7),15)
//                        ,
//                        new Koika(6,"2/7", chamberService.getChamber(7),15)
//                        ,
//                        new Koika(7,"1/2", chamberService.getChamber(2),13)
//                        ,
//                        new Koika(8,"2/2", chamberService.getChamber(2),13)
//                        ,
//                        new Koika(9,"1/4", chamberService.getChamber(4),13)
//                        ,
//                        new Koika(10,"2/4", chamberService.getChamber(4),13)
//                        ,
//                        new Koika(11,"1/5", chamberService.getChamber(5),13)
//                        ,
//                        new Koika(12,"2/5", chamberService.getChamber(5),13)
//                        ,
//                        new Koika(13,"1/6", chamberService.getChamber(6),13)
//                        ,
//                        new Koika(14,"2/6", chamberService.getChamber(6),13)
//                )
//        );
//
//        koikaService.saveAll(koikas);

//        System.out.println("----------------- koiki updated------------------------------");
    }

    public void reset(){
        talonRepository.deleteAll();
        accountingService.deleteAll();
        List<Patient> patients = patientService.getAll("");
        patients.stream().forEach(
                patient -> {
                    patient.setStartActivity(null);
                    patient.setLastActivity(null);
                    patient.setBalance(0);
                }
        );
        patientService.saveAll(patients);
    }

    public void resetPatientsTable() {
        System.out.println(" talon table updated");
    }
}
