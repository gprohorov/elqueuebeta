package com.med.dao.therapy.impls;

import com.med.datastorage.DataStorageTest;
import com.med.dao.therapy.interfaces.ITherapyDAO;
import com.med.model.Therapy;
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
public class TherapyDAOImpl implements ITherapyDAO {


    private List<Therapy> therapys = new ArrayList<>();

    @Autowired
    DataStorageTest dataStorage;

    @PostConstruct
    void init(){
       // therapys = dataStorage.getTherapys();
    }


    @Override
    public Therapy createTherapy(Therapy therapy) {
        return null;
    }

    @Override
    public Therapy updateTherapy(Therapy therapy) {
        return null;
    }

    @Override
    public Therapy getTherapy(ObjectId therapyId) {

        return this.getAll().stream().filter(therapy -> therapy.getId()==therapyId)
                .findAny().get();
    }

    @Override
    public Therapy deleteTherapy(ObjectId id) {
        return null;
    }

    @Override
    public List<Therapy> getAll() {
        return dataStorage.getTherapies();
    }
}
