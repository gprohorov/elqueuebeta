package com.med.controller;

import com.med.model.OutcomeTree;
import com.med.model.Response;
import com.med.services.outcometree.impls.OutcomeTreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
}