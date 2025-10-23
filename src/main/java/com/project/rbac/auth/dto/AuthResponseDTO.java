package com.project.rbac.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String message;
    private String status;
    private Map<String, String> errors;

    public static AuthResponseDTO withMessageAndStatus(String message, String status) {
        return new AuthResponseDTO(message, status, null);
    }

    public static AuthResponseDTO withMessageAndStatusAndToken(String message, String status, String accessToken,String refreshToken) {
        return new AuthResponseDTO(message, status, null);
    }

    public static AuthResponseDTO withError(Map<String, String> errors) {
        return new AuthResponseDTO(null, null, errors);
    }

    // Getter, Setter
}
