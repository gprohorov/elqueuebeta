package com.med.controller;

import com.med.model.hotel.Record;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api/hotel")
@CrossOrigin("*")
public class RecordController {

    @Autowired
    RecordServiceImpl service;

    @PostMapping("/record/create")
    public Record createRecord(@Valid @RequestBody Record record) {
        return service.createRecord(record);
    }

    @GetMapping("/record/get/{recordId}")
    public Record getRecord(@PathVariable(value = "recordId") String recordId) {
        return service.getRecord(recordId);
    }


    @PostMapping("/record/update/")
    public Record updateRecord(@Valid @RequestBody Record record) {
        return service.updateRecord(record);
    }


}
