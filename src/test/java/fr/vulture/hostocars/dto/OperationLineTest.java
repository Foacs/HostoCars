package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLine} DTO.
 */
@DisplayName("Operation line DTO")
class OperationLineTest {

    /**
     * Tests the {@link OperationLine} DTO.
     */
    @Test
    @DisplayName("Testing the DTO")
    final void testBean() {
        validateBean(OperationLine.class);
    }

}
