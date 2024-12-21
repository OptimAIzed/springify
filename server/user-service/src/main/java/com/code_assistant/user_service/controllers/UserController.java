package com.code_assistant.user_service.controllers;

import com.code_assistant.user_service.entities.User;
import com.code_assistant.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/users")
    public List<User> findAll() {
        System.out.println(userRepository.findAll().get(0).getNom());
        return userRepository.findAll();
    }
    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("Utilisateur non trouvé"));
    }
}
