package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.vulture.hostocars.entity.Car;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 * Test class for the {@link CarController} class.
 */
@DisplayName("Car controller")
@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private ControllerHelper helper;

    @InjectMocks
    private CarController carController;

    /**
     * Tests the {@link CarController#getCars} method without sorting fields.
     */
    @Test
    @DisplayName("Get cars (without sorting fields)")
    void testGetCarsWithoutSortingFields() {
        // Prepares the intermediary results
        final ResponseEntity<Collection<Car>> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolveGetCollectionResponse(any(Callable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<Collection<Car>> result = this.carController.getCars();

        // Checks the mocks calls
        verify(this.helper).resolveGetCollectionResponse(any(Callable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

    /**
     * Tests the {@link CarController#getCars} method with sorting fields.
     */
    @Test
    @DisplayName("Get cars (with sorting fields)")
    void testGetCarsWithSortingFields() {
        // Prepares the intermediary results
        final ResponseEntity<Collection<Car>> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolveGetCollectionResponse(any(Callable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<Collection<Car>> result = this.carController.getCars("field1", "field2");

        // Checks the mocks calls
        verify(this.helper).resolveGetCollectionResponse(any(Callable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

    /**
     * Tests the {@link CarController#getCarById} method.
     */
    @Test
    @DisplayName("Get car by ID")
    void testGetCarById() {
        // Prepares the intermediary results
        final ResponseEntity<Car> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolveGetResponse(any(Callable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<Car> result = this.carController.getCarById(0);

        // Checks the mocks calls
        verify(this.helper).resolveGetResponse(any(Callable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

    /**
     * Tests the {@link CarController#createCar} method.
     */
    @Test
    @DisplayName("Create car")
    void testCreateCar() {
        // Prepares the intermediary results
        final ResponseEntity<?> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolvePostResponse(any(Callable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<?> result = this.carController.createCar(new Car());

        // Checks the mocks calls
        verify(this.helper).resolvePostResponse(any(Callable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

    /**
     * Tests the {@link CarController#updateCar} method.
     */
    @Test
    @DisplayName("Update car")
    void testUpdateCar() {
        // Prepares the intermediary results
        final ResponseEntity<?> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolvePutResponse(any(Runnable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<?> result = this.carController.updateCar(new Car());

        // Checks the mocks calls
        verify(this.helper).resolvePutResponse(any(Runnable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

    /**
     * Tests the {@link CarController#deleteCarById} method.
     */
    @Test
    @DisplayName("Delete car by ID")
    void testDeleteCarById() {
        // Prepares the intermediary results
        final ResponseEntity<?> response = mock(ResponseEntity.class);

        // Mocks the calls
        when(this.helper.resolveDeleteResponse(any(Runnable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<?> result = this.carController.deleteCarById(0);

        // Checks the mocks calls
        verify(this.helper).resolveDeleteResponse(any(Runnable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
    }

}
