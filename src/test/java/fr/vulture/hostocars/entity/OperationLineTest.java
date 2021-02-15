package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validatePojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLine} class.
 */
@DisplayName("Operation line")
class OperationLineTest {

    /**
     * Tests the {@link OperationLine} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(OperationLine.class);
    }

}
