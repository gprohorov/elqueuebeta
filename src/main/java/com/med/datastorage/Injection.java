package com.med.datastorage;

import com.med.model.*;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.hotel.Chamber;
import com.med.model.hotel.Koika;
import com.med.model.statistics.dto.doctor.DoctorPeriodSalary;
import com.med.repository.*;
import com.med.repository.hotel.ChamberRepository;
import com.med.repository.hotel.KoikaRepository;
import com.med.repository.hotel.RecordRepository;
import com.med.services.*;
import com.med.services.hotel.ChamberService;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 24.03.19.
 */
//@Service
public class Injection {

    @Autowired
    ChamberRepository chamberRepository;

    @Autowired
    KoikaRepository  koikaRepository;

    private List<Chamber> chambers = new ArrayList<>();
    private List<Koika> koikas;



   // @PostConstruct
    void init() {
        Chamber chamber1 = new Chamber(1,"1", Category.LUX, 2, "", 2);
        Chamber chamber2 = new Chamber(2,"2", Category.LUX, 2, "", 2);
        Chamber chamber3 = new Chamber(3,"3", Category.LUX, 2, "", 2);
        Chamber chamber4 = new Chamber(4,"4", Category.LUX, 2, "", 2);
        Chamber chamber5 = new Chamber(5,"5", Category.LUX, 2, "", 2);
        Chamber chamber6 = new Chamber(6,"6", Category.LUX, 2, "", 2);
        Chamber chamber7 = new Chamber(7,"7", Category.LUX, 2, "", 2);
        Chamber chamber8 = new Chamber(8,"8", Category.SEMILUX, 2, "", 3);
        Chamber chamber9 = new Chamber(9,"9", Category.LUX, 2, "", 2);
        Chamber chamber10 = new Chamber(10,"10", Category.LUX, 2, "", 2);
        Chamber chamber11 = new Chamber(11,"11", Category.SEMILUX, 2, "", 4);
        Chamber chamber12 = new Chamber(12,"12", Category.LUX, 2, "", 2);
        Chamber chamber13 = new Chamber(13,"13", Category.LUX, 2, "", 2);
        Chamber chamber14 = new Chamber(14,"14", Category.LUX, 2, "", 2);
        Chamber chamber15 = new Chamber(15,"15", Category.LUX, 2, "", 2);
        Chamber chamber16 = new Chamber(16,"16", Category.LUX, 2, "", 2);
        Chamber chamber17 = new Chamber(17,"17", Category.LUX, 2, "", 2);

        Chamber chamber20 = new Chamber(20,"20", Category.SOC, 3, "", 8);
        Chamber chamber21 = new Chamber(21,"21", Category.SOC, 3, "", 9);
        Chamber chamber22 = new Chamber(22,"22", Category.SOC, 3, "", 8);
        Chamber chamber23 = new Chamber(23,"23", Category.SOC, 3, "", 7);
        Chamber chamber24 = new Chamber(24,"24", Category.SOC, 3, "", 5);
        Chamber chamber25 = new Chamber(25,"25", Category.SOC, 3, "", 5);
        Chamber chamber26 = new Chamber(26,"26", Category.SOC, 3, "", 4);
        Chamber chamber27 = new Chamber(27,"27", Category.SEMILUX, 3, "", 3);
        Chamber chamber28 = new Chamber(28,"28", Category.SOC, 3, "", 5);
        Chamber chamber29 = new Chamber(29,"29", Category.SOC, 3, "", 6);

        chambers.add(chamber1);
        chambers.add(chamber2);
        chambers.add(chamber3);
        chambers.add(chamber4);
        chambers.add(chamber5);
        chambers.add(chamber6);
        chambers.add(chamber7);
        chambers.add(chamber8);
        chambers.add(chamber9);
        chambers.add(chamber10);
        chambers.add(chamber11);
        chambers.add(chamber12);
        chambers.add(chamber13);
        chambers.add(chamber14);
        chambers.add(chamber15);
        chambers.add(chamber16);
        chambers.add(chamber17);

        chambers.add(chamber20);
        chambers.add(chamber21);
        chambers.add(chamber22);
        chambers.add(chamber23);
        chambers.add(chamber24);
        chambers.add(chamber25);
        chambers.add(chamber26);
        chambers.add(chamber27);
        chambers.add(chamber28);
        chambers.add(chamber29);

        chamberRepository.saveAll(chambers);

        koikas = new ArrayList<>(

                Arrays.asList(

                        new Koika(11, "1.1", chamber1, 350, null),
                        new Koika(12, "1.2", chamber1, 350, null),

                        new Koika(21, "2.1", chamber2, 350, null),
                        new Koika(22, "2.2", chamber2, 350, null),

                        new Koika(31, "3.1", chamber3, 350, null),
                        new Koika(32, "3.2", chamber3, 350, null),

                        new Koika(41, "4.1", chamber4, 350, null),
                        new Koika(42, "4.2", chamber4, 350, null),

                        new Koika(51, "5.1", chamber5, 350, null),
                        new Koika(52, "5.2", chamber5, 350, null),
                        new Koika(61, "6.1", chamber6, 350, null),
                        new Koika(62, "6.2", chamber6, 350, null),
                        new Koika(71, "7.1", chamber7, 600, null),
                        new Koika(72, "7.2", chamber7, 600, null),
                        new Koika(81, "8.1", chamber8, 300, null),
                        new Koika(82, "8.2", chamber8, 300, null),
                        new Koika(83, "8.3", chamber8, 300, null),
                        new Koika(91, "9.1", chamber9, 500, null),
                        new Koika(92, "9.2", chamber9, 500, null),
                        new Koika(101, "10.1", chamber10, 500, null),
                        new Koika(102, "10.2", chamber10, 500, null),
                        new Koika(111, "11.1", chamber11, 300, null),
                        new Koika(112, "11.2", chamber11, 300, null),
                        new Koika(113, "11.3", chamber11, 300, null),
                        new Koika(114, "11.4", chamber11, 300, null),
                        new Koika(121, "12.1", chamber12, 350, null),
                        new Koika(122, "12.2", chamber12, 350, null),
                        new Koika(131, "13.1", chamber13, 350, null),
                        new Koika(132, "13.2", chamber13, 350, null),
                        new Koika(141, "14.1", chamber14, 350, null),
                        new Koika(142, "14.2", chamber14, 350, null),
                        new Koika(151, "15.1", chamber15, 350, null),
                        new Koika(152, "15.2", chamber15, 350, null),
                        new Koika(161, "16.1", chamber16, 350, null),
                        new Koika(162, "16.2", chamber16, 350, null),
                        new Koika(171, "17.1", chamber17, 500, null),
                        new Koika(172, "17.2", chamber17, 500, null)
                )
        );
        chamberRepository.deleteAll();
        chamberRepository.saveAll(chambers);

       // koikaRepository.del
       // koikaRepository.saveAll(koikas);
        List<Koika> koika3 = new ArrayList<>();
        chambers.stream().filter(chamber -> chamber.getId()>=20)
                .forEach(chamber -> {
                    for (int i = 1; i <=chamber.getBeds() ; i++) {
                        koika3.add(new Koika(chamber.getId()*10 +i
                                , String.valueOf(chamber.getId()) + "." + String.valueOf(i)
                                , chamber
                                , 300
                                , null
                        ));

                    }

                });

        koika3.stream().forEach(System.out::println);
        koikaRepository.saveAll(koika3);



    }
}
