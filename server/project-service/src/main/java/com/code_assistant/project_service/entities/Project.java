package com.code_assistant.project_service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
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
    @ManyToOne
    User userfield;

    public Project(Long id,
                   String nom,
                   String artifact,
                   String group,
                   String description,
                   User user,
                   Long id_user
                ) {
                this.id = id;
                this.nom = nom;
                this.artifact = artifact;
                this.groupName = group;
                this.description = description;
                this.userfield = user;
                this.userId = id_user;
             }
    public Project() {
        this.id = null;
        this.nom = "";
        this.artifact = "";
        this.groupName = "";
        this.description = "";
        this.userId = null;
        this.userfield = null;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_user() {
        return userId;
    }

    public void setId_user(Long id_user) {
        this.userId = id_user;
    }

    public User getUser() {
        return userfield;
    }

    public void setUser(User user) {
        this.userfield = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getGroup() {
        return groupName;
    }

    public void setGroup(String group) {
        this.groupName = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
