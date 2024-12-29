package com.code_assistant.project_service.helper;


import com.code_assistant.project_service.dto.ProjectDto;
import com.code_assistant.project_service.entities.Project;

public class
ProjectMapper {
	public static ProjectDto map(final Project project) {
		return ProjectDto.builder()
				.id(project.getId())
				.name(project.getName())
				.artifactId(project.getArtifactId())
				.groupId(project.getGroupId())
				.packageName(project.getPackageName())
				.javaVersion(project.getJavaVersion())
				.dependencies(project.getDependencies())
				.bootVersion(project.getBootVersion())
				.baseDir(project.getBaseDir())
				.language(project.getLanguage())
				.packageName(project.getPackageName())
				.packaging(project.getPackaging())
				.description(project.getDescription())
				.type(project.getType())
				.image(project.getImage())
				.userId(project.getUserId())
				.build();
	}

	public static Project map(final ProjectDto projectDto) {
		return Project.builder()
				.id(projectDto.getId())
				.name(projectDto.getName())
				.artifactId(projectDto.getArtifactId())
				.description(projectDto.getDescription())
				.groupId(projectDto.getGroupId())
				.packageName(projectDto.getPackageName())
				.javaVersion(projectDto.getJavaVersion())
				.baseDir(projectDto.getBaseDir())
				.bootVersion(projectDto.getBootVersion())
				.language(projectDto.getLanguage())
				.packaging(projectDto.getPackaging())
				.type(projectDto.getType())
				.dependencies(projectDto.getDependencies())
				.image(projectDto.getImage())
				.userId(projectDto.getUserId())
				.build();
	}
}








