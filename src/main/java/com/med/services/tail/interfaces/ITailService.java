package com.med.services.tail.interfaces;

import com.med.model.Patient;
import com.med.model.Tail;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITailService {


    List<Tail> getAll();

    List<Patient> getPatients(int procedureId);

    List<Patient> getAllPatientsActiveAndOnProcedure(int procedureId);



    Patient getFirstActive(int procedureId);

    Patient getFirstActiveAndOnProcedure(int procedureId);

    List<Patient> getPatientsOnProcedure(int procedureId);

}
