package com.code_assistant.project_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue
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
    @Column(columnDefinition = "BLOB")
    private byte[] image;
    @CreationTimestamp
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp lastModifiedDate;
    @Transient
    User userfield;

}
