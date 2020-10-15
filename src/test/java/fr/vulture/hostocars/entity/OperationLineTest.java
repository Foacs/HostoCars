package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLine} entity.
 */
@DisplayName("Operation line entity")
class OperationLineTest {

    /**
     * Tests the {@link OperationLine} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(OperationLine.class);
    }

}
