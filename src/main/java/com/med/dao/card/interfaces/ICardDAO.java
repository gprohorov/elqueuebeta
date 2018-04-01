package com.med.dao.card.interfaces;

import com.med.model.Card;

import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public interface ICardDAO {
    Card createCard(Card card);
    Card updateCard(Card card);
    Card getCard(int id);
    Card deleteCard(int id);
    List<Card> getAll();
}
