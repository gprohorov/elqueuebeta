package com.med.services.card.impls;

import com.med.model.Card;
import com.med.repository.card.CardRepository;
import com.med.services.card.interfaces.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 3/9/18.
 */
@Component
public class CardServiceImpl  implements ICardService {


    @Autowired
    CardRepository repository;

    @Override
    public Card saveCard(Card card) {
        if (card.getId()==0){
         int id=   this.getAll().stream().mapToInt(Card::getId).max().getAsInt();
            card.setId(id);
        }
        return repository.save(card);
    }

    @Override
    public Card getCard(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Card getCardByProcedureId(int procedureId) {
        return repository.findByProcedureId(procedureId);
    }

    @Override
    public Card saveCard(int procedureId, Card card) {
        card.setId(procedureId);
        return repository.save(card);
    }

    public List<Card> getAll(){return repository.findAll();}

    public void saveAll(List<Card> cards){repository.saveAll(cards);}

    public List<Integer> getFreeProcedures(){
        return repository.findAll().stream().filter(card -> card.isAnytime()==true)
                .mapToInt(Card::getProcedureId).boxed().collect(Collectors.toList());
    }


}
