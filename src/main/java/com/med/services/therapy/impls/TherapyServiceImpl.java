package com.med.services.therapy.impls;

import com.med.model.Therapy;
import com.med.repository.therapy.TherapyRepository;
import com.med.services.talon.impls.TalonServiceImpl;
import com.med.services.therapy.interfaces.ITherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TherapyServiceImpl implements ITherapyService {

    private List<Therapy> therapys = new ArrayList<>();

    @Autowired
    TherapyRepository repository;

    @Autowired
    TalonServiceImpl talonService;

    public Therapy createTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy saveTherapy(Therapy therapy) {return repository.save(therapy);
    }

    public Therapy updateTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy getTherapy(String id) {
        return repository.findById(id).orElse(null);
    }

    public Therapy deleteTherapy(String id) {
        return null;
    }

    public List<Therapy> getAll() {
        return repository.findAll();
    }


    public Therapy finishTherapy(String therapyId) {
       Therapy therapy = this.getTherapy(therapyId);
       therapy.setFinish(LocalDateTime.now());

       return this.saveTherapy(therapy);
    }

    // TODO:  Human way
    public Therapy findTheActualTherapy(String patientId){

        return this.getAll().stream().filter(th->th.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Therapy::getStart).reversed())
                .findFirst().orElse(null);
    }

    public Therapy executeTherapy(Therapy therapy) {

        return null;
    }

/*
    // TODO:   more logic
    public List<Talon> assignTherapy(String therapyId) {

        Therapy therapy = this.getTherapy(therapyId);
      //  List<Procedure> procedures = therapy.getProcedures();
        List<Talon> talons = new ArrayList<>();
        int days = therapy.getDays();

        for (int i =0; i<days; i++){

           for (Procedure procedure:procedures) {

               Talon talon = new Talon(therapy.getPatientId()
                       , procedure
                       , LocalDate.now().plusDays(i));
               talon.setActivity(Activity.NON_ACTIVE);
               talons.add(talon);
           }

        }
        return talonService.saveTalons(talons);
    }
    */
}
