package com.med.services.outcometree.interfaces;

import com.med.model.OutcomeTree;

import java.util.List;

/**
 * Created by george on 22.11.18.
 */
public interface iOutcomeTreeService {
    List<OutcomeTree> getTree();
    OutcomeTree getNode(String id);
    OutcomeTree createNode(OutcomeTree node);
    OutcomeTree updateNode(OutcomeTree node);
    Boolean deleteNode(String id);
}
