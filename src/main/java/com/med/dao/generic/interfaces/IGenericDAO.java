package com.med.dao.generic.interfaces;

import com.med.model.Generic;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface IGenericDAO {
    Generic createGeneric(Generic generic);
    Generic updateGeneric(Generic generic);
    Generic getGeneric(int id);
    Generic deleteGeneric(int id);
    List<Generic> getAll();
}
