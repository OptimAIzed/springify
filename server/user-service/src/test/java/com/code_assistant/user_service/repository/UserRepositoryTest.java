package com.code_assistant.user_service.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.code_assistant.user_service.model.Users;
import com.code_assistant.user_service.repository.UserRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = new Users();
        testUser.setEmail("testuser@example.com");
        testUser.setFirstname("John");
        testUser.setLastname("Doe");
        testUser.setPassword("password123");
        testUser.setGender(true);
        userRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        Optional<Users> foundUser = userRepository.findByEmail("testuser@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<Users> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("testuser@example.com"));
    }

    @Test
    void testExistsByEmail_NotFound() {
        assertFalse(userRepository.existsByEmail("nonexistent@example.com"));
    }

    @Test
    void testSaveAndRetrieveUser() {
        Users savedUser = userRepository.save(new Users(null, "Jane", "Smith", "anotheruser@example.com", "password456", null, null, true));
        Optional<Users> foundUser = userRepository.findByEmail(savedUser.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }
    @Test
    void testDeleteByEmail() {
        userRepository.delete(testUser);
        Optional<Users> foundUser = userRepository.findByEmail(testUser.getEmail());
        assertThat(foundUser).isEmpty();
    }
}
