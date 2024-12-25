package com.code_assistant.user_service.controller;

import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.exception.wrapper.ResourceNotFoundException;
import com.code_assistant.user_service.service.interfaces.UserService;
import com.code_assistant.user_service.dto.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Test
    void testFindAll() throws Exception {
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setFirstname("John");
        userDto1.setLastname("Doe");
        userDto1.setEmail("john.doe@example.com");
        userDto1.setPassword("validPassword123");
        userDto1.setGender(true);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setFirstname("Jane");
        userDto2.setLastname("Doe");
        userDto2.setEmail("jane.doe@example.com");
        userDto2.setPassword("validPassword123");
        userDto2.setGender(false);

        List<UserDto> userDtos = List.of(userDto1, userDto2);

        when(userService.findAll()).thenReturn(userDtos);

        mockMvc.perform(get("/api/auth/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[1].firstname").value("Jane"));
    }

    @Test
    void testFindById_withValidUserId() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("validPassword123");
        userDto.setGender(true);

        when(userService.findById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/auth/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
    @Test
    void testDelete_withValidUserId() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/api/auth/{id}", 1L))
                .andExpect(status().isAccepted());

        verify(userService, times(1)).deleteById(1L);
    }

}
