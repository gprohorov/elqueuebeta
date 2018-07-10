package com.med.services.card.interfaces;

import com.med.model.Card;

/**
 * Created by george on 3/9/18.
 */
public interface ICardService  {
    Card saveCard(Card card);
    Card getCard(int id);
    Card getCardByProcedureId(int procedureId);

    Card saveCard(int procedureId, Card card);


}
