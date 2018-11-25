package com.med.services.outcometree.impls;

import com.med.model.CashBox;
import com.med.model.OutcomeTree;
import com.med.model.OutcomeTreeSum;
import com.med.repository.outcometree.OutcomeTreeRepository;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.outcometree.interfaces.iOutcomeTreeService;
import com.med.services.patient.Impls.PatientServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;

import java.awt.Window.Type;
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
    
    @Autowired
    DoctorServiceImpl doctorService;
    
    @Autowired
    PatientServiceImpl patientService;

    @Override
    public List<OutcomeTree> getTree() {
    	List<OutcomeTree> tree = repository.findAll().stream().filter( node -> node.getCatID() == null )
        		.collect(Collectors.toList());
        tree.forEach( node -> {
        	List<OutcomeTree> items = this._getItemsByCatID(node.getId());
        	node.setItems(items);
        });

        tree.add(new OutcomeTree("ІНШЕ (спеціальна категорія)", null));
        
        return tree;
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

    			// TODO: update CashBox rows here ...
    			
    			repository.delete(item);
    		}); 
    	} else {
    		// TODO: update CashBox rows here ...
    	}
        repository.delete(node);
        return true;
    }
    
    public List<OutcomeTree> getTreeSum(LocalDate from, LocalDate to) {
    	
    	// TODO: Make it By MongoRepository
    	List<CashBox> outcomes = cashBoxService.getAll().stream()
    			.filter(cash->cash.getSum()<0)
    			.filter(cash->cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
    			.filter(cash->cash.getDateTime().toLocalDate().isBefore(to. plusDays(1)))
    			.collect(Collectors.toList());
    	
    	final long[] totalSum = {0};
    	
    	List<OutcomeTree> tree = repository.findAll().stream().filter( node -> node.getCatID() == null )
    			.collect(Collectors.toList());
    	tree.forEach( node -> {
    		List<OutcomeTree> items = this._getItemsByCatID(node.getId());
    		totalSum[0] = 0;
    		items.forEach(item -> {
    			item.setSum(outcomes.stream()
    					.filter( outcome-> item.getId().equals( outcome.getItemId() ) )
    					.mapToLong(CashBox::getSum).sum());
    			totalSum[0] += item.getSum();
    		});
    		node.setSum(totalSum[0]);
    		node.setItems(items);
    	});
    	
    	tree.add(new OutcomeTree("ІНШЕ (спеціальна категорія)", null, outcomes.stream()
    			.filter( o -> o.getItemId() == null).mapToLong(CashBox::getSum).sum()));
    	
    	return tree;
    }
    
    public List<CashBox> getOutcomeListOfItem(String itemId, LocalDate from, LocalDate to) {
    	// TODO: Make it By MongoRepository
    	List<CashBox> list = cashBoxService.getAll().stream()
			.filter(cash -> itemId.equals("null") ? cash.getItemId() == null : itemId.equals(cash.getItemId()) )
			.filter(cash -> cash.getDateTime().toLocalDate().isAfter(from.minusDays(1)))
			.filter(cash -> cash.getDateTime().toLocalDate().isBefore(to.plusDays(1)))
			.collect(Collectors.toList());
    	list.forEach(item -> {
    		if (item.getDoctorId() > 0) {
    			item.setDoctor( doctorService.getDoctor( item.getDoctorId() ).getFullName() );
    		}
    		if (item.getPatientId() != null) {
    			item.setPatient( 
					patientService.getPatient( item.getPatientId() ).getPerson().getFullName() );
    		}
    	});
    	return list;
    }

    private Boolean _isCategory(String id) {
        OutcomeTree test = this.getNode(id);
        return ( test != null && test.getCatID() == null );
    }
    
    private List<OutcomeTree> _getItemsByCatID(String id) {
    	return repository.findByCatID(id);
    }
}
