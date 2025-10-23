package com.project.rbac.global.exception;

import org.springframework.security.authentication.BadCredentialsException;

// Custom Exception 클래스
public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException(String message) {
        super(message);
    }
}