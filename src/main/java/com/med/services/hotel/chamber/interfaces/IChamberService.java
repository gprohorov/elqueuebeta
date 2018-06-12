package com.med.services.hotel.chamber.interfaces;

import com.med.model.hotel.Chamber;

import java.util.List;

/**
 * Created by george on 01.06.18.
 */
public interface IChamberService {

    Chamber createChamber(Chamber chamber);
    List<Chamber> getAll();
    Chamber getChamber(int chamberId);
}
