package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link InterventionEntity} entity.
 */
@DisplayName("Intervention entity")
class InterventionEntityTest {

    /**
     * Tests the {@link InterventionEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(InterventionEntity.class);
    }

}
