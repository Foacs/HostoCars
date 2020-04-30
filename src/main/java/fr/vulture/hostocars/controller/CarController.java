package fr.vulture.hostocars.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.CarDao;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.entity.CarEntity;
import fr.vulture.hostocars.pojo.Response;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
public class CarController {

    @NonNull
    private final CarDao carDao;

    /**
     * Valued autowired constructor.
     *
     * @param carDao
     *     The autowired {@link CarDao} component
     */
    @Autowired
    public CarController(@NonNull final CarDao carDao) {
        this.carDao = carDao;
    }

    /**
     * Retrieves the list of all the {@link Car} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public ResponseEntity<?> getCars(@RequestParam(required = false) final String sortedBy) {
        log.info("[getCars <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the DAO to retrieve the list of all the cars from the database
            final List<Car> resultList = this.carDao.getCars(sortedBy);

            // If no result was found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
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
     * Retrieves the {@link Car} with the given ID from the database.
     *
     * @param id
     *     The {@link CarEntity} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable @NonNull final Integer id) {
        log.info("[getCarByID <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the car with the given ID from the database
            final Car result = this.carDao.getCarById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getCarByID => {}] No car found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getCarByID => {}] Car found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of all distinct {@link Car} registration numbers from the database.
     *
     * @return an HTTP response
     */
    @GetMapping("/registrations")
    public ResponseEntity<?> getCarRegistrations() {
        log.info("[getCarRegistrations <= Calling]");

        try {
            // Calls the DAO to retrieve the list of all distinct car registration numbers from the database
            final Set<String> resultList = this.carDao.getCarRegistrations();

            // If no result has been found, returns a 204 status
            if (isNull(resultList) || resultList.isEmpty()) {
                log.info("[getCarRegistrations => {}] No registration number found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getCarRegistrations => {}] {} registration number(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Car} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchCars(@RequestBody @NonNull final Car body) {
        log.info("[searchCars <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the car matching the given body from the database
            final List<Car> resultList = this.carDao.searchCars(body);

            // If no result was found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
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
     * Inserts a new {@link Car} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveCar(@RequestBody @NonNull final Car body) {
        log.info("[saveCar <= Calling] With body = {}", body);

        try {
            // Calls the DAO to insert a new car in the database
            final Integer generatedId = this.carDao.saveCar(body);

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
     * Updates a {@link Car} with the given body in the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateCar(@RequestBody @NonNull final Car body) {
        log.info("[updateCar <= Calling] With body = {}", body);

        try {
            // Calls the DAO to update the given car in the database
            this.carDao.updateCar(body);

            // Returns a 200 status
            log.info("[updateCar => {}] Car updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateCar => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link Car} in the database by its ID.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCarById(@PathVariable @NonNull final Integer id) {
        log.info("[deleteCarByID <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the car with the given ID from the database
            if (this.carDao.deleteCarById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteCarByID => {}] Car deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
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
