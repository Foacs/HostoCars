package fr.foacs.hostocars.entity;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Car} class.
 */
@DisplayName("Car entity")
class CarTest extends AbstractEntityTest<Car> {

    /**
     * {@inheritDoc}
     */
    @Override
    Class<Car> getTestClass() {
        return Car.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Car constructTestObject() {
        return new Car();
    }

}
