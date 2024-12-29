package com.code_assistant.project_service.services.implementations;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.helper.ProjectMapper;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import com.code_assistant.project_service.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserService userService;
    @Override
    public ProjectDto save(ProjectDto projectDto) {
        return ProjectMapper.map(projectRepository.save(ProjectMapper.map(projectDto)));
    }

    @Override
    public ProjectDto findProjectById(Long id) {
        return ProjectMapper.map(
                projectRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("No project found with ID: " + id))
        );
    }

    @Override
    public List<ProjectDto> findByUserId(Long id) {
        User user = userService.userById(id);
        if(user != null) {
            return projectRepository.findByUserId(id).stream()
                    .map(ProjectMapper::map)
                    .collect(Collectors.toList());
        }
        return null;
    }
    @Override
    @Transactional
    public void deleteAllProjects(Long userid) {
        projectRepository.deleteAllByUserId(userid);
    }
}
