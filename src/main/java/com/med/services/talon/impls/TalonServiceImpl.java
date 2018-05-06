package com.med.services.talon.impls;

import com.med.model.Doctor;
import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.interfaces.ITalonService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    ProcedureServiceImpl procedureServicee;

    @Autowired
    DoctorServiceImpl doctorService;



    @Override
    public Talon createTalon(Talon talon) {
/*        if (this.getAll().size()==0){talon.setId(5L);}

        if (talon.getId() == null){
           Long id = this.getAll().stream().mapToLong(Talon::getId).max().getAsLong() +1;
            talon.setId(id);
            System.out.println(talon.getId()
            );
        }*/
        return repository.save(talon);
    }

    @Override
    public Talon getTalon(ObjectId id) {
        return repository.findById(id).orElse(null);
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

        return repository.findAll()
                .stream().filter(talon ->talon.getDate().equals(LocalDate.now()) )
                .collect(Collectors.toList());
    }

    //------------------------------- BUSINESS LOGIC -------------------------

    public List<Talon> createActiveTalonsFromTherapy(int patientId){

        Patient  patient = patientService.getPatient(patientId);
        if (patient.getTherapy() != null) return null;
        List<Talon> talons = new ArrayList<>();
        List<Procedure> procedures = patient.getTherapy().getProcedures();


        for (Procedure procedure: procedures) {

            Talon talon = new Talon();

            talon.setPatientId(patientId);
            talon.setDate(LocalDate.now());
            talon.setProcedure(procedure);
            talon.setDoctor(null);
            talon.setZones(patient.getTherapy().getZones());
            talon.setDesc(patient.getTherapy().getNotes());
            talon.setExecutionTime(null);
            talon.setDoctor(null);
            talon.setSum(0);
            talon.setDuration(0);

            talons.add(talon);
        }

    /*    private ObjectId id;
        private int patientId;
        private LocalDate date;
        private Procedure procedure;
        private int zones;
        private String desc;
        private LocalDateTime executionTime;
        private Doctor doctor;
        private int sum;
        private int duration;

        */

        return repository.saveAll(talons);
    }
    public Talon createSingleTalon(int patiendId, int procedureId, int zones, String desc
    ){
        Talon talon = new Talon();
        Procedure procedure = procedureServicee.getProcedure(procedureId);

        talon.setPatientId(patiendId);
        talon.setDate(LocalDate.now());
        talon.setProcedure(procedure);
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
        int delta = (int) patient.getDelta();
        talon.setDuration(delta);
        int price = talon.getProcedure().getPrice();
        int sum = price * talon.getZones();
        talon.setSum(sum);

        return this.updateTalon(talon);
    }



}
