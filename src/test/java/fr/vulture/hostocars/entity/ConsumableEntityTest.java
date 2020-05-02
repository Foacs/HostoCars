package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ConsumableEntity} entity.
 */
@DisplayName("Consumable entity")
class ConsumableEntityTest {

    /**
     * Tests the {@link ConsumableEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(ConsumableEntity.class);
    }

}
