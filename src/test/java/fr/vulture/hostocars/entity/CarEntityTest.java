package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link CarEntity} entity.
 */
@DisplayName("Car entity")
class CarEntityTest {

    /**
     * Tests the {@link CarEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(CarEntity.class);
    }

}
