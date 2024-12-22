package com.code_assistant.project_service.helper;


import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;

public class ProjectMapper {
	public static ProjectDto map(final Project project) {
		return ProjectDto.builder()
				.id(project.getId())
				.nom(project.getNom())
				.artifact(project.getArtifact())
				.description(project.getDescription())
				.groupName(project.getGroupName())
				.userId(project.getUserId())
				.build();
	}

	public static Project map(final ProjectDto projectDto) {
		return Project.builder()
				.id(projectDto.getId())
				.nom(projectDto.getNom())
				.artifact(projectDto.getArtifact())
				.description(projectDto.getDescription())
				.groupName(projectDto.getGroupName())
				.userId(projectDto.getUserId())
				.build();
	}
}








