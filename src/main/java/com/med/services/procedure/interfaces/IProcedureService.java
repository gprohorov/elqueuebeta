package com.med.services.procedure.interfaces;

import com.med.model.Procedure;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IProcedureService {

    List<Procedure> getAll();
    Procedure saveProcedure(Procedure procedure);
    Procedure getProcedure(int procedureId);
    void deleteProcedure(int procedureId);
    Procedure updateProcedure(Procedure procedure);

}
