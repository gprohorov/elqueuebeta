package com.med.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/appointment")
@CrossOrigin("*")
@RestController
public class AppointmentController {/*

    @Autowired
    AppointmentServiceImpl service;


    @RequestMapping("/list")
   public List<Appointment> showAllAppointments(){
      //  return service.getAll();
        return null;
    }



    ////////////////////////////// CRUD ////////////////////////////////////

    // CREATE a new Appointment
    @PostMapping("/create")
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment) {

        return service.createAppointment(appointment);
    }

    // READ the Appointment by id
    @GetMapping("/get/{id}")
    public Appointment showOneAppointment(@PathVariable(value = "id")  int appointmentId) {

        return service.getAppointment(appointmentId);
    }

    // UPDATE the appointment by id
    @PostMapping("/update/{id}")
    public Appointment updateAppointment(@PathVariable(value = "id")  int appointmentId,
                                         @Valid @RequestBody Appointment updates)  {
        updates.setId(appointmentId);

        return service.updateAppointment(updates);
    }


    // DELETE the appointment by id
    @PostMapping("/delete/{id}")
    public Appointment delAppointment(@PathVariable(value = "id")  int appointmentId)  {

        return service.deleteAppointment(appointmentId);

    }

////////////////////////////////////////////////////////////////////////

    // get al appointments by date
    @RequestMapping("/list/{date}")
    public List<Appointment> delAppointment(@PathVariable(value = "date")  LocalDate date)  {

        return service.getAppointmentsByDate(date);
    }

    @RequestMapping("/list/today")
   public List<Appointment> showTodayAppointments(){
        return
                service.getAppointmentsByDate(LocalDate.now());
    }

    @RequestMapping("/list/tomorow")
   public List<Appointment> TomorowAppointments(){
        return service.getAppointmentsByDate(LocalDate.now().plusDays(1));
    }


*/
}
