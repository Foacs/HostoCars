package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.converter.CarConverter;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.entity.CarEntity;
import fr.vulture.hostocars.repository.CarRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * DAO for the {@link Car} DTO.
 */
@Slf4j
@Component
public class CarDao {

    @NonNull
    private final CarRepository carRepository;

    @NonNull
    private final CarConverter carConverter;

    /**
     * Valued autowired constructor.
     *
     * @param carRepository
     *     The autowired {@link CarRepository} component
     * @param carConverter
     *     The autowired {@link CarConverter} component
     */
    public CarDao(@NonNull final CarRepository carRepository, @NonNull final CarConverter carConverter) {
        this.carRepository = carRepository;
        this.carConverter = carConverter;
    }

    /**
     * Retrieves the list of all the {@link Car} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return a list of {@link Car}
     */
    public List<Car> getCars(final String sortedBy) {
        log.trace("[getCars] With sorting field = {}", sortedBy);

        // Calls the car repository to retrieve the list of all the cars from the database
        final List<CarEntity> resultEntityList = nonNull(sortedBy) ? this.carRepository.findAll(Sort.by(sortedBy)) : this.carRepository.findAll();

        // Converts and returns the list of car entities to a list of DTOs
        return this.carConverter.toDtoList(resultEntityList);
    }

    /**
     * Retrieves the {@link Car} with the given ID from the database.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return a {@link Car}
     */
    public Car getCarById(@NonNull final Integer id) {
        log.trace("[getCarByID] With ID = {}", id);

        // Calls the car repository to retrieve the car with the given ID from the database
        final Optional<CarEntity> resultEntity = this.carRepository.findById(id);

        // Converts and returns the car entity to a DTO
        return resultEntity.map(this.carConverter::toDto).orElse(null);
    }

    /**
     * Retrieves the set of all distinct {@link Car} registration numbers from the database.
     *
     * @return a set of registration numbers
     */
    public Set<String> getCarRegistrations() {
        log.trace("[getCarRegistrations <= Calling]");

        // Calls the repository to retrieve and return the list of all distinct car registration numbers from the database
        return this.carRepository.findDistinctRegistrationNumbers();
    }

    /**
     * Retrieves the list of {@link Car} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return a list of {@link Car}
     */
    public List<Car> searchCars(@NonNull final Car body) {
        log.trace("[searchCars] With body = {}", body);

        // Converts the given body to an entity
        final CarEntity entity = this.carConverter.toEntity(body);

        // Calls the car repository to retrieve the list of cars matching the entity from the database
        final List<CarEntity> resultEntityList =
            this.carRepository.findAll(Example.of(entity, DEFAULT_MATCHER.withIgnorePaths("certificate", "picture")));

        // Converts and returns the list of car entities to a list of DTOs
        return this.carConverter.toDtoList(resultEntityList);
    }

    /**
     * Inserts a new {@link Car} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return the generated ID
     */
    public Integer saveCar(@NonNull final Car body) {
        log.trace("[saveCar] With body = {}", body);

        // Converts the given body to an entity
        final CarEntity entity = this.carConverter.toEntity(body);

        // Calls the car repository to insert the entity in the database
        final CarEntity result = this.carRepository.save(entity);

        // Retrieves and returns the generated ID
        return result.getId();
    }

    /**
     * Updates a {@link Car} with the given body in the database.
     *
     * @param body
     *     The body
     */
    public void updateCar(@NonNull final Car body) {
        log.trace("[updateCar] With body = {}", body);

        // Converts the given body to an entity
        final CarEntity entity = this.carConverter.toEntity(body);

        // Calls the car repository to update the entity in the database
        this.carRepository.save(entity);
    }

    /**
     * Deletes a {@link Car} from the database by its ID.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return if the {@link Car} existed in the database
     */
    public boolean deleteCarById(@NonNull final Integer id) {
        log.trace("[deleteCarByID] With ID = {}", id);

        // Checks if a car exists in the database with the given ID
        if (this.carRepository.existsById(id)) {
            // Calls the repository to delete the car with the given ID
            this.carRepository.deleteById(id);

            // Returns true
            return true;
        }

        // If no car exists in the database with the given ID, returns false
        return false;
    }

}
