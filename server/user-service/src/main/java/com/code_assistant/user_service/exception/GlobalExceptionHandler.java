package com.code_assistant.user_service.exception;
import com.code_assistant.user_service.exception.wrapper.ErrorResponse;
import com.code_assistant.user_service.exception.wrapper.ResourceAlreadyExistException;
import com.code_assistant.user_service.exception.wrapper.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(
			ResourceNotFoundException ex, WebRequest request) {
		ErrorResponse errorDetails =   ErrorResponse.builder()
						.error("INTERNAL_SERVER_ERROR")
								.message(ex.getMessage())
										.status(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
				.details(request.getDescription(false))
						.timestamp(LocalDateTime.now())
								.build();

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<?> handleResourceAlreadyExistException(
			ResourceNotFoundException ex, WebRequest request) {
		ErrorResponse errorDetails =   ErrorResponse.builder()
				.error("ResourceAlreadyExistException")
				.message(ex.getMessage())
				.status(HttpStatus.valueOf(	HttpStatus.CONFLICT.value()))
				.details(request.getDescription(false))
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(
			Exception ex, WebRequest request) {
		ErrorResponse errorDetails =   ErrorResponse.builder()
				.error("INTERNAL_SERVER_ERROR")
				.message(ex.getMessage())
				.status(HttpStatus.valueOf(	HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.details(request.getDescription(false))
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}