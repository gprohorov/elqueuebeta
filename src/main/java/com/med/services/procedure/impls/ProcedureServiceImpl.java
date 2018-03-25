package com.med.services.procedure.impls;

import com.med.dao.procedure.impls.ProcedureDAOImpl;
import com.med.dao.procedure.interfaces.IProcedureDAO;
import com.med.model.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */

@Service
public class ProcedureServiceImpl implements IProcedureDAO {

    @Autowired
    ProcedureDAOImpl procedureDAO;

    @Override
    public Procedure createProcedure(Procedure procedure) {
        return procedureDAO.createProcedure(procedure);
    }

    @Override
    public Procedure getProcedure(int id) {  return procedureDAO.getProcedure(id);
    }

    @Override
    public Procedure updateProcedure(Procedure procedure) {
        return procedureDAO.updateProcedure(procedure);
    }


    @Override
    public Procedure deleteProcedure(int id) {
        return procedureDAO.deleteProcedure(id);
    }

    @Override
    public List<Procedure> getAll() {
        return procedureDAO.getAll();
    }
}
