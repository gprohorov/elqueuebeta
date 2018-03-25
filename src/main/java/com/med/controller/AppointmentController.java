package com.med.controller;

import com.med.model.Appointment;
import com.med.services.appointment.impls.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api")
@RestController
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl service;


    @RequestMapping("/appointment/list")
   public List<Appointment> showAllAppointments(){
        return service.getAll();
    }



    ////////////////////////////// CRUD ////////////////////////////////////

    // CREATE a new Appointment
    @PostMapping("/appointment/create")
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment) {
        System.out.println("----");
        return service.createAppointment(appointment);
    }

    // READ the Appointment by id
    @GetMapping("/appointment/get/{id}")
    public Appointment showOneAppointment(@PathVariable(value = "id")  int appointmentId) {

        return service.getAppointment(appointmentId);
    }

    // UPDATE the appointment by id
    @PostMapping("/appointment/update/{id}")
    public Appointment updateAppointment(@PathVariable(value = "id")  int appointmentId,
                                         @Valid @RequestBody Appointment updates)  {
        updates.setId(appointmentId);

        return service.updateAppointment(updates);

    }

    // DELETE the appointment by id
    @PostMapping("/appointment/delete/{id}")
    public Appointment delAppointment(@PathVariable(value = "id")  int appointmentId)  {

        return service.deleteAppointment(appointmentId);

    }

////////////////////////////////////////////////////////////////////////

    // DELETE the appointment by id
    @RequestMapping("/appointment/list/{date}")
    public List<Appointment> delAppointment(@PathVariable(value = "date")  LocalDate date)  {

        return service.getAppointmentsByDate(date);

    }



    @RequestMapping("/appointment/list/today")
   public List<Appointment> showTodayAppointments(){
        return
                service.getAppointmentsByDate(LocalDate.now());
    }

    @RequestMapping("/appointment/list/tomorow")
   public List<Appointment> TomorowAppointments(){
        return
                service.getAppointmentsByDate(LocalDate.now().plusDays(1));
    }



}
