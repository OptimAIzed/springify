package com.code_assistant.project_service.services.interfaces;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;

import java.util.List;

public interface ProjectService {
    ProjectDto save(ProjectDto projectDto);
    ProjectDto findProjectById(Long id);
    List<ProjectDto> findByUserId(Long id);
}
