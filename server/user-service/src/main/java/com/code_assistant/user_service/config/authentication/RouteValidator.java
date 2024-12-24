package com.code_assistant.user_service.config.authentication;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/api/auth/signin",
            "/api/auth/signup"
    );
    public static boolean isSecured (HttpServletRequest request){

        return  openApiEndpoints
                .stream()
                .anyMatch (uri -> request.getRequestURI ().contains(uri));
    }


}