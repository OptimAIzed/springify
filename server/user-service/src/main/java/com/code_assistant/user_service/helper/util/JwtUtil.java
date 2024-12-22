package com.code_assistant.user_service.helper.util;

import com.code_assistant.user_service.config.Authentication.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Service
public class JwtUtil{

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value ("${application.security.jwt.expiration}")
	private long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;

	public String extractEmail(String token) {
		return extractClaim(token, claims -> claims.get("email", String.class));
	}
	public Long extractId( String token) {
		return extractClaim(token, claims -> claims.get("id", Long.class));
	}


	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken( CustomUserDetails userDetails) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("id", userDetails.getId());
		extraClaims.put("email", userDetails.getEmail());
		return generateToken(extraClaims, userDetails);
	}

	public String generateToken(
			Map<String, Object> extraClaims,
			CustomUserDetails userDetails
	) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	public String generateRefreshToken(
			CustomUserDetails userDetails
	) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("id", userDetails.getId());
		extraClaims.put("email", userDetails.getEmail());
		return buildToken(extraClaims, userDetails, refreshExpiration);
	}

	private String buildToken(
			Map<String, Object> extraClaims,
			CustomUserDetails customUserDetails,
			long expiration
	) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(customUserDetails.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, CustomUserDetails customUserDetails) {
		final String email = extractEmail(token);
		extractClaim(token, Claims::getSubject);
		return (email.equals(customUserDetails.getEmail())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}