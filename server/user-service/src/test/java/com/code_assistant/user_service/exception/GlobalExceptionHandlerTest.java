package com.code_assistant.user_service.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.code_assistant.user_service.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    public void testGlobalException() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();

        mockMvc.perform(get("/api/auth/unknown")) // assuming this triggers a generic error
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("INTERNAL_SERVER_ERROR"));
    }
}
