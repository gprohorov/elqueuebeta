package com.med.services.therapy.impls;

import com.med.dao.therapy.interfaces.ITherapyDAO;
import com.med.model.Therapy;
import com.med.repository.therapy.TherapyRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TherapyServiceImpl implements ITherapyDAO {


    private List<Therapy> therapys = new ArrayList<>();



    @Autowired
    TherapyRepository repository;

    @PostConstruct
    void init(){
       // therapys = dataStorage.getTherapys();
    }


    @Override
    public Therapy createTherapy(Therapy therapy) {

        return repository.save(therapy);
    }

    @Override
    public Therapy updateTherapy(Therapy therapy) {
        return repository.save(therapy);
    }

    @Override
    public Therapy getTherapy(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Therapy deleteTherapy(ObjectId id) {
        return null;
    }

    @Override
    public List<Therapy> getAll() {
        return repository.findAll();
    }
}
