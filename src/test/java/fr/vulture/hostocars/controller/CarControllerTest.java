package fr.vulture.hostocars.controller;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.CarDao;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.pojo.Response;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for the {@link CarController} class.
 */
@DisplayName("Car controller")
@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarDao carDao;

    @InjectMocks
    private CarController carController;

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the cars with an OK status")
    final void testGetCarsWithOkStatus() {
        final List<Car> carList = unmodifiableList(singletonList(new Car()));

        when(this.carDao.getCars(null)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.getCars(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(carList, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).getCars(null);
    }

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting all the cars with a NO_CONTENT status")
    final void testGetCarsWithNoContent() {
        final List<Car> carList = unmodifiableList(emptyList());

        when(this.carDao.getCars(null)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.getCars(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).getCars(null);
    }

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the cars with an INTERNAL_SERVER_ERROR status")
    final void testGetCarsWithInternalError() {
        when(this.carDao.getCars(null)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.getCars(null);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).getCars(null);
    }

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#OK} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the cars with an OK status and a sorting clause")
    final void testGetSortedCarsWithOkStatus() {
        final String sortedBy = "";
        final List<Car> carList = unmodifiableList(singletonList(new Car()));

        when(this.carDao.getCars(sortedBy)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.getCars(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(carList, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).getCars(sortedBy);
    }

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#NO_CONTENT} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the cars with a NO_CONTENT status and a sorting clause")
    final void testGetSortedCarsWithNoContent() {
        final String sortedBy = "";
        final List<Car> carList = unmodifiableList(emptyList());

        when(this.carDao.getCars(sortedBy)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.getCars(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).getCars(sortedBy);
    }

    /**
     * Tests the {@link CarController#getCars} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a sorting clause.
     */
    @Test
    @DisplayName("Getting all the cars with an INTERNAL_SERVER_ERROR status and a sorting clause")
    final void testGetSortedCarsWithInternalError() {
        final String sortedBy = "";

        when(this.carDao.getCars(sortedBy)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.getCars(sortedBy);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).getCars(sortedBy);
    }

    /**
     * Tests the {@link CarController#getCarById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting a car by its ID with an OK status")
    final void testGetCarByIdWithOkStatus() {
        final int id = 1;
        final Car car = new Car();

        when(this.carDao.getCarById(id)).thenReturn(car);

        final ResponseEntity<?> response = this.carController.getCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(car, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).getCarById(id);
    }

    /**
     * Tests the {@link CarController#getCarById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Getting a car by its ID with a NO_CONTENT status")
    final void testGetCarByIdWithNoContent() {
        final int id = 1;

        when(this.carDao.getCarById(id)).thenReturn(null);

        final ResponseEntity<?> response = this.carController.getCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).getCarById(id);
    }

    /**
     * Tests the {@link CarController#getCarById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting a car by its ID with an INTERNAL_SERVER_ERROR status")
    final void testGetCarByIdWithInternalError() {
        final int id = 1;

        when(this.carDao.getCarById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.getCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).getCarById(id);
    }

    /**
     * Tests the {@link CarController#getCarRegistrations} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Getting all the car registrations with an OK status")
    final void testGetCarRegistrationsWithOkStatus() {
        final Set<String> registrationList = unmodifiableSet(singleton(""));

        when(this.carDao.getCarRegistrations()).thenReturn(registrationList);

        final ResponseEntity<?> response = this.carController.getCarRegistrations();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(registrationList, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).getCarRegistrations();
    }

    /**
     * Tests the {@link CarController#getCarRegistrations} method with {@link HttpStatus#NO_CONTENT} status and an empty list.
     */
    @Test
    @DisplayName("Getting all the car registrations with a NO_CONTENT status (empty list)")
    final void testGetCarRegistrationsWithNoContentAndEmptyList() {
        final Set<String> registrationList = unmodifiableSet(emptySet());

        when(this.carDao.getCarRegistrations()).thenReturn(registrationList);

        final ResponseEntity<?> response = this.carController.getCarRegistrations();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).getCarRegistrations();
    }

    /**
     * Tests the {@link CarController#getCarRegistrations} method with {@link HttpStatus#NO_CONTENT} status and a null list.
     */
    @Test
    @DisplayName("Getting all the car registrations with a NO_CONTENT status (null list)")
    final void testGetCarRegistrationsWithNoContentAndNullList() {
        when(this.carDao.getCarRegistrations()).thenReturn(null);

        final ResponseEntity<?> response = this.carController.getCarRegistrations();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).getCarRegistrations();
    }

    /**
     * Tests the {@link CarController#getCarRegistrations} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Getting all the car registrations with an INTERNAL_SERVER_ERROR status")
    final void testGetCarRegistrationsWithInternalError() {
        when(this.carDao.getCarRegistrations()).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.getCarRegistrations();

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).getCarRegistrations();
    }

    /**
     * Tests the {@link CarController#searchCars} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Searching for cars with an OK status")
    final void testSearchCarsWithOkStatus() {
        final Car body = new Car();
        final List<Car> carList = unmodifiableList(singletonList(new Car()));

        when(this.carDao.searchCars(body)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.searchCars(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(carList, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).searchCars(body);
    }

    /**
     * Tests the {@link CarController#searchCars} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Searching for cars with a NO_CONTENT status")
    final void testSearchCarsWithNoContent() {
        final Car body = new Car();
        final List<Car> carList = unmodifiableList(emptyList());

        when(this.carDao.searchCars(body)).thenReturn(carList);

        final ResponseEntity<?> response = this.carController.searchCars(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).searchCars(body);
    }

    /**
     * Tests the {@link CarController#searchCars} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Searching for cars with an INTERNAL_SERVER_ERROR status")
    final void testSearchCarsWithInternalError() {
        final Car body = new Car();

        when(this.carDao.searchCars(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.searchCars(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).searchCars(body);
    }

    /**
     * Tests the {@link CarController#saveCar} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Saving a new car with an OK status")
    final void testSaveCarWithOkStatus() {
        final Car body = new Car();
        final Integer id = 1;

        when(this.carDao.saveCar(body)).thenReturn(id);

        final ResponseEntity<?> response = this.carController.saveCar(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertSame(id, response.getBody(), "Response body different from expected");

        verify(this.carDao, times(1)).saveCar(body);
    }

    /**
     * Tests the {@link CarController#saveCar} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Saving a new car with an INTERNAL_SERVER_ERROR status")
    final void testSaveCarWithInternalError() {
        final Car body = new Car();

        when(this.carDao.saveCar(body)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.saveCar(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).saveCar(body);
    }

    /**
     * Tests the {@link CarController#updateCar} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Updating a car with an OK status")
    final void testUpdateCarWithOkStatus() {
        final Car body = new Car();

        final ResponseEntity<?> response = this.carController.updateCar(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).updateCar(body);
    }

    /**
     * Tests the {@link CarController#updateCar} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Updating a car with an INTERNAL_SERVER_ERROR status")
    final void testUpdateCarWithInternalError() {
        final Car body = new Car();

        doThrow(new RuntimeException("")).when(this.carDao).updateCar(body);

        final ResponseEntity<?> response = this.carController.updateCar(body);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).updateCar(body);
    }

    /**
     * Tests the {@link CarController#deleteCarById} method with {@link HttpStatus#OK} status.
     */
    @Test
    @DisplayName("Deleting a car by its ID with an OK status")
    final void testDeleteCarByIdWithOkStatus() {
        final int id = 1;

        when(this.carDao.deleteCarById(id)).thenReturn(true);

        final ResponseEntity<?> response = this.carController.deleteCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).deleteCarById(id);
    }

    /**
     * Tests the {@link CarController#deleteCarById} method with {@link HttpStatus#NO_CONTENT} status.
     */
    @Test
    @DisplayName("Deleting a car by its ID with a NO_CONTENT status")
    final void testDeleteCarByIdWithNoContent() {
        final int id = 1;

        when(this.carDao.deleteCarById(id)).thenReturn(false);

        final ResponseEntity<?> response = this.carController.deleteCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");

        verify(this.carDao, times(1)).deleteCarById(id);
    }

    /**
     * Tests the {@link CarController#deleteCarById} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Deleting a car by its ID with an INTERNAL_SERVER_ERROR status")
    final void testDeleteCarByIdWithInternalError() {
        final int id = 1;

        when(this.carDao.deleteCarById(id)).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.carController.deleteCarById(id);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertEquals(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.carDao, times(1)).deleteCarById(id);
    }

}
