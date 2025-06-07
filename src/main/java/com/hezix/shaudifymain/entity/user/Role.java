package com.hezix.shaudifymain.entity.user;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum Role implements GrantedAuthority, Serializable {
    USER, ADMIN, AUTHOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
