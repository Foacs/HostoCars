package fr.vulture.hostocars.dto;

import org.junit.jupiter.api.DisplayName;

/**
 * Test class for the {@link Car} class.
 */
@DisplayName("Car DTO")
class CarTest extends DtoTest<Car> {

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
