package com.code_assistant.user_service.exception.wrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
	private String message;
	private HttpStatus status;
	private String details;
	private String error;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 	private LocalDateTime timestamp;




}