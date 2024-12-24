package com.code_assistant.user_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private boolean gender;

}
