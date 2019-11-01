package fr.vulture.hostocars.car.entity;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Car} bean.
 */
@DisplayName("Car")
class CarTest {

    /**
     * Tests the {@link Car} bean.
     */
    @Test
    @DisplayName("Validating the bean")
    final void testBeanValidation() {
        TestHelper.validateBean(Car.class);
    }

}
