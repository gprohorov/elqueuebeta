package com.med.datastorage.injection;

import com.med.model.Category;
import com.med.model.hotel.Chamber;
import com.med.model.hotel.Koika;
import com.med.repository.hotel.ChamberRepository;
import com.med.repository.hotel.KoikaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 24.03.19.
 */
//@Service
public class InjectionWeekStat {

    @Autowired
    ChamberRepository chamberRepository;




   // @PostConstruct
    void init() {


    }
}
