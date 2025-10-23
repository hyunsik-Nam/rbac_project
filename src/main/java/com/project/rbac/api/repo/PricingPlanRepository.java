package com.project.rbac.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rbac.api.entity.PricingPlan;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
    
}
