package com.med.services.workplace.interfaces;

import com.med.model.Workplace;

/**
 * Created by george on 17.05.18.
 */
public interface IWorkPlaceService {

    Workplace createWorkPlace(int doctorId, int procedureId);
}
