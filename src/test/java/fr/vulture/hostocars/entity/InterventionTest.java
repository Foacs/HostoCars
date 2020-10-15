package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Intervention} entity.
 */
@DisplayName("Intervention entity")
class InterventionTest {

    /**
     * Tests the {@link Intervention} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(Intervention.class);
    }

}
