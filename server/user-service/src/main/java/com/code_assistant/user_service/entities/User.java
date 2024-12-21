package com.code_assistant.user_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    public User(Long id,String nom,String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public User() {
        this.id = null;
        this.nom = "";
        this.prenom = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
