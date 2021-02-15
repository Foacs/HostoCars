package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validatePojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Intervention} class.
 */
@DisplayName("Intervention")
class InterventionTest {

    /**
     * Tests the {@link Intervention} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(Intervention.class);
    }

}
