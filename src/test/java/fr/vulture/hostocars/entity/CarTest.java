package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validatePojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Car} class.
 */
@DisplayName("Car")
class CarTest {

    /**
     * Tests the {@link Car} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(Car.class);
    }

}
