package fr.vulture.hostocars.entity;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Car} entity.
 */
@DisplayName("Car entity")
class CarTest {

    /**
     * Tests the {@link Car} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        TestHelper.validateBean(Car.class);
    }

}
