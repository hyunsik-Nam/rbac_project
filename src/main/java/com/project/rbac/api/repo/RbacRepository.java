package com.project.rbac.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.rbac.api.entity.Project;

@Repository
public interface RbacRepository extends JpaRepository<Project, Long>{
    
}
