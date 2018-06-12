package com.med.controller.workplace;

import com.med.model.hotel.Hotel;
import com.med.services.hotel.hotel.impls.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@SuppressWarnings("ALL")
@RestController
public class HotelController {

    @Autowired
    HotelServiceImpl service;


    @RequestMapping("/hotel")
   public List<Hotel> showAll(){
        return service.getAll();
    }
}
