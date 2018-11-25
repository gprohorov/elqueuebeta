package com.med.controller;

import com.med.model.Response;
import com.med.model.Settings;
import com.med.services.settings.impls.SettingsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 29.10.18.
 */
@RestController
@RequestMapping("/api/settings")
@CrossOrigin("*")
public class SettingsController {

    @Autowired
    SettingsServiceImpl service;
    
    @RequestMapping("/get")
    public Settings get() {
        return service.get();
    }
    
    @RequestMapping("/update/")
    public Response updateNode(@Valid @RequestBody Settings model) {
        return this.service.update(model).equals(null)
               ? new Response(false, "Error updating settings!")
               : new Response(true, "OK");
    }
}