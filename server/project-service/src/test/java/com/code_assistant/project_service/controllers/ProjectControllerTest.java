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

        User user = new User();
        user.setId(1L);
        user.setPrenom("John");
        when(projectRepository.findById(1L)).thenReturn(java.util.Optional.of(project));
        when(userService.userById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/projects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.artifact").value("com.example.demo"))
                .andExpect(jsonPath("$.description").value("Demo Project"))
                .andExpect(jsonPath("$.groupName").value("Demo Group"));

        verify(projectRepository, times(1)).findById(1L);
        verify(userService, times(1)).userById(1L);
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(projectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/projects/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll_Success() throws Exception {
        // Arrange
        Project project = new Project();
        project.setId(1L);
        project.setUserId(1L);
        project.setArtifactId("com.example.demo");
        project.setDescription("Demo Project");
        project.setGroupId("Demo Group");
        when(projectRepository.findAll()).thenReturn(List.of(project));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].artifact").value("com.example.demo"))
                .andExpect(jsonPath("$[0].description").value("Demo Project"))
                .andExpect(jsonPath("$[0].groupName").value("Demo Group"));

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testFindByUser_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setPrenom("John");
        Project project = new Project();
        project.setId(1L);
        project.setUserId(1L);
        project.setArtifactId("com.example.demo");
        project.setDescription("Demo Project");
        project.setGroupId("Demo Group");

        when(userService.userById(1L)).thenReturn(user);
        when(projectRepository.findByUserId(1L)).thenReturn(List.of(project));

        mockMvc.perform(get("/api/projects/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].artifact").value("com.example.demo"))
                .andExpect(jsonPath("$[0].description").value("Demo Project"))
                .andExpect(jsonPath("$[0].groupName").value("Demo Group"));

        verify(userService, times(1)).userById(1L);
        verify(projectRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testFindByUser_NotFound() throws Exception {
        // Arrange
        when(userService.userById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/projects/user/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).userById(1L);
    }

    @Test
    void testSave_Success() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setArtifactId("com.example.demo");
        projectDto.setDescription("Demo Project");
        projectDto.setGroupId("Demo Group");

        ProjectDto savedProjectDto = new ProjectDto();
        savedProjectDto.setArtifactId("com.example.demo");
        savedProjectDto.setDescription("Demo Project");
        savedProjectDto.setGroupId("Demo Group");

        when(projectService.save(projectDto)).thenReturn(savedProjectDto);

        mockMvc.perform(post("/api/projects/save")
                        .contentType("application/json")
                        .content("{\"artifact\":\"com.example.demo\",\"description\":\"Demo Project\",\"groupName\":\"Demo Group\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.artifact").value("com.example.demo"))
                .andExpect(jsonPath("$.description").value("Demo Project"))
                .andExpect(jsonPath("$.groupName").value("Demo Group"));

        verify(projectService, times(1)).save(projectDto);
    }
    @Test
    void testFindById_InternalServerError() throws Exception {
        when(projectRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/projects/{id}", 1L))
                .andExpect(status().isInternalServerError());

        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindAll_InternalServerError() throws Exception {
        when(projectRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isInternalServerError());

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testFindByUser_NoProjectsFound() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPrenom("John");

        when(userService.userById(1L)).thenReturn(user);
        when(projectRepository.findByUserId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/projects/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(userService, times(1)).userById(1L);
        verify(projectRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testFindById_InvalidId() throws Exception {
        mockMvc.perform(get("/api/projects/{id}", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindByUser_InvalidId() throws Exception {
        mockMvc.perform(get("/api/projects/user/{id}", "invalid"))
                .andExpect(status().isBadRequest());
    }

}
