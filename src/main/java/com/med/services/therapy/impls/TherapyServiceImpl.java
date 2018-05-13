package com.med.services.therapy.impls;

import com.med.model.Therapy;
import com.med.repository.therapy.TherapyRepository;
import com.med.services.therapy.interfaces.ITherapyService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TherapyServiceImpl implements ITherapyService {

    private List<Therapy> therapys = new ArrayList<>();

    @Autowired
    TherapyRepository repository;

    public Therapy createTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy updateTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    public Therapy getTherapy(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    public Therapy deleteTherapy(ObjectId id) {
        return null;
    }

    public List<Therapy> getAll() {
        return repository.findAll();
    }
}
