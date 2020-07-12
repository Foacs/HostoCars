package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.vulture.hostocars.converter.CarConverter;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.entity.CarEntity;
import fr.vulture.hostocars.repository.CarRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

/**
 * Test class for the {@link CarDao} class.
 */
@DisplayName("Car DAO")
@ExtendWith(MockitoExtension.class)
class CarDaoTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;

    @InjectMocks
    private CarDao carDao;

    /**
     * Tests the {@link CarDao#getCars} method.
     */
    @Test
    @DisplayName("Getting all cars")
    void testGetCars() {
        final List<CarEntity> entityList = emptyList();
        when(this.carRepository.findAll(Sort.unsorted())).thenReturn(entityList);

        final List<Car> dtoList = emptyList();
        when(this.carConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.carDao.getCars(), "Result list different from expected");

        verify(this.carRepository, times(1)).findAll(Sort.unsorted());
        verify(this.carConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link CarDao#getCars} method with a sorting fields.
     */
    @Test
    @DisplayName("Getting all cars with a sorting fields")
    void testGetCarsWithSortingFields() {
        final List<CarEntity> entityList = emptyList();
        when(this.carRepository.findAll(any(Sort.class))).thenReturn(entityList);

        final List<Car> dtoList = emptyList();
        when(this.carConverter.toDtoList(entityList)).thenReturn(dtoList);

        final String sortingField1 = "sortingField1";
        final String sortingField2 = "sortingField2";

        assertEquals(dtoList, this.carDao.getCars(sortingField1, sortingField2), "Result list different from expected");

        final ArgumentCaptor<Sort> argumentCaptor = forClass(Sort.class);
        verify(this.carRepository).findAll(argumentCaptor.capture());
        final Sort sort = argumentCaptor.getValue();

        assertNotNull(sort, "Sort clause unexpectedly null");
        assertEquals(2, sort.toList().size(), "Sort clause size different from expected");
        assertAll("Asserting all sorting fields",
            () -> assertEquals(sortingField1, sort.toList().get(0).getProperty(), "First sort clause property different from expected"),
            () -> assertEquals(sortingField2, sort.toList().get(1).getProperty(), "Second sort clause property different from expected"));

        verify(this.carRepository, times(1)).findAll(sort);
        verify(this.carConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link CarDao#getCarById} method.
     */
    @Test
    @DisplayName("Getting a car by ID")
    void testGetCarById() {
        final CarEntity entity = new CarEntity();
        final Integer id = 0;
        when(this.carRepository.findById(id)).thenReturn(Optional.of(entity));

        final Car dto = new Car();
        when(this.carConverter.toDto(entity)).thenReturn(dto);

        assertEquals(dto, this.carDao.getCarById(id), "Result car different from expected");

        verify(this.carRepository, times(1)).findById(id);
        verify(this.carConverter, times(1)).toDto(entity);
    }

    /**
     * Tests the {@link CarDao#getCarRegistrations} method.
     */
    @Test
    @DisplayName("Getting all car registrations")
    void testGetCarRegistrations() {
        final Set<String> registrations = new HashSet<>(0);
        when(this.carRepository.findDistinctRegistrationNumbers()).thenReturn(registrations);

        assertEquals(registrations, this.carDao.getCarRegistrations(), "Result list different from expected");

        verify(this.carRepository, times(1)).findDistinctRegistrationNumbers();
    }

    /**
     * Tests the {@link CarDao#searchCars} method.
     */
    @Test
    @DisplayName("Searching cars")
    void testSearchCars() {
        final Car body = new Car();
        final CarEntity entity = new CarEntity();
        when(this.carConverter.toEntity(body)).thenReturn(entity);

        final List<CarEntity> entityList = emptyList();
        when(this.carRepository.findAll(any(Example.class))).thenReturn(entityList);

        final List<Car> dtoList = emptyList();
        when(this.carConverter.toDtoList(entityList)).thenReturn(dtoList);

        assertEquals(dtoList, this.carDao.searchCars(body), "Result list different from expected");

        final ArgumentCaptor<Example> argumentCaptor = forClass(Example.class);
        verify(this.carRepository).findAll(argumentCaptor.capture());
        final Example example = argumentCaptor.getValue();

        assertNotNull(example, "Example unexpectedly null");
        assertEquals(DEFAULT_MATCHER.withIgnorePaths("certificate", "picture"), example.getMatcher(), "Example matcher different from expected");

        verify(this.carRepository, times(1)).findAll(example);
        verify(this.carConverter, times(1)).toDtoList(entityList);
    }

    /**
     * Tests the {@link CarDao#saveCar} method.
     */
    @Test
    @DisplayName("Saving a car")
    void testSaveCar() {
        final Car body = new Car();
        final CarEntity entity = new CarEntity();
        when(this.carConverter.toEntity(body)).thenReturn(entity);

        final CarEntity savedEntity = new CarEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.carRepository.save(entity)).thenReturn(savedEntity);

        assertEquals(id, this.carDao.saveCar(body), "Result ID different from expected");

        verify(this.carConverter, times(1)).toEntity(body);
        verify(this.carRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link CarDao#updateCar} method.
     */
    @Test
    @DisplayName("Updating a car")
    void testUpdateCar() {
        final Car body = new Car();
        final CarEntity entity = new CarEntity();
        when(this.carConverter.toEntity(body)).thenReturn(entity);

        final CarEntity savedEntity = new CarEntity();
        final Integer id = 0;
        savedEntity.setId(id);
        when(this.carRepository.save(entity)).thenReturn(null);

        this.carDao.updateCar(body);

        verify(this.carConverter, times(1)).toEntity(body);
        verify(this.carRepository, times(1)).save(entity);
    }

    /**
     * Tests the {@link CarDao#deleteCarById} method with an existing car.
     */
    @Test
    @DisplayName("Deleting an existing car")
    void testDeleteExistingCarById() {
        final Integer id = 0;
        when(this.carRepository.existsById(id)).thenReturn(true);
        doNothing().when(this.carRepository).deleteById(id);

        assertTrue(this.carDao.deleteCarById(id), "Result different from expected");

        verify(this.carRepository, times(1)).existsById(id);
        verify(this.carRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the {@link CarDao#deleteCarById} method with an inexistant car.
     */
    @Test
    @DisplayName("Deleting an inexistant car")
    void testDeleteInexistantCarById() {
        final Integer id = 0;
        when(this.carRepository.existsById(id)).thenReturn(false);

        assertFalse(this.carDao.deleteCarById(id), "Result different from expected");

        verify(this.carRepository, times(1)).existsById(id);
        verify(this.carRepository, times(0)).deleteById(id);
    }

}
