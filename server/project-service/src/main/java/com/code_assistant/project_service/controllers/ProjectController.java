package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.AIService;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AIService aiService;
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
    @PostMapping("gemini")
    public String communicateWithAi(@RequestBody String userInput) {

        return aiService.chat(userInput);
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
        String url = "https://start.spring.io/starter.zip?groupId=" + groupId + "&artifactId=" + artifactId
                + "&name=" + name + "&description=" + description + "&packageName=" + packageName + "&packaging=" + packaging
                + "&javaVersion=" + javaVersion + "&type=" + type + "&language=" + language + "&bootVersion=" + bootVersion
                + "&baseDir=" + baseDir  + "&dependencies=" + dependencies;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        byte[] content = inputStream.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
