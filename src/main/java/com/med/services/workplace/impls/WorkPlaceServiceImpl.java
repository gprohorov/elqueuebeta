package com.med.services.workplace.impls;

import com.med.model.Workplace;
import com.med.services.workplace.interfaces.IWorkPlaceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 17.05.18.
 */
@Service
public class WorkPlaceServiceImpl implements IWorkPlaceService {

 private List<Workplace> workplaces = new ArrayList<>();


    @Override
    public Workplace createWorkPlace(int doctorId, int procedureId) {
        return null;
    }
}
