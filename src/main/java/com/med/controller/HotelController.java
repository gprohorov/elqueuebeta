package com.med.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.Response;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import com.med.model.hotel.dto.KoikaRecord;
import com.med.model.hotel.dto.RecordDto;
import com.med.services.hotel.KoikaService;
import com.med.services.hotel.RecordService;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin("*")
public class HotelController {
	
	@Autowired
    KoikaService koikaService;

    @Autowired
    RecordService service;

    @RequestMapping("/koika/map/{start}")
    public List<KoikaRecord> showMap(@PathVariable(value = "start") String start) {
        return service.getKoikaMap(LocalDate.parse(start));
    }

    @RequestMapping("/koika/list")
    public List<Koika> showKoikas() { 
    	return koikaService.getAll();
    }

    @RequestMapping("/record/list")
    public List<Record> showAll() {
        return service.getAll();
    }

    @PostMapping("/record/create")
    public Response createRecord(@Valid @RequestBody RecordDto recordDto) {
        return service.createRecordFromDto(recordDto);
    }
    
    @PostMapping("/record/update")
    public Response updateRecord(@Valid @RequestBody RecordDto recordDto) {
    	return service.updateRecord(recordDto);
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