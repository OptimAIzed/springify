package com.code_assistant.project_service.repositories;

import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    User user;
    @BeforeEach
    public void setup() {
        projectRepository.deleteAll();
        user = new User();
        user.setNom("ouabiba");
        user.setPrenom("hamza");
        user.setId(1L);
        project = new Project();
        project.setNom("p1");
        project.setArtifact("artifact");
        project.setGroupName("g1");
        project.setDescription("desc");
        project.setUserId(user.getId());
        projectRepository.save(project);
    }

    @Test
    public void testSaveProject() {
        Project newProject = new Project();
        newProject.setNom("p2");
        newProject.setArtifact("artifact2");
        newProject.setGroupName("g2");
        newProject.setDescription("desc2");

        Project savedProject = projectRepository.save(newProject);

        assertNotNull(savedProject.getId(), "Le projet doit avoir un ID généré après la sauvegarde.");
        assertEquals("p2", savedProject.getNom(), "Le nom du projet doit être 'p2'.");
        assertEquals("artifact2", savedProject.getArtifact(), "L'artefact du projet doit être 'artifact2'.");
        assertEquals("g2", savedProject.getGroupName(), "Le groupe du projet doit être 'g2'.");
        assertEquals("desc2", savedProject.getDescription(), "La description du projet doit être 'desc2'.");
    }

    @Test
    public void testFindAll() {
        List<Project> projectList = projectRepository.findAll();

        assertNotNull(projectList, "La liste des projets ne doit pas être nulle.");
        assertFalse(projectList.isEmpty(), "La liste des projets ne doit pas être vide.");
        assertEquals(1, projectList.size(), "Il doit y avoir un projet dans la base de données.");
        assertEquals("p1", projectList.get(0).getNom(), "Le nom du projet récupéré doit être 'p1'.");
    }
    @Test
    public void testFindByIdProject() {
        projectRepository.deleteAll();

        Project project = new Project();
        project.setNom("p3");
        project.setArtifact("artifact");
        project.setGroupName("g3");
        project.setDescription("desc");

        Project savedProject = projectRepository.save(project);

        Long projectId = savedProject.getId();
        Optional<Project> foundProject = projectRepository.findById(projectId);

        assertTrue(foundProject.isPresent(), "Le projet doit être trouvé par son ID.");
        assertEquals("p3", foundProject.get().getNom(), "Le nom du projet doit être 'p1'.");
        assertEquals("artifact", foundProject.get().getArtifact(), "L'artefact doit être 'artifact'.");
        assertEquals("g3", foundProject.get().getGroupName(), "Le groupe doit être 'g1'.");
        assertEquals("desc", foundProject.get().getDescription(), "La description doit être 'desc'.");
    }
    @Test
    public void testFindByIdUser() {
        List<Project> projectList = projectRepository.findByUserId(user.getId());


        assertNotNull(projectList, "La liste des projets ne doit pas être nulle.");
        assertFalse(projectList.isEmpty(), "La liste des projets ne doit pas être vide.");
        assertEquals(1, projectList.size(), "Il doit y avoir un projet dans la base de données.");
        assertEquals("p1", projectList.get(0).getNom(), "Le nom du projet récupéré doit être 'p1'.");
    }
}
