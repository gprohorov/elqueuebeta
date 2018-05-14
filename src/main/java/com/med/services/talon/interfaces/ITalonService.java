package com.med.services.talon.interfaces;

import com.med.model.Talon;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITalonService {

    Talon createTalon(String patientId, int procedureId, int days);
    Talon createTalon(String patientId, int procedureId, LocalDate date);
    List<Talon> getAll();
    Talon getTalon(String id);
    List<Talon>  getTalonsForToday();
    List<Talon>  getTalonsForDate(LocalDate date);

/*

    Talon createTalon(Talon talon);

    Talon getTalonByPatientAndProcedure(int patientId, int procedureId);
    Talon updateTalon(Talon talon);
    Talon deleteTalon(ObjectId id);


    int getTodayIncome();
*/
}
