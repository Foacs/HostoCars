package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Car} DTO.
 */
@DisplayName("Car DTO")
class CarTest {

    /**
     * Tests the {@link Car} DTO.
     */
    @Test
    @DisplayName("Testing the DTO")
    final void testBean() {
        validateBean(Car.class);
    }

}
