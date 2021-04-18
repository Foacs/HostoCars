package fr.foacs.hostocars;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Application} class.
 */
@DisplayName("Application")
class ApplicationTest {

    /**
     * Tests the {@link Application#main(String[])} method.
     */
    @Test
    @DisplayName("Main")
    void testMain() {
        // Call the method
        assertDoesNotThrow(() -> Application.main(new String[] {}), "Unexpected exception thrown");
    }

}
