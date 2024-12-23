package com.code_assistant.user_service;

import com.code_assistant.user_service.dto.AuthenticationResponse;
import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.exception.wrapper.ResourceAlreadyExistException;
import com.code_assistant.user_service.exception.wrapper.ResourceNotFoundException;
import com.code_assistant.user_service.helper.mappers.UserMapper;
import com.code_assistant.user_service.model.Users;
import com.code_assistant.user_service.repository.UserRepository;
import com.code_assistant.user_service.service.implementations.UserServiceImpl;
import com.code_assistant.user_service.service.interfaces.UserService;
import com.code_assistant.user_service.helper.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    private Users user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users(1L, "John", "Doe", "john.doe@example.com", "password123", null, null, true);
        userDto = new UserDto(1L,"John","Doe","john.doe@example.com","password123",true);
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        var users = userService.findAll();

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_UserFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var foundUser = userService.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    public void testLogin_ValidCredentials() {
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("access_token");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("refresh_token");

        AuthenticationResponse response = userService.login(userDto);

        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new RuntimeException("invalid access"));

        assertThrows(RuntimeException.class, () -> userService.login(userDto));
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class, () -> userService.register(userDto));
    }

    @Test
    public void testRegister_Success() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("jwtToken");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("refreshToken");

        AuthenticationResponse response = userService.register(userDto);

        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testUpdate_UserNotFound() {
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(userDto));
    }


    @Test
    public void testDeleteById() {
        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteById(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void testExistsById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        boolean exists = userService.existsById(user.getId());

        assertTrue(exists);
        verify(userRepository, times(1)).findById(user.getId());
    }
}
