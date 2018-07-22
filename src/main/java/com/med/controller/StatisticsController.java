package com.med.controller;

import com.med.model.Patient;
import com.med.model.statistics.dto.DoctorProcedureZoneFee;
import com.med.services.statistics.impls.StatisticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("*")
public class StatisticsController {


    @Autowired
    StatisticServiceImpl service;




    @RequestMapping("/cash/now")
    public Long getCashAvailable(){
        return service.getCashAvailable();
    }

    @RequestMapping("/cash/total")
    public Long getCashTotal(){
        return service.getTotalCash();
    }

      @RequestMapping("/doctors/{start}/{finish}")
    public List<DoctorProcedureZoneFee> getDoctorsStatistics(
              @PathVariable(value = "start") LocalDate start,
              @PathVariable(value = "finish") LocalDate finish)
      {
        return service.getDoctorsProceduresFromTo(start,finish);
    }

    @RequestMapping("/procedures/count")
    public Long getProceduresCount(){
        return service.getAllProceduresCount();
    }

    @RequestMapping("/patients/count")
    public Long getPatientsCount(){
        return service.getAllPatientsCount();
    }

    @RequestMapping("/patients/debetors")
    public List<Patient> getPatientsDebets(){
        return service.getAllDebtors();
    }




/*





    // get a Person list by lastName
    @GetMapping("/doctor/list/{name}")
    public List<Doctor> getDoctorsByLastName(@PathVariable(value = "name") String lastName) {
        return service.getDoctorListByLetters(lastName);
    }



    // CREATE a new Doctor
    @PostMapping("/doctor/create")
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) {

        return service.createDoctor(doctor);
    }


    // READ the Doctor by id
    @GetMapping("/doctor/get/{id}")
    public Doctor showOneDoctor(@PathVariable(value = "id")  int doctorId) {

        return service.getDoctor(doctorId);
    }

    // UPDATE the doctor by id
    @PostMapping("/doctor/update/")
    public Doctor updateDoctor(@Valid @RequestBody Doctor updates)  {
     //   updates.setId(doctorId);

        return service.updateDoctor(updates);

    }

    // DELETE the doctor by id
    @GetMapping("/doctor/delete/{id}")
    public Doctor delDoctor(@PathVariable(value = "id")  int doctorId)  {

        return service.deleteDoctor(doctorId);

    }
*/

}
