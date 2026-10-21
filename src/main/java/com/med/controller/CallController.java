package com.med.controller;


/*
  @author   george
  @project   elqueuebeta
  @class  CallController
  @version  1.0.0 
  @since 21/10/2026 - 17.50
*/

import com.med.model.InitCall;
import com.med.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/call-center")
@CrossOrigin("*")
public class CallController {

    @Autowired
    CallService callService;

    @GetMapping("/get-call/{internal}/{external}")
    public InitCall getCall( @PathVariable String external,
                           @PathVariable String internal) {

        return callService.getCall(internal, external);
    }



}
