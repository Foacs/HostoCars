package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.entity.Car;
import fr.vulture.hostocars.pojo.Response;
import fr.vulture.hostocars.repository.CarRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for cars.
 */
@Slf4j
@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
public class CarController {

    @NonNull
    private final CarRepository carRepository;

    /**
     * Valued autowired constructor.
     *
     * @param carRepository
     *     The autowired {@link CarRepository} component
     */
    @Autowired
    public CarController(@NonNull final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Retrieves the list of all the {@link Car} entities from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public final ResponseEntity<?> getCars(@RequestParam(required = false) final String sortedBy) {
        log.info("[getCars <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the repository to retrieve the list of cars, sorted by the given field if it is not null
            final List<Car> resultList = nonNull(sortedBy) ? this.carRepository.findAll(by(sortedBy)) : this.carRepository.findAll();

            // If no entity was found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one entity has been found, returns the list with a 200 status
            log.info("[getCars => {}] {} car(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the {@link Car} entity with the given ID from the database.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    public final ResponseEntity<?> getCarById(@PathVariable @NonNull final Integer id) {
        log.info("[getCarByID <= Calling] With ID = {}", id);

        try {
            // Calls the repository to retrieve the car with the given ID
            final Optional<Car> result = this.carRepository.findById(id);

            // If an entity has been found, retrieves it
            if (result.isPresent()) {
                // Returns the extracted entity with a 200 status
                log.info("[getCarByID => {}] Car found for ID = {}", OK.value(), id);
                return ResponseEntity.ok(result.get());
            }

            // If no entity has been found, returns a 204 status
            log.info("[getCarByID => {}] No car found for ID = {}", NO_CONTENT.value(), id);
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Car} entities matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public final ResponseEntity<?> searchCars(@RequestBody @NonNull final Car body) {
        log.info("[searchCars <= Calling] With body = {}", body);

        try {
            // Calls the repository to retrieve the cars matching the given body
            final List<Car> resultList = this.carRepository.findAll(Example.of(body, DEFAULT_MATCHER.withIgnorePaths("certificate", "picture")));

            // If no entity was found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one entity has been found, returns the list with a 200 status
            log.info("[searchCars => {}] {} car(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[searchCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Inserts a new {@link Car} entity in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public final ResponseEntity<?> saveCar(@RequestBody @NonNull final Car body) {
        log.info("[saveCar <= Calling] With body = {}", body);

        try {
            // Calls the repository to save the given body
            final Car result = this.carRepository.save(body);

            // Retrieves the generated key
            final int generatedId = result.getId();

            // Returns the generated key with a 200 status
            log.info("[saveCar => {}] New car saved with ID = {}", OK.value(), generatedId);
            return ResponseEntity.ok(generatedId);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[saveCar => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Updates a {@link Car} entity with the given body in the database by its ID.
     *
     * @param id
     *     The {@link Car} ID
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/{id}/update")
    public final ResponseEntity<?> updateCarByID(@PathVariable @NonNull final Integer id, @RequestBody @NonNull final Car body) {
        log.info("[updateCarByID <= Calling] With ID = {} and body = {}", id, body);

        try {
            // Sets the given ID to the given body
            body.setId(id);

            // Calls the repository to update the given body
            this.carRepository.save(body);

            // Returns a 200 status
            log.info("[updateCarByID => {}] Car updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link Car} entity in the database by its ID.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    public final ResponseEntity<?> deleteCarByID(@PathVariable @NonNull final Integer id) {
        log.info("[deleteCarByID <= Calling] With ID = {}", id);

        try {
            // Calls the car repository to check if the given ID exists in the database
            if (this.carRepository.existsById(id)) {
                // If the ID exists, calls the car repository to delete the car corresponding to the given ID
                this.carRepository.deleteById(id);

                // Returns a 200 status
                log.info("[deleteCarByID => {}] Car deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID doesn't exist, returns a 204 status
            log.info("[deleteCarByID => {}] No car found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
