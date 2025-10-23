package com.project.rbac.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import com.project.rbac.auth.constants.ResponseStatus;
import com.project.rbac.auth.dto.AuthResponseDTO;
import com.project.rbac.auth.dto.AuthDTO;
import com.project.rbac.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "사용자인증 관련 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "회원정보를 입력받아 회원가입을 진행시킵니다.")
    public ResponseEntity<AuthResponseDTO> register(@Validated(AuthDTO.RegisterGroup.class) @RequestBody AuthDTO authDTO) {
        authService.registerUser(authDTO);

        AuthResponseDTO response = AuthResponseDTO.withMessageAndStatus(ResponseStatus.REGISTER_SUCCESS, ResponseStatus.SUCCESS);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    @Operation(summary = "로그인", description = "ID와 PWD를 입력받아 로그인을 진행시킵니다.")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO, HttpServletRequest request) {
        String result = authService.login(authDTO.getUserId(), authDTO.getPassword(), request);
        return ResponseEntity.ok(result);
    }

    
    @Operation(summary = "권한 변경", description = "사용자의 권한을 변경합니다.")
    @PatchMapping("/permissions/{userId}")
    public ResponseEntity<?> updateUserPermissions(@PathVariable Long userId, @RequestBody String newPermissions) {
        authService.updateUserPermissions(userId, newPermissions);
        return ResponseEntity.ok().build();
    }

}
