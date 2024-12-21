package com.code_assistant.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
//	@Bean
//	RouteLocator routes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(r -> r.path("/users/**").uri("lb://user-service"))
//				.build();
//	}
	@Bean
	DiscoveryClientRouteDefinitionLocator routesDynamic(ReactiveDiscoveryClient rdc , DiscoveryLocatorProperties discoveryLocatorProperties) {
		return new DiscoveryClientRouteDefinitionLocator(rdc,discoveryLocatorProperties);
	}
}
