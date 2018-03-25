package com.med.dao.procedure.interfaces;

import com.med.model.Procedure;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IProcedureDAO {
    Procedure createProcedure(Procedure procedure);
    Procedure updateProcedure(Procedure procedure);
    Procedure getProcedure(int id);
    Procedure deleteProcedure(int id);
    List<Procedure> getAll();
}
