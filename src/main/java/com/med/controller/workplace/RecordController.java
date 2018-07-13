package com.med.controller.workplace;

import com.med.model.dto.KoikaLine;
import com.med.model.hotel.Record;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/workplace/hotel/")
@RestController
public class RecordController {

    @Autowired
    RecordServiceImpl service;

    @RequestMapping("/hotel")
   public List<KoikaLine> showAll(){
        return service.getLines(10);
    }
}
