package com.med.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.med.model.Response;
import com.med.model.Settings;
import com.med.services.SettingsService;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin("*")
public class SettingsController {

    @Autowired
    SettingsService service;
    
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