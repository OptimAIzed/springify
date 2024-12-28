package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.helper.ProjectMapper;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.AIService;
import com.code_assistant.project_service.services.SpringInitializerService;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
            @RequestParam(value = "dependencies", required = false) String dependencies,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "userId",required = false) Long userId
    ) throws IOException, java.io.IOException {
        HashMap<String, List<HashMap<String, String>>> content = new HashMap<>();
        byte[] imageBytes = null;
        if (image != null && !image.isEmpty()) {
            imageBytes = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            content = aiService.sendImage(artifactId, packageName, base64Image);
        }
        System.out.println(dependencies);
        // Creating the project :
        ProjectDto project = new ProjectDto();
        project.setImage(imageBytes);
        project.setName(name);
        project.setGroupId(groupId);
        project.setArtifactId(artifactId);
        project.setBaseDir(baseDir);
        project.setUserId(userId);
        project.setBootVersion(bootVersion);
        project.setJavaVersion(javaVersion);
        project.setLanguage(language);
        project.setDependencies(dependencies);
        project.setDescription(description);
        project.setPackageName(packageName);
        project.setPackaging(packaging);
        // Save
        projectService.save(project);
        return  springInitializerService.downloadAndModifyZip(groupId, artifactId, name, description, packageName,
                packaging, javaVersion, type, language, bootVersion, baseDir, dependencies, content);
    }

    @PostMapping(value = "/generate/dependencies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateProjectDependencies(
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException, java.io.IOException {
        if (image != null && !image.isEmpty()) {
            byte[] imageBytes = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String jsonResponse = aiService.generateDependencies(base64Image);

            return ResponseEntity.ok(jsonResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"No image provided\"}");
    }
    @DeleteMapping(value = "/deleteAll/{userid}")
    public ResponseEntity<Void> deleteAll(@PathVariable Long userid) {
        projectService.deleteAllProjects(userid);
        return ResponseEntity.noContent().build();
    }

}
