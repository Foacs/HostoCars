package fr.vulture.hostocars.entity;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link OperationEntity} entity.
 */
@DisplayName("Operation entity")
class OperationEntityTest {

    /**
     * Tests the {@link OperationEntity} entity.
     */
    @Test
    @DisplayName("Testing the entity")
    final void testBean() {
        validateBean(OperationEntity.class);
    }

}
