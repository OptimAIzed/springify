package com.code_assistant.user_service.exception.wrapper;


public class ResourceAlreadyExistException extends RuntimeException {
	public ResourceAlreadyExistException(String message) {
		super(message);
	}
}