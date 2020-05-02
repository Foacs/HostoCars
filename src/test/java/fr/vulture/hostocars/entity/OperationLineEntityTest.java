package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationLineEntity} entity.
 */
@DisplayName("Operation line entity")
class OperationLineEntityTest {

    /**
     * Tests the {@link OperationLineEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(OperationLineEntity.class);
    }

}
