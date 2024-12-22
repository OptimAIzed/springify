package com.code_assistant.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/api/auth/signin",
            "/api/auth/signup"
    );
    public static boolean isSecured (ServerHttpRequest request){
        return  openApiEndpoints
                .stream()
                .noneMatch (uri -> request.getURI().getPath().contains(uri));
    }
}