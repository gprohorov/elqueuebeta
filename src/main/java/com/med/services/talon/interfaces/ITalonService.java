package com.med.services.talon.interfaces;

import com.med.model.Patient;
import com.med.model.Talon;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITalonService {
    Talon createTalon(Talon talon);
    Talon updateTalon(Talon talon);
    Talon getTalon(long id);
    Talon deleteTalon(long id);
    List<Talon> getAllTalonsForPatientForToday(Patient patient);
    List<Talon> getAll();

}
