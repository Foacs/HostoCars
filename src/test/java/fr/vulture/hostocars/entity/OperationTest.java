package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

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
    @DisplayName("Bean validation")
    final void testBean() {
        validateBean(Operation.class);
    }

}
