package com.med.datastorage;

import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by george on 3/9/18.
 */
@Configuration
public class DataStorageTest {

    @Autowired
    TalonRepository talonRepository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ChamberServiceImpl chamberService;

    @Autowired
    KoikaServiceImpl koikaService;

    @Autowired
    RecordServiceImpl recordService;

    @Autowired
    AccountingServiceImpl accountingService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    SalaryServiceImpl salaryService;

   @PostConstruct
    void init(){
   }


    public void launch(){

    }

    public void taskOne() { salaryService.createWeekSalary(); }

    public void taskTwo(){
        salaryService.createWeekBonus();
    }



    public void reset(){
        
    }



    public void resetPatientsTable() {
        System.out.println(" talon table updated");
    }



}
