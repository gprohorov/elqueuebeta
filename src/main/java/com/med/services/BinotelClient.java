package com.med.services;


/*
  @author   george
  @project   elqueuebeta
  @class  BinotelClient
  @version  1.0.0 
  @since 21/10/2026 - 23.45
*/

import com.med.model.InitCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BinotelClient {
    private static final String BINOTEL_URL =
            "https://api.binotel.com/api/4.0/calls/internal-number-to-external-number.json";

    @Autowired
    RestTemplate restTemplate;

    public Object getCall(InitCall json) {
        Object object = restTemplate.postForObject(BINOTEL_URL, json, Object.class);
        System.out.println(object);
        return object ;
    }
}
