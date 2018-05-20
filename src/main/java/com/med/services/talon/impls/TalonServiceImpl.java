package com.med.services.talon.impls;

import com.med.model.*;
import com.med.repository.talon.TalonRepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.interfaces.ITalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TalonServiceImpl implements ITalonService {


    @Autowired
    TalonRepository repository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ProcedureServiceImpl procedureService;

    @Autowired
    DoctorServiceImpl doctorService;


    @Override
    public Talon createTalon(String patientId, int procedureId, int days) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure,days);

        return repository.save(talon);
    }

    public Talon saveTalon(Talon talon){
        return repository.save(talon);
    }

   @Override
    public Talon createTalon(String patientId, int procedureId, LocalDate date) {

        Procedure procedure = procedureService.getProcedure(procedureId);
        Talon talon = new Talon(patientId, procedure, date);

        return repository.save(talon);
    }

    @Override
    public List<Talon> getAll() {
        return repository.findAll();
    }

    @Override
    public Talon getTalon(String id) {

        return repository.findById(id).orElse(null);
    }

    public Talon getTalonByProcedure(String patientId, int procedureId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getProcedure().getId()==procedureId)
                .filter(tal -> tal.getPatientId()==patientId)
                .filter(tal -> tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public Talon getTalonByPatient(String patientId,  Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getPatientId().equals(patientId))
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    public Talon getTalonByDoctor(String patientId, int doctorId, Activity activity) {
        Talon talon = this.getTalonsForToday().stream()
                .filter(tal -> tal.getDoctor().getId()==doctorId)
                .filter(tal->tal.getPatientId()==patientId)
                .filter(tal->tal.getActivity().equals(activity))
                .findFirst().orElse(null);

        return talon;
    }

    @Override
    public List<Talon> getTalonsForToday() {
        return this.getAll().stream()
                .filter(talon -> talon.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Talon> getTalonsForDate(LocalDate date) {
        return this.getAll().stream()
                .filter(talon -> talon.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public Talon setActivity(String talonId, Activity activity) {
        List<Activity> activities = new ArrayList<>(
            Arrays.asList(
                Activity.EXECUTED,
                Activity.EXPIRED,
                Activity.CANCELED,
                Activity.ON_PROCEDURE
            )
        );
        Talon talon = this.getTalon(talonId);
        if(talon != null && !activities.contains(talon.getActivity())){
            Activity former = talon.getActivity();
            talon.setActivity(activity);
            if (former==Activity.NON_ACTIVE && activity==Activity.ACTIVE) {
                Patient patient = patientService.getPatient(talon.getPatientId());
                LocalDateTime sa = patient.getStartActivity();
                if (sa == null || sa.toLocalDate().isBefore(LocalDate.now())) {
                    patient.setStartActivity(LocalDateTime.now());
                    patient.setLastActivity(LocalDateTime.now());
                }
                patientService.savePatient(patient);
            }
        }
        return repository.save(talon);
    }


    public List<Talon> setAllActivity(String patientId, Activity activity) {
/*

        List<Activity> activities = new ArrayList<>(
                Arrays.asList(Activity.E, Activity.NON_ACTIVE, Activity.TEMPORARY_NA)
        );
*/

        List<Talon> talons = this.getTalonsForToday().stream()
                .filter(talon -> talon.getPatientId().equals(patientId))
             //   .filter(talon -> !activities.contains(talon.getActivity()))
                .collect(Collectors.toList());

        talons.stream().forEach(talon -> this.setActivity(talon.getId(), activity));

        return null;
    }

    public List<Patient> toPatientList(List<Talon> talons){
        List<Patient> patients = new ArrayList<>();

        talons.stream().forEach(talon -> patients
                .add(patientService.getPatientWithTalons(talon.getPatientId()))
        );

        return patients.stream().sorted().collect(Collectors.toList());
    }









/*

    @Override
    public Talon createTalon(Talon talon) {
*/
/*        if (this.getAll().size()==0){talon.setId(5L);}

        if (talon.getId() == null){
           Long id = this.getAll().stream().mapToLong(Talon::getId).max().getAsLong() +1;
            talon.setId(id);
            System.out.println(talon.getId()
            );
        }*//*

        return repository.save(talon);
    }

    @Override
    public Talon getTalon(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Talon getTalonByPatientAndProcedure(ObjectId patientId, int procedureId) {
        Talon talon = this.getAll().stream().filter(tl -> tl.getPatientId()==patientId)
                .filter(tl->tl.getProcedure().getId()==procedureId)
                .findFirst().get();
        return talon;
    }

    @Override
    public Talon updateTalon(Talon talon) {
        return repository.save(talon);
    }

    @Override
    public Talon deleteTalon(ObjectId id) {
        Talon talon = this.getTalon(id);
        if (talon.getExecutionTime().equals(null)) {
            repository.deleteById(id);
            return talon;
        }else {
            return null;
        }
    }

    @Override
    public List<Talon> getAll() {
        return repository.findAll();
    }


    @Override
    public List<Talon> getTalonsForToday() {

        List<Talon>  talons =  repository.findAll()
                .stream().filter(talon ->talon.getDate().equals(LocalDate.now()) )
                .collect(Collectors.toList());

        return talons;
    }

    @Override
    public int getTodayIncome() {
        return this.getAll().stream().filter(talon -> talon.getSum()!=0)
                .mapToInt(Talon::getSum).sum();
    }

    //------------------------------- BUSINESS LOGIC -------------------------

    public List<Talon> createActiveTalonsFromTherapy(ObjectId patientId){

        Patient  patient = patientService.getPatient(patientId);
        if (patient.getTherapy() != null) return null;
        List<Talon> talons = new ArrayList<>();
        List<Procedure> procedures = new ArrayList<>();//= patient.getTherapy().getProcedures();


        for (Procedure procedure: procedures) {

            Talon talon = new Talon();

            talon.setPatientId(patientId);
            talon.setDate(LocalDate.now());
            talon.setProcedure(procedure);
            talon.setDoctor(null);

            // therapy must be obtained fron Talon service, no longer from patient.getTherapy()

          //  talon.setZones(patient.getTherapy().getZones());
           // talon.setDesc(patient.getTherapy().getNotes());
            talon.setExecutionTime(null);
            talon.setDoctor(null);
            talon.setSum(0);


            talons.add(talon);
        }

        return repository.saveAll(talons);
    }
    public Talon createSingleTalon(int patiendId, int procedureId, int zones, String desc
    ){
        Talon talon = new Talon();
        Procedure procedure = procedureServicee.getProcedure(procedureId);

        talon.setPatientId(patiendId);
        talon.setDate(LocalDate.now());
        talon.setProcedure(procedure);
        ////////// xones must be injected by registrator
        talon.setZones(zones);
        talon.setDesc(desc);

        return this.createTalon(talon);
    }

    public Talon executeTalon(ObjectId talonId, int doctorId){
        Talon talon = this.getTalon(talonId);
        Doctor doctor = doctorService.getDoctor(doctorId);
        Patient patient = patientService.getPatient(talon.getPatientId());


        talon.setDoctor(doctor);
        talon.setExecutionTime(LocalDateTime.now());
        int price = this.getCorrectPrice(patient,talon.getProcedure());
        if (talon.getZones()==0) talon.setZones(1);
        int sum = price * talon.getZones();
        talon.setSum(sum);

        return this.updateTalon(talon);
    }

    int getCorrectPrice(Patient patient, Procedure procedure){
        int price = procedure.getPrice();
        switch (patient.getStatus()) {
            case SOCIAL: price = procedure.getPrice();
            break;
            case ALL_INCLUSIVE: procedure.getAllInclusive();
            break;
            case BUSINESS: procedure.getBusiness();
            break;
            case VIP: procedure.getVip();
            break;
            case FOREIGNER: procedure.getForeigner();
            break;
        }


        return price;
    }


    public int calculateTotalSumForPatient(ObjectId patientId, LocalDate start, LocalDate finish){
       //  int sum = this.getAll()
        return 0;
    }


   public List<Talon> getAllTalonsForPatient(ObjectId patientId,LocalDate date){
        return this.getAll().stream().filter(talon -> talon.getDate().equals(date))
                .filter(talon -> talon.getPatientId()==patientId).collect(Collectors.toList());
   }


    List<Talon> getAllTalonsForPatienForToday(ObjectId patientId){
       return getAllTalonsForPatient(patientId, LocalDate.now());
    }

*/
}
