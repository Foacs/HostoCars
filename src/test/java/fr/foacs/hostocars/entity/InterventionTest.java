package fr.foacs.hostocars.entity;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Intervention} class.
 */
@DisplayName("Intervention entity")
class InterventionTest extends AbstractEntityTest<Intervention> {

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
