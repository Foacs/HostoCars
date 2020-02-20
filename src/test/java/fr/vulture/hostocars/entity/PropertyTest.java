package fr.vulture.hostocars.entity;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Property} entity.
 */
@DisplayName("Property entity")
class PropertyTest {

    /**
     * Tests the {@link Property} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        TestHelper.validateBean(Property.class);
    }

}
