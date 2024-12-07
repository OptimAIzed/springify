package com.springify.uml.server.service;

import com.springify.uml.server.dto.UserDTO;
import com.springify.uml.server.model.User;
import com.springify.uml.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<UserDTO> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail()));
    }

    public Optional<UserDTO> updateUser(String id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user = userRepository.save(user); // Save updated user

            return Optional.of(new UserDTO(user.getId(), user.getName(), user.getEmail())); // Return DTO
        }
        return Optional.empty();  // Return an empty Optional if user not found
    }

    public boolean deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;  // Return false if user not found
    }
}