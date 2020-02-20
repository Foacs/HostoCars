package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ProfileEnum} enumeration.
 */
@DisplayName("Profile enum")
class ProfileEnumTest {

    /**
     * Tests the {@link ProfileEnum} values.
     */
    @Test
    @DisplayName("Testing the enum values")
    final void testProfileEnumValues() {
        assertAll("Asserting all profiles values", () -> assertNotNull(ProfileEnum.valueOf("PROD"), "Enum value not found"),
            () -> assertNotNull(ProfileEnum.valueOf("DEV"), "Enum value not found"),
            () -> assertEquals(2, ProfileEnum.values().length, "Enum values length different than expected"));
    }

}
