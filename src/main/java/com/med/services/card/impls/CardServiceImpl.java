package com.med.services.card.impls;

import com.med.model.Card;
import com.med.repository.card.CardRepository;
import com.med.services.card.interfaces.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by george on 3/9/18.
 */
@Component
public class CardServiceImpl  implements ICardService {


    @Autowired
    CardRepository repository;

    @Override
    public Card saveCard(Card card) {
        return repository.save(card);
    }

    @Override
    public Card saveCard(int procedureId, Card card) {
        card.setId(procedureId);
        return repository.save(card);
    }
}
