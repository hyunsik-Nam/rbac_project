package com.project.rbac.global.exception;

import com.project.rbac.global.constants.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(CustomBadCredentialsException e) {
        return buildErrorResponseMessage(HttpStatus.UNAUTHORIZED,e.getMessage());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationFailedException(DuplicateUserException e){
        return buildErrorResponseMessage(HttpStatus.UNAUTHORIZED,e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 오류 메시지들을 하나의 문자열로 모으기 위한 StringBuilder 사용
        StringBuilder messageBuilder = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            messageBuilder.append(error.getDefaultMessage())
                    .append(", ");
        });

        // 마지막에 있는 불필요한 쉼표 제거
        if (!messageBuilder.isEmpty()) {
            messageBuilder.setLength(messageBuilder.length() - 2);
        }

        return buildErrorResponseMessage(HttpStatus.UNAUTHORIZED,messageBuilder.toString());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        return buildErrorResponseEnum(HttpStatus.UNAUTHORIZED,ExceptionMessage.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponseEnum(HttpStatus.UNAUTHORIZED,ExceptionMessage.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponseEnum(HttpStatus.UNAUTHORIZED,ExceptionMessage.ACCESS_DENIED_EXCEPTION);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponseEnum(HttpStatus.UNAUTHORIZED,ExceptionMessage.AUTHENTICATION_EXCEPTION);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        return buildErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildErrorResponseEnum(HttpStatus status, ExceptionMessage e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());

        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponseMessage(HttpStatus status, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);

        return ResponseEntity.status(status).body(errorResponse);
    }

}
