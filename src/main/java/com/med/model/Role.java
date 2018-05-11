package com.med.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by george on 10.05.18.
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN {
        @Override
        public String getAuthority() {
            return name();
        }
    },
    ROLE_MASSAGE {
        @Override
        public String getAuthority() {
            return name();
        }
    },
    ROLE_USER {
        @Override
        public String getAuthority() {
            return name();
        }
    };
}
