package fr.vulture.hostocars.dto;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Operation} class.
 */
@DisplayName("Operation DTO")
class OperationTest extends DtoTest<Operation> {

    /**
     * {@inheritDoc}
     */
    @Override
    Class<Operation> getTestClass() {
        return Operation.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operation constructTestObject() {
        return new Operation();
    }

}
