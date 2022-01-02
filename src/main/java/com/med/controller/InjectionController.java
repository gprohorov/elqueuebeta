package com.med.controller;

import com.med.model.Doctor;
import com.med.services.DoctorService;
import com.med.services.InjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/injection")
@CrossOrigin("*")
public class InjectionController {

    @Autowired
    InjectionService service;

    @GetMapping("/inject")
    public void inject() {
      //  service.inject();
        System.out.println("INJECTED");
    }

}
