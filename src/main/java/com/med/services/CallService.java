package com.med.services;


/*
  @author   george
  @project   elqueuebeta
  @class  CallService
  @version  1.0.0 
  @since 21/10/2026 - 17.41
*/

import com.med.model.InitCall;
import com.med.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CallService {

    @Autowired
    UserService userService;

    @Autowired
    BinotelClient binotelClient;

    public Object getCall( String external) {
        InitCall initCall = new InitCall();
        String internal = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        initCall.setInternalNumber(internal);
        initCall.setExternalNumber(external);
        Object object = binotelClient.getCall(initCall);
      //  System.out.println(object);
        return object;
    }
}
