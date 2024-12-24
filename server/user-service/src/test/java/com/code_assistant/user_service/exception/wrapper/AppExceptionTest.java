package com.code_assistant.user_service.exception.wrapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppExceptionTest {

    @Test
    void testAppException_withMessage() {
        String errorMessage = "This is a test exception message.";
        AppException appException = new AppException(errorMessage);

        assertEquals(errorMessage, appException.getMessage());
    }

    @Test
    void testAppException_withoutMessage() {
        AppException appException = new AppException(null);

        // Assert that the exception message is null
        assertNull(appException.getMessage());
    }
}
