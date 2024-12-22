package com.code_assistant.project_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String artifact;
    private String groupName;
    private String description;
    private Long userId;
    @Transient
    User userfield;
}
