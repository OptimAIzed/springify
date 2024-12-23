package com.code_assistant.user_service.controller;

import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.service.interfaces.UserService;
import com.code_assistant.user_service.dto.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testSignup_withValidUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("validPassword123");
        userDto.setGender(true);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken("test_access")
                .refreshToken("test_refresh")
                .build();
        when(userService.register(any(UserDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstname\":\"John\",\"lastname\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"validPassword123\",\"gender\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.access_token").value("test_access"))
                .andExpect(jsonPath("$.refresh_token").value("test_refresh"));
    }

    @Test
    void testSignin_withValidUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("validPassword123");

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken("sampleAccessToken")
                .refreshToken("sampleRefreshToken")
                .build();
        when(userService.login(any(UserDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"password\":\"validPassword123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("sampleAccessToken"))
                .andExpect(jsonPath("$.refresh_token").value("sampleRefreshToken"));
    }

}
