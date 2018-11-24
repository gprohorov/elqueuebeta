package com.med.services.outcometree.impls;

import com.med.model.CashBox;
import com.med.model.OutcomeTree;
import com.med.model.OutcomeTreeSum;
import com.med.repository.outcometree.OutcomeTreeRepository;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import com.med.services.outcometree.interfaces.iOutcomeTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by george on 22.11.18.
 */
@Service
public class OutcomeTreeServiceImpl implements iOutcomeTreeService {

    @Autowired
    OutcomeTreeRepository repository;

    @Autowired
    CashBoxServiceImpl cashBoxService;


    @Override
    public List<OutcomeTree> getTree() {
        List<OutcomeTree> list = repository.findAll();
        return list;
    }

    @Override
    public OutcomeTree getNode(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OutcomeTree createNode(OutcomeTree node) {
        if (!node.getId().isEmpty() || node.getName().isEmpty()) return null;
        if ( !node.getCatID().equals(null) && !this._isCategory(node.getCatID()) ) return null;
        return repository.save(node);
    }

    @Override
    public OutcomeTree updateNode(OutcomeTree node) {
        OutcomeTree src = this.getNode(node.getId());
        if ( node.getName().isEmpty() ) return null;
        if ( src.equals(null) ) return null;
        if ( ( src.getCatID().equals(null) && !node.getCatID().equals(null) )
            || ( !src.getCatID().equals(null) && node.getCatID().equals(null) ) ) return null;
        if ( !node.getCatID().equals(src.getCatID())
            && !this._isCategory(node.getCatID()) ) return null;
        return repository.save(node);
    }

    private Boolean _isCategory(String id) {
        OutcomeTree test = this.getNode(id);
        return ( !test.equals(null) && test.getCatID().equals(null) );
    }

    public List<OutcomeTreeSum> getOutcomeListAsTree(LocalDate from,LocalDate to){
        //TODO: Refactor
       List<CashBox> outcomes = cashBoxService.getAll().stream()
               .filter(cash->cash.getSum()<0)
               .filter(cash->cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
               .filter(cash->cash.getDateTime().toLocalDate().isBefore(to. plusDays(1)))
               .collect(Collectors.toList());

        List<OutcomeTreeSum> list = new ArrayList<>();

        List<OutcomeTree> tree = this.getTree();
        List<String> ids = this.getTree().stream()
                .filter(node->node.getCatID()==null)
                .map(node->node.getId())
                .collect(Collectors.toList());

        ids.stream().forEach(id->{
            OutcomeTreeSum rootSummary = new OutcomeTreeSum();
            rootSummary.setCategory(null);
            rootSummary.setName(this.getNode(id).getName());
            long sum = outcomes.stream()
                    .filter(outcome->outcome.getCatId().equals(id))
                    .mapToInt(CashBox::getSum).sum();
             rootSummary.setSum(sum);

            list.add(rootSummary);
        });

        return list;
    }

    public List<OutcomeTreeSum> getOutcomeSummaryOfCategory(String category, LocalDate from, LocalDate to) {

        //TODO: Refactor
        List<CashBox> outcomes = cashBoxService.getAll().stream()
                .filter(cash->cash.getSum()<0)
                .filter(cash->cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
                .filter(cash->cash.getDateTime().toLocalDate().isBefore(to. plusDays(1)))
                .filter(cash->cash.getCatId().equals(category))
                .collect(Collectors.toList());

        List<OutcomeTreeSum> list = new ArrayList<>();

        List<OutcomeTree> items = this.getTree().stream()
                .filter(node->node.getCatID()==category).collect(Collectors.toList());
        items.stream().forEach(item->{
            OutcomeTreeSum element = new OutcomeTreeSum();
            element.setName(item.getName());
            element.setCategory(category);
            long sum = outcomes.stream().mapToInt(CashBox::getSum).sum();
            element.setSum(sum);
            list.add(element);
        });
        return list;
    }

    public List<CashBox> getOutcomeSummaryOfItem(String item, LocalDate from, LocalDate to) {
        return cashBoxService.getAll().stream()
                .filter(cash->cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
                .filter(cash->cash.getDateTime().toLocalDate().isBefore(to.plusDays(1)))
                .filter(cash->cash.getItem().equals(item))
                .collect(Collectors.toList());
    }
}
