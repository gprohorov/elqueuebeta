package com.med.model;

/**
 * Created by george on 3/9/18.
 */
public enum Action {
    //Patient
    REGISTRATED,
    PUT_IN_DATE,
    PUT_IN_QUEUE,
    INVITED,
    HAS_COME,
    FINALIZED,
    SUSPEND,
    REMOVED,
    ADVANCE,
    PAY,


   //Doctor
    ABSENT,
    PRESENT,
    FREE,
    INVITING,
    WORKING,
    BUSY,
    DIAGNOSE,
    ASSIGNED,
    APPOINT,
    GET_APPOINTED
}
