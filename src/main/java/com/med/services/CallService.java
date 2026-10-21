package com.med.services;


/*
  @author   george
  @project   elqueuebeta
  @class  CallService
  @version  1.0.0 
  @since 21/10/2026 - 17.41
*/

import com.med.model.InitCall;
import org.springframework.stereotype.Service;

@Service
public class CallService {

    public InitCall getCall(String internal, String external) {
        InitCall initCall = new InitCall();
        initCall.setInternalNumber(internal);
        initCall.setExternalNumber(external);
        return initCall;
    }
}
