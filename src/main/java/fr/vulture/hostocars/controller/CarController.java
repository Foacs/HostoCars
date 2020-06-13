package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.utils.ControllerUtils.INTERNAL_SERVER_ERROR_CODE;
import static fr.vulture.hostocars.utils.ControllerUtils.NO_CONTENT_CODE;
import static fr.vulture.hostocars.utils.ControllerUtils.OK_CODE;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import fr.vulture.hostocars.dao.CarDao;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.pojo.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
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
@CrossOrigin(origins = "*")
@RequestMapping("/cars")
@Tags(@Tag(name = "Cars", description = "Services related to cars."))
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
    @Operation(summary = "Gets all cars.", description = "Retrieves the list of all the cars from the database. A sorting field can be specified.",
        responses = {@ApiResponse(description = "At least one car has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
            @ApiResponse(description = "No car has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getCars(@Parameter(description = "The sorting field.") @RequestParam(required = false) final String sortedBy) {
        log.info("[getCars <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the DAO to retrieve the list of all the cars from the database
            final List<Car> resultList = this.carDao.getCars(sortedBy);

            // If no result has been found, returns a 204 status
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
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    @Operation(summary = "Gets a car by its ID.", description = "Retrieves the car corresponding to the specified ID from the database.",
        responses = {@ApiResponse(description = "A car has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Car.class))),
            @ApiResponse(description = "No car has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getCarById(@Parameter(description = "The car ID to search.", required = true) @PathVariable @NonNull final Integer id) {
        log.info("[getCarById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the car with the given ID from the database
            final Car result = this.carDao.getCarById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getCarById => {}] No car found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getCarById => {}] Car found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getCarById => {}]", INTERNAL_SERVER_ERROR.value(), e);
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
    @Operation(summary = "Gets all distinct car registrations.",
        description = "Retrieves the list of all the distinct car registrations from the database.",
        responses = {@ApiResponse(description = "At least one car registration has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(description = "No car registration has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getCarRegistrations() {
        log.info("[getCarRegistrations <= Calling]");

        try {
            // Calls the DAO to retrieve the set of all distinct car registration numbers from the database
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
            log.error("[getCarById => {}]", INTERNAL_SERVER_ERROR.value(), e);
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
    @Operation(summary = "Searches for cars.", description = "Retrieves the list of cars that match the specified body from the database.",
        responses = {@ApiResponse(description = "At least one car has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
            @ApiResponse(description = "No car has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> searchCars(@Parameter(required = true) @RequestBody @NonNull final Car body) {
        log.info("[searchCars <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the car matching the given body from the database
            final List<Car> resultList = this.carDao.searchCars(body);

            // If no result has been found, returns a 204 status
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
    @Operation(summary = "Saves a car.", description = "Inserts a new car in the database.",
        responses = {@ApiResponse(description = "The car has been inserted successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> saveCar(@Parameter(required = true) @RequestBody @NonNull final Car body) {
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
    @Operation(summary = "Updates a car.", description = "Updates an existing car in the database.",
        responses = {@ApiResponse(description = "The car has been updated successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> updateCar(@Parameter(required = true) @RequestBody @NonNull final Car body) {
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
    @Operation(summary = "Deletes a car by its ID.", description = "Deletes the car corresponding to the specified ID from the database.",
        responses = {@ApiResponse(description = "The car has been deleted successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "No car has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> deleteCarById(
        @Parameter(description = "The ID of the car to delete.", required = true) @PathVariable @NonNull final Integer id) {
        log.info("[deleteCarById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the car with the given ID from the database
            if (this.carDao.deleteCarById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteCarById => {}] Car deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
            log.info("[deleteCarById => {}] No car found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteCarById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
