package com.med.services.therapy.impls;

import com.med.dao.therapy.impls.TherapyDAOImpl;
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
@Component
public class TherapyServiceImpl implements ITherapyDAO {


    private List<Therapy> therapys = new ArrayList<>();



    @Autowired
    TherapyDAOImpl therapyDAO;

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
    public Therapy getTherapy(int id) {
        return therapyDAO.getTherapy(id);
    }

    @Override
    public Therapy deleteTherapy(int id) {
        return null;
    }

    @Override
    public List<Therapy> getAll() {
        return therapyDAO.getAll();
    }
}
