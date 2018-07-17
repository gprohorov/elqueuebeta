package com.med.controller.workplace;

import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Record;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/workplace/hotel/")
@RestController
public class RecordController {

    @Autowired
    RecordServiceImpl service;
    
    @RequestMapping("/koika/list")
    public List<KoikaLine> showAllKoikasForDays() {
        return service.getLines(30);
    }

    @RequestMapping("/record/list")
    public List<Record> showAll() {
        return service.getAll();
    }
}
