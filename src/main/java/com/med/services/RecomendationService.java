package com.med.services;


import com.med.model.Patient;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendationService {

    @Autowired
    AccountingService accountingService;

    @Autowired
    PatientService patientService;

    public void setAwardForRecomendation() {

        List<Accounting> paymentList = accountingService.getAllForDate(LocalDate.now())
                .stream().filter(accounting ->
                        (accounting.getPayment().equals(PaymentType.CASH)
                      || accounting.getPayment().equals(PaymentType.CARD)))
                .collect(Collectors.toList());

        paymentList.stream().forEach(pay->{

                 String recomendatorId = patientService.getPatient(pay.getPatientId())
                         .getRecomendation();


                 if (recomendatorId!=null){

                     LocalDate registration = patientService.getPatient(pay.getPatientId())
                             .getRegistration().toLocalDate();
                     int daysGone = Period.between(registration, LocalDate.now()).getDays();
                     System.out.println(daysGone);

                     if (daysGone <= 31){
                       String looserName = patientService.getPatient(pay.getPatientId())
                             .getPerson().getFullName().split(" ")[0];
                       int sum = pay.getSum() * 5/100;
                       Accounting award = new Accounting();
                       award.setDate(LocalDate.now());
                       award.setDateTime(LocalDateTime.now());
                       award.setPatientId(recomendatorId);
                       award.setDesc("Премiя за " + looserName  );
                       award.setPayment(PaymentType.RECOMENDATION);
                       award.setSum(sum);
                       accountingService.createAccounting(award);
                       System.out.println(award.toString() + "\n");
                     }
                 }
                }
        );


    }



}
