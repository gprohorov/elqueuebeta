package com.med.controller.workplace;

import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Record;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/workplace/hotel/")
@RestController
@CrossOrigin("*")
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


    // CREATE a new Record
    @PostMapping("/record/create")
    public Record createDoctor(@Valid @RequestBody Record record) {

        return service.createRecord(record);
    }


}
