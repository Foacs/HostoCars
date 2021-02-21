package fr.vulture.hostocars.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import fr.vulture.hostocars.configuration.Loggable;
import fr.vulture.hostocars.entity.Car;
import fr.vulture.hostocars.repository.CarRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.Collection;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cars")
@Tags(@Tag(name = "Cars", description = "Services related to cars."))
public class CarController {

    private final CarRepository repository;
    private final ControllerHelper helper;

    /**
     * Valued autowired constructor.
     *
     * @param repository
     *     The autowired {@link CarRepository} component
     * @param helper
     *     The autowired {@link ControllerHelper} component
     */
    @Autowired
    public CarController(final CarRepository repository, final ControllerHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    /**
     * Retrieves the list of all the {@link Car} from the database. A list of sorting fields can also be specified.
     *
     * @param sortingFields
     *     The optional sorting clause fields
     *
     * @return an HTTP response
     */
    @Loggable
    @GetMapping
    @Operation(summary = "Gets all cars.", description = "Retrieves the list of all the cars from the database. A list of sorting fields can also be specified.",
        responses = @ApiResponse(description = "At least one car has been found.", responseCode = "200",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Car.class)))))
    public ResponseEntity<Collection<Car>> getCars(@Parameter(description = "The sorting fields.") @RequestParam(required = false) final String... sortingFields) {
        return this.helper.resolveGetCollectionResponse(() -> Objects.isNull(sortingFields) ? this.repository.findAll() : this.repository.findAll(Sort.by(sortingFields)));
    }

    /**
     * Retrieves the {@link Car} with the given ID from the database.
     *
     * @param id
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @Loggable
    @GetMapping("/{id}")
    @Operation(summary = "Gets a car by its ID.", description = "Retrieves the car corresponding to the specified ID from the database.",
        responses = @ApiResponse(description = "A car has been found.", responseCode = "200",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Car.class))))
    public ResponseEntity<Car> getCarById(@Parameter(description = "The car ID to search.", required = true) @PathVariable @NonNull final Integer id) {
        return this.helper.resolveGetResponse(() -> this.repository.findById(id));
    }

    /**
     * Inserts a new {@link Car} in the database, generated from the REST call body.
     *
     * @param car
     *     The {@link Car} to insert
     *
     * @return an HTTP response
     */
    @Loggable
    @PostMapping
    @Operation(summary = "Inserts a new car.", description = "Inserts a new car in the database.",
        responses = @ApiResponse(description = "The car has been inserted successfully.", responseCode = "201", content = @Content))
    public ResponseEntity<?> createCar(@Parameter(required = true) @RequestBody @NonNull final Car car) {
        return this.helper.resolvePostResponse(() -> "/cars/" + this.repository.save(car).getId());
    }

    /**
     * Updates a {@link Car} with the given REST call body in the database.
     *
     * @param car
     *     The {@link Car} to update
     *
     * @return an HTTP response
     */
    @Loggable
    @PutMapping
    @Operation(summary = "Updates a car.", description = "Updates an existing car in the database.",
        responses = @ApiResponse(description = "The car has been updated successfully.", responseCode = "201", content = @Content))
    public ResponseEntity<?> updateCar(@Parameter(required = true) @RequestBody @NonNull final Car car) {
        return this.helper.resolvePutResponse(() -> this.repository.save(car));
    }

    /**
     * Deletes a {@link Car} in the database by its ID.
     *
     * @param id
     *     The ID of the {@link Car} to delete
     *
     * @return an HTTP response
     */
    @Loggable
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a car by its ID.", description = "Deletes the car corresponding to the specified ID from the database.",
        responses = @ApiResponse(description = "The car has been deleted successfully.", responseCode = "204", content = @Content))
    public ResponseEntity<?> deleteCarById(@Parameter(description = "The ID of the car to delete.", required = true) @PathVariable @NonNull final Integer id) {
        return this.helper.resolveDeleteResponse(() -> this.repository.deleteById(id));
    }

}
