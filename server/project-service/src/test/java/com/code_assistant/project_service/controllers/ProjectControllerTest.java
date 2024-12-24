package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void testFindById_Success() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setUserId(1L);
        project.setArtifactId("com.example.demo");
        project.setDescription("Demo Project");
        project.setGroupId("Demo Group");
        project.setJavaVersion("11");
        project.setLanguage("Java");
        project.setPackaging("Jar");
        project.setBootVersion("2.7.0");
        project.setBaseDir("/demo");
        project.setName("DemoApp");
        project.setDependencies("Spring Web, Spring Data JPA");

        User user = new User();
        user.setId(1L);
        user.setPrenom("John");

        when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(project));
        when(userService.userById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/projects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.artifactId").value("com.example.demo"))
                .andExpect(jsonPath("$.description").value("Demo Project"))
                .andExpect(jsonPath("$.groupId").value("Demo Group"))
                .andExpect(jsonPath("$.javaVersion").value("11"))
                .andExpect(jsonPath("$.language").value("Java"))
                .andExpect(jsonPath("$.packaging").value("Jar"))
                .andExpect(jsonPath("$.bootVersion").value("2.7.0"))
                .andExpect(jsonPath("$.baseDir").value("/demo"))
                .andExpect(jsonPath("$.name").value("DemoApp"))
                .andExpect(jsonPath("$.dependencies").value("Spring Web, Spring Data JPA"));

        verify(projectRepository, times(1)).findById(1L);
        verify(userService, times(1)).userById(1L);
    }

    @Test
    void testSave_Success() throws Exception {
        ProjectDto projectDto = ProjectDto.builder()
                .artifactId("com.example.demo")
                .description("Demo Project")
                .groupId("Demo Group")
                .javaVersion("11")
                .language("Java")
                .packaging("Jar")
                .bootVersion("2.7.0")
                .baseDir("/demo")
                .name("DemoApp")
                .dependencies("Spring Web, Spring Data JPA")
                .build();

        ProjectDto savedProjectDto = ProjectDto.builder()
                .id(1L)
                .artifactId("com.example.demo")
                .description("Demo Project")
                .groupId("Demo Group")
                .javaVersion("11")
                .language("Java")
                .packaging("Jar")
                .bootVersion("2.7.0")
                .baseDir("/demo")
                .name("DemoApp")
                .dependencies("Spring Web, Spring Data JPA")
                .build();

        when(projectService.save(projectDto)).thenReturn(savedProjectDto);

        mockMvc.perform(post("/api/projects/save")
                        .contentType("application/json")
                        .content("""
                                {
                                  "artifactId": "com.example.demo",
                                  "description": "Demo Project",
                                  "groupId": "Demo Group",
                                  "javaVersion": "11",
                                  "language": "Java",
                                  "packaging": "Jar",
                                  "bootVersion": "2.7.0",
                                  "baseDir": "/demo",
                                  "name": "DemoApp",
                                  "dependencies": "Spring Web, Spring Data JPA"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.artifactId").value("com.example.demo"))
                .andExpect(jsonPath("$.description").value("Demo Project"))
                .andExpect(jsonPath("$.groupId").value("Demo Group"))
                .andExpect(jsonPath("$.javaVersion").value("11"))
                .andExpect(jsonPath("$.language").value("Java"))
                .andExpect(jsonPath("$.packaging").value("Jar"))
                .andExpect(jsonPath("$.bootVersion").value("2.7.0"))
                .andExpect(jsonPath("$.baseDir").value("/demo"))
                .andExpect(jsonPath("$.name").value("DemoApp"))
                .andExpect(jsonPath("$.dependencies").value("Spring Web, Spring Data JPA"));

        verify(projectService, times(1)).save(projectDto);
    }
}

