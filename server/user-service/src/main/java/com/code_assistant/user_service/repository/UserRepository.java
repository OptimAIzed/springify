package com.code_assistant.user_service.repository;

import com.code_assistant.user_service.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String Email);
	boolean existsByEmail(String Email);
}
