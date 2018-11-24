package com.med.controller;

import com.med.model.CashBox;
import com.med.model.OutcomeTree;
import com.med.model.OutcomeTreeSum;
import com.med.model.Response;
import com.med.services.outcometree.impls.OutcomeTreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 29.10.18.
 */
@RestController
@RequestMapping("/api/outcome")
@CrossOrigin("*")
public class OutcomeController {

    @Autowired
    OutcomeTreeServiceImpl service;

    @RequestMapping("/gettree")
    public List<OutcomeTree> getTree() {
        return service.getTree();
    }
    
    @RequestMapping("/gettree-sum/{from}/{to}")
    public List<OutcomeTree> getTreeSum(
    		@PathVariable(value = "from") String from,
    		@PathVariable(value = "to") String to ) {
    	return service.getTreeSum(LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/createnode/")
    public Response addNode(@Valid @RequestBody OutcomeTree node) {
        return this.service.createNode(node).equals(null)
                ? new Response(false, "Error creating node!")
                : new Response(true, "OK");
    }

    @RequestMapping("/updatenode/")
    public Response updateNode(@Valid @RequestBody OutcomeTree node) {
        return this.service.updateNode(node).equals(null)
               ? new Response(false, "Error updating node!")
               : new Response(true, "OK");
    }
    
    @RequestMapping("/deletenode/{id}")
    public Response deleteNode(@PathVariable(value = "id") String id) {
    	return (!this.service.deleteNode(id))
    			? new Response(false, "Error updating node!")
				: new Response(true, "OK");
    }

    @RequestMapping("/list/{item}/{from}/{to}")
    public List<CashBox> getItemBand(
            @PathVariable(value = "item") String item,
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to ){
        return service.getOutcomeListOfItem(item, LocalDate.parse(from), LocalDate.parse(to));
    }

}