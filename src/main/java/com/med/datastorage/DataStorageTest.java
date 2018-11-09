package com.med.datastorage;

import com.med.model.SalaryDTO;
import com.med.repository.talon.TalonRepository;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.hotel.chamber.impls.ChamberServiceImpl;
import com.med.services.hotel.koika.impls.KoikaServiceImpl;
import com.med.services.hotel.record.impls.RecordServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import com.med.services.salarydto.impls.SalaryDTOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    SalaryDTOServiceImpl salaryDTOService;

   @PostConstruct
    void init(){
   }


    public void launch(){

    }

    public void taskOne() {
        // salaryService.createWeekSalary();
        List<SalaryDTO> list = salaryService.getSalaryList();
        LocalDate from = LocalDate.now().minusDays(12);
        LocalDate to = LocalDate.now().minusDays(7);
        LocalDateTime opened = LocalDateTime.now().minusDays(7);
        int week =  to.getDayOfYear()/7;

        list.stream().forEach(item-> {

            item.setFrom(from);
            item.setTo(to);
            item.setWeek(week);
            item.setOpened(opened);
            salaryDTOService.createSalaryDTO(item);
        });



        }




    public List<SalaryDTO> taskTwo(){
       return salaryDTOService.getOpenTable();
    }



    public void reset(){
        
    }



    public void resetPatientsTable() {
        System.out.println(" talon table updated");
    }



}
