package com.code_assistant.user_service.helper.enums;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    public void testRoleEnum() {
        // Test that the 'CLIENT' enum has the expected role value
        Role role = Role.CLIENT;
        assertEquals("ROLE_USER", role.getRole(), "Role for CLIENT should be 'ROLE_USER'");
    }

    @Test
    public void testEnumValues() {
        // Ensure that the 'CLIENT' enum exists in the enum values
        Role[] roles = Role.values();
        boolean foundClient = false;

        for (Role role : roles) {
            if (role == Role.CLIENT) {
                foundClient = true;
                break;
            }
        }

        assertEquals(true, foundClient, "CLIENT role should be present in the enum values");
    }
}
