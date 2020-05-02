package fr.vulture.hostocars.dto;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Consumable} DTO.
 */
@DisplayName("Consumable DTO")
class ConsumableTest {

    /**
     * Tests the {@link Consumable} DTO.
     */
    @Test
    @DisplayName("Testing the DTO")
    final void testBean() {
        validateBean(Consumable.class);
    }

}
