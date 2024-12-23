package com.code_assistant.user_service.controller;

import com.code_assistant.user_service.dto.AuthenticationResponse;
import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(this.userService.login(userDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserDto userDto){
        AuthenticationResponse response = this.userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authorizationHeader.substring(7);
        return ResponseEntity.ok(userService.refreshToken(token));
    }
    @PutMapping
    public ResponseEntity<UserDto> updateUser(
            @RequestBody  UserDto userDto
    ) {
        return ResponseEntity.ok(this.userService.update(userDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(this.userService.existsById(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.ok(this.userService.findById(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long userId
    ) {
        this.userService.deleteById(userId);
        return ResponseEntity.accepted().build();
    }

}
