package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link PropertyEntity} entity.
 */
@DisplayName("Property entity")
class PropertyEntityTest {

    /**
     * Tests the {@link PropertyEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(PropertyEntity.class);
    }

}
