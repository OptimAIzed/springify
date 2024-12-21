package com.code_assistant.user_service;

import com.code_assistant.user_service.entities.User;
import com.code_assistant.user_service.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner initialiserBaseH2(UserRepository userRepository) {
		return args ->
		{
			userRepository.save(new User(null,"ouabiba","imade"));
			userRepository.save(new User(null,"ouabiba","hamza"));
		};
	}
}
