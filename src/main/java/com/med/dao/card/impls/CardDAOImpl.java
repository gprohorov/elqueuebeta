package com.med.dao.card.impls;

import com.med.datastorage.DataStorageTest;
import com.med.dao.card.interfaces.ICardDAO;
import com.med.model.Card;
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
public class CardDAOImpl implements ICardDAO {


    private List<Card> cards = new ArrayList<>();

    @Autowired
    DataStorageTest dataStorage;

    @PostConstruct
    void init(){
       // cards = dataStorage.getCards();
    }


    @Override
    public Card createCard(Card card) {
        return null;
    }

    @Override
    public Card updateCard(Card card) {
        return null;
    }

    @Override
    public Card getCard(int id) {
       return this.getAll().stream().filter(card -> card.getId()==id).findAny().get();

    }

    @Override
    public Card deleteCard(int id) {
        return null;
    }

    @Override
    public List<Card> getAll() {
        return dataStorage.getCards();
    }
}
