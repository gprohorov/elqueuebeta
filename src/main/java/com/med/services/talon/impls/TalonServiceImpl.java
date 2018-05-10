package com.med.services.talon.impls;

import com.med.model.Patient;
import com.med.model.Procedure;
import com.med.model.Talon;
import com.med.repository.talon.TalonRepository;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.procedure.impls.ProcedureServiceImpl;
import com.med.services.talon.interfaces.ITalonService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Component
public class TalonServiceImpl implements ITalonService {

    @Autowired
    TalonRepository repository;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    ProcedureServiceImpl procedureServicee;



    @Override
    public Talon createTalon(Talon talon) {
        return repository.save(talon);
    }

    @Override
    public Talon getTalon(ObjectId id) {
        return repository.findById(id).orElse(null);
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

 //------------------------------- BUSINESS LOGIC -------------------------

    public Talon createActiveTalon(int patientId, int procedureId){

        Patient  patient = patientService.getPatient(patientId);
        Procedure procedure = procedureServicee.getProcedure(procedureId);
        Talon talon = new Talon(patient.getId(), procedure);

        talon.setDoctor(null);

        return null;
    }


}
