package com.code_assistant.user_service.helper.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.code_assistant.user_service.config.authentication.CustomUserDetails;
import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserMapperTest {

    private Users user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        // Setting up the test data
        user = Users.builder()
                .id(1L)
                .email("test@example.com")
                .firstname("John")
                .lastname("Doe")
                .gender(true)
                .password("password123")
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .email("test@example.com")
                .firstname("John")
                .lastname("Doe")
                .gender(true)
                .password("password123")
                .build();
    }

    @Test
    public void testMapToUserDto() {
        UserDto mappedUserDto = UserMapper.map(user);

        // Check that all properties are correctly mapped
        assertEquals(user.getId(), mappedUserDto.getId(), "ID should be equal");
        assertEquals(user.getEmail(), mappedUserDto.getEmail(), "Email should be equal");
        assertEquals(user.getFirstname(), mappedUserDto.getFirstname(), "First name should be equal");
        assertEquals(user.getLastname(), mappedUserDto.getLastname(), "Last name should be equal");
        assertEquals(user.isGender(), mappedUserDto.isGender(), "Gender should be equal");
    }

    @Test
    public void testMapToUser() {
        Users mappedUser = UserMapper.map(userDto);

        // Check that all properties are correctly mapped
        assertEquals(userDto.getId(), mappedUser.getId(), "ID should be equal");
        assertEquals(userDto.getEmail(), mappedUser.getEmail(), "Email should be equal");
        assertEquals(userDto.getFirstname(), mappedUser.getFirstname(), "First name should be equal");
        assertEquals(userDto.getLastname(), mappedUser.getLastname(), "Last name should be equal");
        assertEquals(userDto.getPassword(), mappedUser.getPassword(), "Password should be equal");
        assertEquals(userDto.isGender(), mappedUser.isGender(), "Gender should be equal");
    }

    @Test
    public void testMapToCustomUserDetails() {
        CustomUserDetails customUserDetails = UserMapper.mapToCustomUserDetails(user);

        // Check that all properties are correctly mapped
        assertEquals(user.getId(), customUserDetails.getId(), "ID should be equal");
        assertEquals(user.getEmail(), customUserDetails.getEmail(), "Email should be equal");
        assertEquals(user.getPassword(), customUserDetails.getPassword(), "Password should be equal");
    }
}
