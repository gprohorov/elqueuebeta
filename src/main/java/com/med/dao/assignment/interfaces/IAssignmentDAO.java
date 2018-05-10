package com.med.dao.assignment.interfaces;

import com.med.model.Assignment;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
public interface IAssignmentDAO {
    Assignment createAssignment(Assignment assignment);
    Assignment updateAssignment(Assignment assignment);
    Assignment getAssignment(int id);
    Assignment deleteAssignment(int id);
    List<Assignment> getAll();
}
