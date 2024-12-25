package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.SpringInitializerService;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import io.jsonwebtoken.io.IOException;
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
    @Autowired
    private SpringInitializerService springInitializerService;

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

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateProject(
            @RequestParam(value = "groupId", defaultValue = "com.example") String groupId,
            @RequestParam(value = "artifactId", defaultValue = "demo") String artifactId,
            @RequestParam(value = "name", defaultValue = "demo") String name,
            @RequestParam(value = "description", defaultValue = "Demo project for Spring Boot") String description,
            @RequestParam(value = "packageName", defaultValue = "com.example.demo") String packageName,
            @RequestParam(value = "packaging", defaultValue = "jar") String packaging,
            @RequestParam(value = "javaVersion", defaultValue = "17") String javaVersion,
            @RequestParam(value = "type", defaultValue = "gradle-project") String type,
            @RequestParam(value = "language", defaultValue = "java") String language,
            @RequestParam(value = "bootVersion", defaultValue = "3.4.1") String bootVersion,
            @RequestParam(value = "baseDir", defaultValue = "demo") String baseDir,
            @RequestParam(value = "dependencies", required = false) String dependencies
    ) throws IOException, java.io.IOException {
       return  springInitializerService.downloadAndModifyZip(groupId, artifactId, name, description, packageName,
               packaging, javaVersion, type, language, bootVersion, baseDir, dependencies);
    }
}
