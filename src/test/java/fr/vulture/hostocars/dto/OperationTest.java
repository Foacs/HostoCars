package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Operation} DTO.
 */
@DisplayName("Operation DTO")
class OperationTest {

    /**
     * Tests the {@link Operation} DTO.
     */
    @Test
    @DisplayName("Testing the DTO")
    final void testBean() {
        validateBean(Operation.class);
    }

}
