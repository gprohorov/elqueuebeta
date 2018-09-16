package com.med.controller;

import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HotelController {

    @Autowired
    RecordServiceImpl service;

  /*  @PostMapping("/record/create")
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
*/

}
