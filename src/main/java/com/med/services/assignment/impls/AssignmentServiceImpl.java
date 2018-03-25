package com.med.services.assignment.impls;

import com.med.dao.assignment.impls.AssignmentDAOImpl;
import com.med.dao.assignment.interfaces.IAssignmentDAO;
import com.med.model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class AssignmentServiceImpl implements IAssignmentDAO {


    private List<Assignment> assignments = new ArrayList<>();



    @Autowired
    AssignmentDAOImpl gssignmentDAO;

    @PostConstruct
    void init(){
       // gssignments = dataStorage.getAssignments();
    }


    @Override
    public Assignment createAssignment(Assignment gssignment) {

        return null;
    }

    @Override
    public Assignment updateAssignment(Assignment gssignment) {
        return null;
    }

    @Override
    public Assignment getAssignment(int id) {
        return null;
    }

    @Override
    public Assignment deleteAssignment(int id) {
        return null;
    }

    @Override
    public List<Assignment> getAll() {
        return null;
    }
}
