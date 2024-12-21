package com.code_assistant.project_service;

import com.code_assistant.project_service.entities.Project;
import com.code_assistant.project_service.entities.User;
import com.code_assistant.project_service.repositories.ProjectRepository;
import com.code_assistant.project_service.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner initialiserBaseH2(ProjectRepository projectRepository, UserService userService) {
		User u1 = userService.userById(1L);
		User u2 = userService.userById(2L);
		return args ->
		{
			projectRepository.save(new Project(null, "ouabiba", "imade", "group1", "description1",u1,1L));
			projectRepository.save(new Project(null, "ouabiba", "hamza", "group2", "description2",u2,2L));
		};
	}
}
