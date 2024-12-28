package com.code_assistant.project_service.repositories;

import com.code_assistant.project_service.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userid);
    void deleteAllByUserId(Long userid);
}
