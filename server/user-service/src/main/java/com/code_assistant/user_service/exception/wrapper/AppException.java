package com.code_assistant.user_service.exception.wrapper;

public class AppException extends RuntimeException{
    public AppException(String message)
    {
        super(message);
    }
}