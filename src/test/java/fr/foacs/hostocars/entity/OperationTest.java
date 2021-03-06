package fr.foacs.hostocars.entity;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Operation} class.
 */
@DisplayName("Operation entity")
class OperationTest extends AbstractEntityTest<Operation> {

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
