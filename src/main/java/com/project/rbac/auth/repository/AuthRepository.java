package com.project.rbac.auth.repository;

import com.project.rbac.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);

}
