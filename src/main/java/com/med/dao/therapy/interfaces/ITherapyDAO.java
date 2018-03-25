package com.med.dao.therapy.interfaces;

import com.med.model.Therapy;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITherapyDAO {
    Therapy createTherapy(Therapy therapy);
    Therapy updateTherapy(Therapy therapy);
    Therapy getTherapy(int id);
    Therapy deleteTherapy(int id);
    List<Therapy> getAll();
}
