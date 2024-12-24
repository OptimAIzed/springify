package com.code_assistant.user_service.helper.util;

import static org.junit.jupiter.api.Assertions.*;


import com.code_assistant.user_service.config.authentication.CustomUserDetails;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    @Mock
    private CustomUserDetails customUserDetails;

    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private String token;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecretKey(secretKey);
        jwtUtil.setJwtExpiration(1000 * 60 * 60);
        jwtUtil.setRefreshExpiration(1000 * 60 * 60 * 24);

        customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getId()).thenReturn(1L);
        when(customUserDetails.getEmail()).thenReturn("user@example.com");

        token = jwtUtil.generateToken(customUserDetails);
    }

    @Test
    public void testExtractEmail() {
        String email = jwtUtil.extractEmail(token);
        assertEquals("user@example.com", email, "Email extracted from the token should match the expected value.");
    }

    @Test
    public void testExtractId() {
        Long id = jwtUtil.extractId(token);
        assertEquals(1L, id, "ID extracted from the token should match the expected value.");
    }

    @Test
    public void testGenerateToken() {
        String generatedToken = jwtUtil.generateToken(customUserDetails);
        assertNotNull(generatedToken, "Token should be generated successfully.");
        assertFalse(generatedToken.isEmpty(), "Generated token should not be empty.");
    }

    @Test
    public void testGenerateRefreshToken() {
        String refreshToken = jwtUtil.generateRefreshToken(customUserDetails);
        assertNotNull(refreshToken, "Refresh token should be generated successfully.");
        assertFalse(refreshToken.isEmpty(), "Generated refresh token should not be empty.");
    }

    @Test
    public void testIsTokenValid() {
        boolean isValid = jwtUtil.isTokenValid(token, customUserDetails);
        assertTrue(isValid, "Token should be valid.");
    }


}
