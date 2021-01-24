package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

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
    @DisplayName("Bean validation")
    final void testBean() {
        validateBean(Intervention.class);
    }

}
