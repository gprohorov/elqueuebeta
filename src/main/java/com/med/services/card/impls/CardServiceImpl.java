package com.med.services.card.impls;

import com.med.dao.card.impls.CardDAOImpl;
import com.med.model.Card;
import com.med.services.card.interfaces.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Component
public class CardServiceImpl  implements ICardService{


    private List<Card> cards = new ArrayList<>();


    @Autowired
    CardDAOImpl cardDAO;

    @PostConstruct
    void init() {
        // generics = dataStorage.getCards();
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
        return cardDAO.getCard(id);
    }

    @Override
    public Card deleteCard(int id) {
        return null;
    }

    @Override
    public List<Card> getAll() {
        return cardDAO.getAll();
    }
}
