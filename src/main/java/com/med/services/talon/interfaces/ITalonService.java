package com.med.services.talon.interfaces;

import com.med.model.Talon;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ITalonService {
    Talon createTalon(Talon talon);
    Talon getTalon(ObjectId id);
    Talon deleteTalon(ObjectId id);
    List<Talon> getAll();

}
