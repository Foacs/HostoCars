package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validatePojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Operation} class.
 */
@DisplayName("Operation")
class OperationTest {

    /**
     * Tests the {@link Operation} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(Operation.class);
    }

}
