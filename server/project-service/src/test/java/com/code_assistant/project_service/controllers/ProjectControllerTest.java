package com.code_assistant.project_service.controllers;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.AIService;
import com.code_assistant.project_service.services.SpringInitializerService;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mockMvc;
    @Mock
    private SpringInitializerService springInitializerService;

    @Mock
    private AIService aiService;
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
    public void testGenerateProject_withImage() throws Exception {
        // Mock image file
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "image.png", "image/png", "image content".getBytes()
        );

        HashMap<String, List<HashMap<String, String>>> mockContent = new HashMap<>();
        when(aiService.sendImage(anyString(), anyString(), anyString())).thenReturn(mockContent);

        byte[] zipContent = "zip content".getBytes();
        when(springInitializerService.downloadAndModifyZip(
                anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(),
                any(HashMap.class))
        ).thenReturn(ResponseEntity.ok(zipContent));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/projects/generate")
                        .file(imageFile)
                        .param("groupId", "com.example")
                        .param("artifactId", "demo")
                        .param("name", "Test Project")
                        .param("description", "Test Description")
                        .param("packageName", "com.example.demo")
                        .param("packaging", "jar")
                        .param("javaVersion", "17")
                        .param("type", "gradle-project")
                        .param("language", "java")
                        .param("bootVersion", "3.4.1")
                        .param("baseDir", "demo")
                        .param("dependencies", "spring-web, spring-data-jpa")
                        .param("userId", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().bytes(zipContent)); // Assert the response content

        verify(aiService, times(1)).sendImage(anyString(), anyString(), anyString());
        verify(projectService, times(1)).save(any(ProjectDto.class));
        verify(springInitializerService, times(1)).downloadAndModifyZip(
                anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(),
                any(HashMap.class)
        );
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
    public void testGenerateProjectDependencies_withImage() throws Exception {
        // Mock behavior of aiService
        String mockJsonResponse = "{\"dependencies\": [\"spring-web\", \"spring-data-jpa\"]}";
        when(aiService.generateDependencies(anyString())).thenReturn(mockJsonResponse);

        // Create a mock MultipartFile
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "image.png", "image/png", "image content".getBytes()
        );

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/projects/generate/dependencies")
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.dependencies").isArray())
                .andExpect(jsonPath("$.dependencies[0]").value("spring-web"))
                .andExpect(jsonPath("$.dependencies[1]").value("spring-data-jpa"));

        // Verify the service method was called once
        verify(aiService, times(1)).generateDependencies(anyString());
    }
    @Test
    void testFindByUser_UserFound() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setPrenom("John");

        Project project1 = new Project();
        project1.setId(1L);
        project1.setUserId(userId);
        project1.setName("Project 1");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setUserId(userId);
        project2.setName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);

        when(userService.userById(userId)).thenReturn(user);
        when(projectRepository.findByUserId(userId)).thenReturn(projects);

        mockMvc.perform(get("/api/projects/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[1].name").value("Project 2"));

        verify(userService, times(1)).userById(userId);
        verify(projectRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testFindByUser_UserNotFound() throws Exception {
        Long userId = 1L;

        when(userService.userById(userId)).thenReturn(null);

        mockMvc.perform(get("/api/projects/user/{id}", userId))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).userById(userId);
    }

    @Test
    void testFindByUser_Exception() throws Exception {
        Long userId = 1L;

        when(userService.userById(userId)).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/projects/user/{id}", userId))
                .andExpect(status().isInternalServerError());

        verify(userService, times(1)).userById(userId);
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
    @Test
    public void testDeleteAll_ReturnsNoContent() throws Exception {
        Long userId = 1L;

        doNothing().when(projectService).deleteAllProjects(userId);

        mockMvc.perform(delete("/api/projects/deleteAll/{userid}", userId))
                .andExpect(status().isNoContent());

        verify(projectService).deleteAllProjects(userId);
    }
    @Test
    public void testDeleteAll_ProjectNotFound_ReturnsNoContent() throws Exception {
        Long userId = 1L;

        // Simulate that the method will not throw any exceptions (i.e., even if there are no projects to delete)
        doNothing().when(projectService).deleteAllProjects(userId);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/projects/deleteAll/{userid}", userId))
                .andExpect(status().isNoContent());  // Check for HTTP status 204 No Content

        // Verify that the service method was called
        verify(projectService).deleteAllProjects(userId);
    }
    @Test
    public void testFindById_notFound() throws Exception {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isNotFound());

        verify(projectRepository, times(1)).findById(projectId);
        verifyNoInteractions(userService);
    }
    @Test
    public void testFindById_exception() throws Exception {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isInternalServerError());

        verify(projectRepository, times(1)).findById(projectId);
        verifyNoInteractions(userService);
    }
}

