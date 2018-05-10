package com.med.controller;

import com.med.datastorage.DataStorageTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 27.04.18.
 */

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class Util {

     @Autowired
     DataStorageTest dataStorage;

    @RequestMapping("/util/reset-db")
    public void resetPatientsTable(){ dataStorage.resetPatientsTable(); }





}
