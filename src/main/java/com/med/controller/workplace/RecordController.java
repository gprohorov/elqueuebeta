package com.med.controller.workplace;

import com.med.model.hotel.dto.KoikaLine;
import com.med.model.hotel.Koika;
import com.med.model.hotel.Record;
import com.med.model.hotel.dto.KoikaRecord;
import com.med.model.hotel.dto.RecordDto;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/workplace/hotel")
@RestController
@CrossOrigin("*")
public class RecordController {

    @Autowired
    KoikaServiceImpl koikaService;

    @Autowired
    RecordServiceImpl service;
    
    @RequestMapping("/booking")
    public List<KoikaLine> showAllKoikasForDays() {
        return service.getLines(14);
    }

    @RequestMapping("/koika/map")
    public List<KoikaRecord> showMap() {
        return service.getKoikaMap();
    }

    @RequestMapping("/koika/list")
    public List<Koika> showKoikas() { return koikaService.getAll();}

    //show free koikas for today
    public List<Koika> showFreeKoikasForToday(){return service.getFreeKoikasForDay(LocalDateTime.now());}

    @RequestMapping("/record/list")
    public List<Record> showAll() {
        return service.getAll();
    }

    // CREATE a new Record
    @PostMapping("/record/create")
    public Record createRecord(@Valid @RequestBody RecordDto recordDto) {
        return service.createRecordFromDto(recordDto);
    }
    
    // UPDATE the Record
    @PostMapping("/record/update")
    public Record updateRecord(@Valid @RequestBody RecordDto recordDto) {
    	return service.createRecordFromDto(recordDto);
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
