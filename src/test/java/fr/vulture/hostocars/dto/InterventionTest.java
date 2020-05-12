package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Intervention} DTO.
 */
@DisplayName("Intervention DTO")
class InterventionTest {

    /**
     * Tests the {@link Intervention} DTO.
     */
    @Test
    @DisplayName("Testing the DTO")
    final void testBean() {
        validateBean(Intervention.class);
    }

}
