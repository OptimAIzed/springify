package com.code_assistant.user_service.dto;

import lombok.*; 

@Builder
@Getter
@Setter
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
