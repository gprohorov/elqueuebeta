package com.med.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by george on 27.04.18.
 */
public enum Role implements GrantedAuthority{
   // SUPERADMIN, ADMIN, CHIEF,  DOCTOR, PAT,
    USER {
       @Override
       public String getAuthority() {
           return name();
       }
   }
}
