package com.project.rbac.auth.entity;

import jakarta.persistence.*;
import com.project.rbac.auth.dto.AuthDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id",nullable = false)
    private Long id;

    @Column(name="user_id", unique = true)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "auth", nullable = false)
    private String auth;

    // ✅ 정적 팩토리 메서드 추가
    public static User of(AuthDTO authDTO, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.userName = authDTO.getUserName();
        user.userId = authDTO.getUserId();
        user.password = authDTO.getPassword()!=null?passwordEncoder.encode(authDTO.getPassword()):null;
        user.auth = authDTO.getAuth();
        return user;
    }
}
