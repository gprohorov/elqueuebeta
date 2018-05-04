package com.med.dao.assignment.impls;

import com.med.DataStorage;
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
@SuppressWarnings("ALL")
@Component
public class AssignmentDAOImpl implements IAssignmentDAO {


    private List<Assignment> assignments = new ArrayList<>();

    @Autowired
    DataStorage dataStorage;

    @PostConstruct
    void init(){
       // assignments = dataStorage.getAssignments();
    }


    @Override
    public Assignment createAssignment(Assignment assignment) {
        return null;
    }

    @Override
    public Assignment updateAssignment(Assignment assignment) {
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
