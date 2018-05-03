package com.med.dao.therapy.interfaces;

import com.med.model.Therapy;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITherapyDAO {
    Therapy createTherapy(Therapy therapy);
    Therapy updateTherapy(Therapy therapy);
    Therapy getTherapy(ObjectId id);
    Therapy deleteTherapy(ObjectId id);
    List<Therapy> getAll();
}
