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


    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
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
