package com.project.rbac.global.utils;

import jakarta.servlet.http.HttpServletResponse;
import com.project.rbac.global.constants.ExceptionMessage;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponseUtil {
    public static void setErrorResponse(HttpServletResponse response, HttpStatus status, ExceptionMessage exceptionMessage) {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        try {
            // JSON 형식으로 에러 메시지 작성
            response.getWriter().write("{\"message\": \"" + exceptionMessage.getMessage() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
