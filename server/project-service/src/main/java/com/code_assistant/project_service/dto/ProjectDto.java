package com.code_assistant.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String artifactId;
    private String groupId;
    private String packageName;
    private String javaVersion;
    private String language;
    private String packaging;
    private String bootVersion;
    private String baseDir;
    private String description;
    private Long userId;
    private String type;
    private String dependencies;
    private byte[] image;
}
