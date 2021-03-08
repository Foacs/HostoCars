package fr.vulture.hostocars.dto;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Intervention} class.
 */
@DisplayName("Intervention DTO")
class InterventionTest extends DtoTest<Intervention> {

    /**
     * {@inheritDoc}
     */
    @Override
    Class<Intervention> getTestClass() {
        return Intervention.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Intervention constructTestObject() {
        return new Intervention();
    }

}
