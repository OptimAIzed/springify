package com.code_assistant.user_service.config.Authentication;

import com.code_assistant.user_service.helper.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;


	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {

		if (RouteValidator.isSecured(request)){
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println(request.getRequestURI());
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing or invalid.");
			return;
		}
		try{
			jwt = authHeader.substring(7);
			userEmail = jwtUtil.extractEmail(jwt);
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userEmail);
	
				boolean isTokenValid = jwtUtil.isTokenValid(jwt,customUserDetails);
				if (isTokenValid) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							customUserDetails,
							null,
							customUserDetails.getAuthorities()
					);
					authToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request)
					);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
		}
		filterChain.doFilter(request, response);
	} catch (Exception e) {
		// If the token is invalid or expired, send a 403 Forbidden response
		response.setHeader("error message", e.getMessage());
		response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		e.printStackTrace();
		System.out.println("Token validation failed: " + e.getMessage());
	}
	}

}