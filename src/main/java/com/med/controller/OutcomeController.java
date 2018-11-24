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
        return this.service.getTree();
    }

    @RequestMapping("/createnode/{node}")
    public Response addNode(@Valid @RequestBody OutcomeTree node) {
        return this.service.createNode(node).equals(null)
                ? new Response(false, "Error creating node!")
                : new Response(true, "OK");
    }

    @RequestMapping("/updatenode/{node}")
    public Response updateNode(@Valid @RequestBody OutcomeTree node) {
        return this.service.updateNode(node).equals(null)
               ? new Response(false, "Error updating node!")
               : new Response(true, "OK");
    }
    @RequestMapping("/summary/{from}/{to}")
    public List<OutcomeTreeSum> getTreeSummary(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to ){
        return service.getOutcomeListAsTree(LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/summary/{category}/{from}/{to}")
    public List<OutcomeTreeSum> getCategorySummary(
            @PathVariable(value = "category") String category,
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to ){
        return service.getOutcomeSummaryOfCategory(category,LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/summary/{item}/{from}/{to}")
    public List<CashBox> getItemBand(
            @PathVariable(value = "item") String item,
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to ){
        return service.getOutcomeSummaryOfItem(item,LocalDate.parse(from), LocalDate.parse(to));
    }



}