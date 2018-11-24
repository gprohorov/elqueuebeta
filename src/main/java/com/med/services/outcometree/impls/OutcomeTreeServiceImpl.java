package com.med.services.outcometree.impls;

import com.med.model.OutcomeTree;
import com.med.repository.outcometree.OutcomeTreeRepository;
import com.med.services.outcometree.interfaces.iOutcomeTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by george on 22.11.18.
 */
@Service
public class OutcomeTreeServiceImpl implements iOutcomeTreeService {

    @Autowired
    OutcomeTreeRepository repository;

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
}
