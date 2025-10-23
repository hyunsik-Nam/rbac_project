package com.project.rbac.api.service;

import org.springframework.stereotype.Service;

import com.project.rbac.api.entity.PricingPlan;
import com.project.rbac.api.entity.Project;
import com.project.rbac.api.repo.PricingPlanRepository;
import com.project.rbac.api.repo.RbacRepository;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RbacService {
    
    private final RbacRepository rbacRepository;
    private final PricingPlanRepository pricingPlanRepository;

    public void createProject(Project project) {
        PricingPlan pricingPlan = pricingPlanRepository.findById(project.getPricingPlan().getId())
            .orElseThrow(() -> new RuntimeException("Pricing plan not found"));
        project.setPricingPlan(pricingPlan);

        rbacRepository.save(project);
    }

    public Project getProject(Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        Project project = rbacRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("A".equals(role) || "D".equals(role)) {
                return project;
            }
        }

        throw new RuntimeException("Access denied");

    }

    public void deleteProject(Long projectId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (!"D".equals(role)) {
                rbacRepository.deleteById(projectId);
            } 
        }

        throw new RuntimeException("Access denied");


    }

    public void updateProject(Project project, Long projectId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("A".equals(role) || "B".equals(role)) {
                project.setId(projectId);
                rbacRepository.save(project);
            }
        }
    }
}
