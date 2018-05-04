package com.med.dao.therapy.impls;

import com.med.DataStorage;
import com.med.dao.therapy.interfaces.ITherapyDAO;
import com.med.model.Therapy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@SuppressWarnings("ALL")
@Component
public class TherapyDAOImpl implements ITherapyDAO {


    private List<Therapy> therapys = new ArrayList<>();

    @Autowired
    DataStorage dataStorage;

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
    public Therapy getTherapy(int therapyId) {

        return this.getAll().stream().filter(therapy -> therapy.getId()==therapyId)
                .findAny().get();
    }

    @Override
    public Therapy deleteTherapy(int id) {
        return null;
    }

    @Override
    public List<Therapy> getAll() {
        return dataStorage.getTherapies();
    }
}
