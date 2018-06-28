package com.med.controller.workplace;

import com.med.model.Therapy;
import com.med.services.therapy.impls.TherapyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DiagnosticsController {



    @Autowired
    TherapyServiceImpl service;

    @RequestMapping("/therapy/list/")
    public List<Therapy> showTherapys() {
        return service.getAll();
    }

    // READ the Therapy by id
    @GetMapping("/therapy/get/{id}")
    public Therapy showOneTherapy(@PathVariable(value = "id")  String therapyId) {

        return service.getTherapy(therapyId);
    }

    // CREATE the Therapy
    @PostMapping("/therapy/save")
    public Therapy saveTherapy(@RequestBody Therapy therapy) {

//
        return service.saveTherapy(therapy);
    }

    // DELETE the therapy by id
    @PostMapping("/therapy/delete/{id}")
    public Therapy delTherapy(@PathVariable(value = "id")  int therapyId)  {

        return null;
                //service.deleteTherapy(therapyId);

    }

    // finish the Therapy by id
    @GetMapping("/therapy/finish/{id}")
    public Therapy finishTherapy(@PathVariable(value = "id")  String therapyId) {

        return service.finishTherapy(therapyId);
    }
/*

    // assign talons acc. to therapy
    @GetMapping("/therapy/assign/{id}")
    public List<Talon> assignTherapy(@PathVariable(value = "id")  String therapyId) {

        return service.assignTherapy(therapyId);
    }
*/


}

