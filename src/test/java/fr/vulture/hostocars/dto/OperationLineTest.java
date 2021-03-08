package fr.vulture.hostocars.dto;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link OperationLine} class.
 */
@DisplayName("Operation line DTO")
class OperationLineTest extends DtoTest<OperationLine> {

    /**
     * {@inheritDoc}
     */
    @Override
    Class<OperationLine> getTestClass() {
        return OperationLine.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    OperationLine constructTestObject() {
        return new OperationLine();
    }

}
