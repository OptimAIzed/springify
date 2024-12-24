package com.code_assistant.project_service.services.interfaces;


import com.code_assistant.project_service.config.FeignConfig;
import com.code_assistant.project_service.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE",configuration = FeignConfig.class)
public interface UserService {
    @GetMapping(path = "/api/auth/{id}")
    User userById(@PathVariable Long id);
}