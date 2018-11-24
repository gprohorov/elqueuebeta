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
        if ( node.getId() != null || node.getName().isEmpty()) return null;
        if ( node.getCatID() != null && !this._isCategory(node.getCatID()) ) return null;
        return repository.save(node);
    }

    @Override
    public OutcomeTree updateNode(OutcomeTree node) {
    	if ( node.getId() == null ) return null;
        OutcomeTree src = this.getNode(node.getId());
        if ( node.getName() == null || node.getName().isEmpty() ) return null;
        if ( src == null ) return null;
        if ( ( src.getCatID() == null && node.getCatID() != null )
            || ( src.getCatID() != null && node.getCatID() == null ) ) return null;
        if ( node.getCatID() != src.getCatID()
            && !this._isCategory(node.getCatID()) ) return null;
        return repository.save(node);
    }
    
    @Override
    public Boolean deleteNode(String id) {
    	OutcomeTree node = this.getNode(id);
    	if ( node == null ) return false;

    	if ( node.getCatID() == null ) {
    		// This is Category, so check for items
    		List<OutcomeTree> items = this._getItemsByCatID(node.getId());
    		items.stream().forEach(item -> {

    			// update CashBox rows here ...
    			
    			repository.delete(item);
    		}); 
    	} else {
    		// update CashBox rows here ...
    	}
        repository.delete(node);
        return true;
    }

    private Boolean _isCategory(String id) {
        OutcomeTree test = this.getNode(id);
        return ( test != null && test.getCatID() == null );
    }
    
    private List<OutcomeTree> _getItemsByCatID(String id) {
    	return repository.findByCatID(id);
    }

    public List<OutcomeTree> getOutcomeListAsTree(LocalDate from, LocalDate to) {
        //TODO: Refactor
    	List<CashBox> outcomes = cashBoxService.getAll().stream()
           .filter(cash->cash.getSum()<0)
           .filter(cash->cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
           .filter(cash->cash.getDateTime().toLocalDate().isBefore(to. plusDays(1)))
           .collect(Collectors.toList());

    	final long[] totalSum = {0};
    	
        List<OutcomeTree> tree = this.getTree().stream().filter( node -> node.getCatID() == null )
    		.collect(Collectors.toList());
        tree.forEach( node -> {
        	List<OutcomeTree> items = this._getItemsByCatID(node.getId());
        	totalSum[0] = 0;
        	items.forEach(item -> {
        		item.setSum(outcomes.stream()
                    .filter( outcome->outcome.getCatId().equals( item.getId() ) )
                    .mapToLong(CashBox::getSum).sum());
        		totalSum[0] += item.getSum();
        	});
        	node.setSum(totalSum[0]);
        	node.setItems(items);
        });

        // TODO: Constructor two...
        long sum = outcomes.stream().filter( o -> o.getCatId() == null)
    		.mapToLong(CashBox::getSum).sum();
        OutcomeTree nodeSpecial = new OutcomeTree("ІНШЕ (спеціальна категорія)", null);
        nodeSpecial.setSum(sum);
        tree.add(nodeSpecial);
        
        return tree;
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
