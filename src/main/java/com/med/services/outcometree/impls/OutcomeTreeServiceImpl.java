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
}
