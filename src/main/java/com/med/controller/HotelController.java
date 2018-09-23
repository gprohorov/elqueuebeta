package com.med.controller;

import com.med.model.Response;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import com.med.model.hotel.dto.KoikaLine;
import com.med.model.hotel.dto.KoikaRecord;
import com.med.model.hotel.dto.RecordDto;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api/hotel")
@CrossOrigin("*")
public class HotelController {
	
	@Autowired
    KoikaServiceImpl koikaService;

    @Autowired
    RecordServiceImpl service;

    @RequestMapping("/koika/map/{start}")
    public List<KoikaRecord> showMap(@PathVariable(value = "start") String start) {
        return service.getKoikaMap(LocalDate.parse(start));
    }

    @RequestMapping("/koika/list")
    public List<Koika> showKoikas() { 
    	return koikaService.getAll();
    }

    //show free koikas for today ---- WTF !!!??? ----
    public List<Koika> showFreeKoikasForToday(){
    	return service.getFreeKoikasForDay(LocalDateTime.now());
	}

    @RequestMapping("/record/list")
    public List<Record> showAll() {
        return service.getAll();
    }

    // CREATE a new Record
    @PostMapping("/record/create")
    public Response createRecord(@Valid @RequestBody RecordDto recordDto) {
        return service.createRecordFromDto(recordDto);
    }
    
    // UPDATE the Record
    @PostMapping("/record/update")
    public Response updateRecord(@Valid @RequestBody RecordDto recordDto) {
    	return new Response(true, ""); //service.createRecordFromDto(recordDto);
    }
    
    @GetMapping("/record/cancel/{recordId}")
    public String cancelRecord(@PathVariable(value = "recordId") String recordId) {
    	service.cancelRecord(recordId);
    	return null;
    }

    @GetMapping("/record/get/{recordId}")
    public Record getRecord(@PathVariable(value = "recordId") String recordId) {
    	return service.getRecord(recordId);
    }
}
