package com.project.rbac.api.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.rbac.api.entity.Project;
import com.project.rbac.api.service.RbacService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Image Upload", description = "프로젝트 관리 API")
@RestController
@RequiredArgsConstructor
public class RbacController {

    private final RbacService rbacService;
    
    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성합니다.")
    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        rbacService.createProject(project);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "프로젝트 조회", description = "특정 프로젝트를 조회합니다.")
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = rbacService.getProject(id);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "프로젝트 수정", description = "특정 프로젝트를 수정합니다.")
    @PatchMapping("/projects/{id}")
    public ResponseEntity<?> updateProjectById(@RequestBody Project project,@PathVariable Long id) {
        rbacService.updateProject(project, id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "프로젝트 삭제", description = "특정 프로젝트를 삭제합니다.")
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long id) {
        rbacService.deleteProject(id);

        return ResponseEntity.ok().build();
    }
}
