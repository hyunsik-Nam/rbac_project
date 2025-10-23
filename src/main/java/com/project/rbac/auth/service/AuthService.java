package com.project.rbac.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import com.project.rbac.auth.dto.AuthDTO;
import com.project.rbac.auth.entity.User;
import com.project.rbac.auth.repository.AuthRepository;
import com.project.rbac.global.constants.ExceptionMessage;
import com.project.rbac.global.exception.CustomBadCredentialsException;
import com.project.rbac.global.exception.DuplicateUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(AuthDTO authDTO) {
        // ✅ 1. 중복 회원 검사
        if (authRepository.existsByUserId(authDTO.getUserId())) {
            throw new DuplicateUserException(ExceptionMessage.EXIST_USER_ID.getMessage());
        }
        // ✅ User 엔티티로 변환 후 저장
        User user = User.of(authDTO,passwordEncoder);
        authRepository.save(user);
    }



    public String login(String userId, String password, HttpServletRequest request) {
        try {
            // 사용자 존재 확인
            User user = authRepository.findByUserId(userId)
                    .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID.getMessage()));

            // 인증 수행
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, password)
            );

            // ✅ SecurityContext에 인증 정보 저장
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            // ✅ 세션에 SecurityContext 저장
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            return "로그인 성공";

        } catch (UsernameNotFoundException e) {
            throw new CustomBadCredentialsException(e.getMessage());
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException(ExceptionMessage.WRONG_PASSWORD.getMessage());
        }
    }

    public void updateUserPermissions(Long userId, String newPermissions) {
        // 현재 인증된 사용자의 권한 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("A"))) {
            throw new SecurityException("권한이 없습니다. A 권한이 필요합니다.");
        }

        // 사용자 조회
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID.getMessage()));

        // 새로운 권한 설정 (예: 콤마로 구분된 문자열로 저장)
        user.setAuth(newPermissions);

        // 변경된 사용자 정보 저장
        authRepository.save(user);
    }

}
