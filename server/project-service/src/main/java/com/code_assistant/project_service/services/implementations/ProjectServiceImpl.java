package com.code_assistant.project_service.services.implementations;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.helper.ProjectMapper;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Override
    public ProjectDto save(ProjectDto projectDto) {
        return ProjectMapper.map(projectRepository.save(ProjectMapper.map(projectDto)));
    }
}
