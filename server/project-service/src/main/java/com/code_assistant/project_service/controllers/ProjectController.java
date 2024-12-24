package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        try {
            Project project = projectRepository.findById(id).orElse(null);
            if (project != null) {
                User user = userService.userById(project.getUserId());
                project.setUserfield(user);
                return ResponseEntity.ok(project);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Project>> findAll() {
        try {
            List<Project> projects = projectRepository.findAll();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> findByUser(@PathVariable Long id) {
        try {
            User user = userService.userById(id);
            if (user != null) {
                List<Project> projects = projectRepository.findByUserId(id);
                return ResponseEntity.ok(projects);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/save")
    public ResponseEntity<ProjectDto> save(@RequestBody  ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.projectService.save(projectDto));
    }
}
