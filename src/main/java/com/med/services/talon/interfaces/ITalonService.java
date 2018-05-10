package com.med.services.talon.interfaces;

import com.med.model.Talon;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface ITalonService {
    Talon createTalon(Talon talon);
    Talon getTalon(ObjectId id);
    Talon getTalonByPatientAndProcedure(int patientId, int procedureId);
    Talon updateTalon(Talon talon);
    Talon deleteTalon(ObjectId id);
    List<Talon> getAll();
    List<Talon>  getTalonsForToday();
    int getTodayIncome();


}
