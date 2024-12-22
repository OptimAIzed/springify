package com.code_assistant.project_service.services.interfaces;

import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;

public interface ProjectService {
    ProjectDto save(ProjectDto projectDto);
}
