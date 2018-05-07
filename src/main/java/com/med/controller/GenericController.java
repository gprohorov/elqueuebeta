package com.med.controller;

import com.med.model.Generic;
import com.med.services.generic.impls.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@SuppressWarnings("ALL")
@RestController
public class GenericController {

    @Autowired
    GenericServiceImpl service;


    @RequestMapping("/generics")
   public List<Generic> showGenerics(){
        return service.getAll();
    }
}
