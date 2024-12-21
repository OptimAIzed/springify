package com.code_assistant.project_service.services;

import com.code_assistant.project_service.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping(path = "/users/{id}")
    User userById(@PathVariable Long id);
}
