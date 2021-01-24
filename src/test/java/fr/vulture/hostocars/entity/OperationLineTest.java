package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

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
    @DisplayName("Bean validation")
    final void testBean() {
        validateBean(OperationLine.class);
    }

}
