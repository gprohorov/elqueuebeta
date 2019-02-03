package com.med.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.med.model.Card;
import com.med.repository.CardRepository;

@Component
public class CardService {

    @Autowired
    CardRepository repository;

    public Card saveCard(Card card) {
        if (card.getId() == 0) {
            card.setId(this.getAll().stream().mapToInt(Card::getId).max().getAsInt());
        }
        return repository.save(card);
    }

    public Card getCard(int id) {
        return repository.findById(id).orElse(null);
    }

    public Card getCardByProcedureId(int procedureId) {
        return repository.findByProcedureId(procedureId);
    }

    public Card saveCard(int procedureId, Card card) {
        card.setId(procedureId);
        return repository.save(card);
    }

    public List<Card> getAll() {
    	return repository.findAll();
	}

    public void saveAll(List<Card> cards) {
    	repository.saveAll(cards);
	}

    public List<Integer> getFreeProcedures() {
        return repository.findAll().stream().filter(card -> card.isAnytime() == true)
            .mapToInt(Card::getProcedureId).boxed().collect(Collectors.toList());
    }
}