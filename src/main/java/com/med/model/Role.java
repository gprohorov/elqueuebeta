package com.med.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by george on 27.04.18.
 */
public enum Role implements GrantedAuthority{
      SUPERADMIN {
        @Override
        public String getAuthority() {
            return name();
     }
    }, ADMIN {
        @Override
        public String getAuthority() {
            return name();
        }
    }, CHIEF {
        @Override
        public String getAuthority() {
            return null;
        }
    },  DOCTOR {
        @Override
        public String getAuthority() {
            return null;
        }
    }, PAT {
        @Override
        public String getAuthority() {
            return null;
        }
    }, USER {
       @Override
       public String getAuthority() {
           return name();
       }
   }
}
