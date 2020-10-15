package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Operation} entity.
 */
@DisplayName("Operation entity")
class OperationTest {

    /**
     * Tests the {@link Operation} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(Operation.class);
    }

}
