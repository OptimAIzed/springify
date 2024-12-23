package com.code_assistant.project_service.services.interfaces;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.helper.ProjectMapper;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.implementations.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    ProjectServiceImpl projectService;

    @Test
    void testFindProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setUserId(1L);
        project.setArtifact("com.example.demo");
        project.setDescription("demo project");
        project.setGroupName("group demo");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectDto result = projectService.findProjectById(1L);

        assertEquals(1L, result.getId());
        assertEquals("com.example.demo", result.getArtifact());
        assertEquals("demo project", result.getDescription());
        assertEquals("group demo", result.getGroupName());
    }
    @Test
    public void TestFindProjectsByUserId() {
        when(userService.userById(1L)).thenReturn(new User(1L,"ouabiba","hamza"));

        when(projectRepository.findByUserId(1L)).thenReturn(List.of());

        List<ProjectDto> projects = projectService.findByUserId(1L);

        assertTrue(projects.isEmpty(), "Projects list should be empty when the user is found but no projects are associated.");

        verify(userService, times(1)).userById(1L);
        verify(projectRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testSaveProject() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setArtifact("com.example.demo");
        projectDto.setDescription("Demo Project");
        projectDto.setGroupName("Demo Group");

        when(projectRepository.save(ProjectMapper.map(projectDto))).thenReturn(ProjectMapper.map(projectDto));

        ProjectDto savedProjectDto = projectService.save(projectDto);

        verify(projectRepository, times(1)).save(ProjectMapper.map(projectDto));

        assertNotNull(savedProjectDto, "Saved project should not be null.");
        assertEquals("com.example.demo", savedProjectDto.getArtifact());
        assertEquals("Demo Project", savedProjectDto.getDescription());
        assertEquals("Demo Group", savedProjectDto.getGroupName());
    }

}
