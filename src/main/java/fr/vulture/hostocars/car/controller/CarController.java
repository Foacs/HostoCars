package fr.vulture.hostocars.car.controller;

import static java.sql.Types.BLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.car.entity.Car;
import fr.vulture.hostocars.database.builder.Query;
import fr.vulture.hostocars.database.builder.QueryArgument;
import fr.vulture.hostocars.database.builder.QueryBuilder;
import fr.vulture.hostocars.database.controller.DatabaseController;
import fr.vulture.hostocars.error.ResponseData;
import fr.vulture.hostocars.error.exception.FunctionalException;
import fr.vulture.hostocars.error.exception.TechnicalException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for the {@code Cars} table.
 */
@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private static final String TABLE_NAME = "Cars";
    private static final String ID_COLUMN_NAME = "id";
    private static final String REGISTRATION_COLUMN_NAME = "registration";
    private static final String SERIAL_NUMBER_COLUMN_NAME = "serialNumber";
    private static final String OWNER_COLUMN_NAME = "owner";
    private static final String BRAND_COLUMN_NAME = "brand";
    private static final String MODEL_COLUMN_NAME = "model";
    private static final String MOTORIZATION_COLUMN_NAME = "motorization";
    private static final String ENGINE_CODE_COLUMN_NAME = "engineCode";
    private static final String RELEASE_DATE_COLUMN_NAME = "releaseDate";
    private static final String COMMENTS_COLUMN_NAME = "comments";
    private static final String CERTIFICATE_COLUMN_NAME = "certificate";
    private static final String PICTURE_COLUMN_NAME = "picture";

    private final DatabaseController databaseController;

    /**
     * Valued constructor.
     *
     * @param databaseController
     *     The database controller
     */
    @Autowired
    public CarController(final DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Retrieves the list of all the {@link Car} entities from the database.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public final ResponseEntity<?> getCars(@RequestParam(required = false) final String sortedBy) {
        logger.info("[getCars <= Calling] With sorting field = {}", sortedBy);

        try {
            // Builds the query
            final QueryBuilder queryBuilder = new QueryBuilder().buildSelectQuery(TABLE_NAME, false);

            // If the optional sorting field is not null, adds a sorting clause to the query
            if (nonNull(sortedBy)) {
                final List<String> sortingFields = Collections.singletonList(sortedBy);
                queryBuilder.addOrderByClause(sortingFields, false);
            }

            // Retrieves the built query
            final Query query = queryBuilder.getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement
            final ResultSet result = statement.executeQuery();

            // Retrieves the resultant entities
            final List<Car> cars = new ArrayList<>(0);
            while (result.next()) {
                // Extracts the next entity from the result set
                final Car car = extractEntityFromResultSet(result);

                // Adds the entity to the result list
                cars.add(car);
            }

            // If no entity was found, returns a 204 status
            if (cars.isEmpty()) {
                logger.info("[getCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one entity has been found, returns the list with a 200 status
            logger.info("[getCars => {}] {} car(s) found", OK.value(), cars.size());
            return ResponseEntity.ok(cars);
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[getCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[getCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Extracts a {@link Car} entity from a {@link ResultSet}.
     *
     * @param resultSet
     *     The result set to extract the {@link Car} entity from
     *
     * @return the extracted {@link Car} entity
     *
     * @throws SQLException
     *     if the extraction fails
     */
    private static Car extractEntityFromResultSet(@NotNull final ResultSet resultSet) throws SQLException {
        final Car car = new Car();

        // Extracts the integers
        car.setId(resultSet.getInt(ID_COLUMN_NAME));

        // Extracts the strings
        car.setRegistration(resultSet.getString(REGISTRATION_COLUMN_NAME));
        car.setSerialNumber(resultSet.getString(SERIAL_NUMBER_COLUMN_NAME));
        car.setOwner(resultSet.getString(OWNER_COLUMN_NAME));
        car.setBrand(resultSet.getString(BRAND_COLUMN_NAME));
        car.setModel(resultSet.getString(MODEL_COLUMN_NAME));
        car.setMotorization(resultSet.getString(MOTORIZATION_COLUMN_NAME));
        car.setEngineCode(resultSet.getString(ENGINE_CODE_COLUMN_NAME));
        car.setComments(resultSet.getString(COMMENTS_COLUMN_NAME));

        // Extracts the dates
        final String releaseDateString = resultSet.getString(RELEASE_DATE_COLUMN_NAME);
        car.setReleaseDate(nonNull(releaseDateString) ? LocalDate.parse(releaseDateString) : null);

        // Extracts the BLOBs
        car.setCertificate(resultSet.getBytes(CERTIFICATE_COLUMN_NAME));
        car.setPicture(resultSet.getBytes(PICTURE_COLUMN_NAME));

        return car;
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
    public final ResponseEntity<?> getCarByID(@PathVariable @NotNull @Min(1) final Integer id) {
        logger.info("[getCarByID <= Calling] With ID = {}", id);

        try {
            // Builds the query
            final List<QueryArgument> arguments = Collections.singletonList(new QueryArgument(ID_COLUMN_NAME, id, INTEGER));
            final Query query = new QueryBuilder()
                .buildSelectQuery(TABLE_NAME, false)
                .addWhereClause(arguments)
                .getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement
            final ResultSet result = statement.executeQuery();

            // If an entity has been found, retrieves it
            if (result.next()) {
                // Returns the extracted entity with a 200 status
                logger.info("[getCarByID => {}] Car found for ID = {}", OK.value(), id);
                return ResponseEntity.ok(extractEntityFromResultSet(result));
            }

            // If no entity has been found, returns a 204 status
            logger.info("[getCarByID => {}] No car found for ID = {}", NO_CONTENT.value(), id);
            return ResponseEntity.noContent().build();
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[getCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[getCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Retrieves the distinct values of the input field from the {@code Cars} table in the database. The {@code NULL} values are excluded.
     *
     * @param field
     *     The field to get values from
     *
     * @return an HTTP response
     */
    @GetMapping("/{field}/values")
    public final ResponseEntity<?> getDistinctFieldValues(@PathVariable @NotNull final String field) {
        logger.info("[getDistinctFieldValues <= Calling] With field = {}", field);

        try {
            // If the field is inexistant in the Cars table or irrelevant for the query, throws a functional exception
            if (isFieldIrrelevant(field)) {
                throw new FunctionalException("Inexistant or irrelevant field in the Cars table");
            }

            // Builds the query
            final List<String> fields = Collections.singletonList(field);
            final Query query = new QueryBuilder().buildSelectQuery(TABLE_NAME, fields, true).addOrderByClause(fields, false).getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement
            final ResultSet result = statement.executeQuery();

            // Retrieves the resultant entities
            final List<Object> values = new ArrayList<>(0);
            while (result.next()) {
                final Object value = result.getObject(1);

                // If the current value is not null, adds it to the result list
                if (nonNull(value)) {
                    values.add(value);
                }
            }

            // If no value was found, returns a 204 status
            if (values.isEmpty()) {
                logger.info("[getDistinctFieldValues => {}] No value found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one value has been found, returns the list with a 200 status
            logger.info("[getDistinctFieldValues => {}] {} distinct values(s) found", OK.value(), values.size());
            return ResponseEntity.ok(values);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (final FunctionalException e) {
            logger.error("[getDistinctFieldValues => {}]", BAD_REQUEST.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.badRequest().body(new ResponseData(responseMessage));
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[getDistinctFieldValues => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[getDistinctFieldValues => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Returns whether or not the input field exists and is relevant for searching over the {@link Car} entities.
     *
     * @param field
     *     The field name
     *
     * @return if the field exists and is relevant or not
     */
    private static boolean isFieldIrrelevant(@NotNull final String field) {
        return !Arrays.asList(REGISTRATION_COLUMN_NAME, SERIAL_NUMBER_COLUMN_NAME, OWNER_COLUMN_NAME, BRAND_COLUMN_NAME, MODEL_COLUMN_NAME,
            MOTORIZATION_COLUMN_NAME, ENGINE_CODE_COLUMN_NAME, RELEASE_DATE_COLUMN_NAME).contains(field);
    }

    /**
     * Retrieves the list of {@link Car} entities matching the input body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public final ResponseEntity<?> searchCars(@RequestBody @NotNull final CarRequestBody body) {
        logger.info("[searchCars <= Calling] With body = {}", body);

        try {
            // Gets the query arguments
            final List<QueryArgument> arguments = getSearchRelevantFields(body);

            // If there is no query argument, throw an exception
            if (arguments.isEmpty()) {
                throw new FunctionalException("No search filter has been provided");
            }

            // Builds the query
            final Query query = new QueryBuilder().buildSelectQuery(TABLE_NAME, false).addWhereClause(arguments).getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement
            final ResultSet result = statement.executeQuery();

            // Retrieves the resultant entities
            final List<Car> cars = new ArrayList<>(0);
            while (result.next()) {
                // Extracts the next entity from the result set
                final Car car = extractEntityFromResultSet(result);

                // Adds the entity to the result list
                cars.add(car);
            }

            // If no entity was found, returns a 204 status
            if (cars.isEmpty()) {
                logger.info("[searchCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one entity has been found, returns the list with a 200 status
            logger.info("[searchCars => {}] {} car(s) found", OK.value(), cars.size());
            return ResponseEntity.ok(cars);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (final FunctionalException e) {
            logger.error("[searchCars => {}]", BAD_REQUEST.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.badRequest().body(new ResponseData(responseMessage));
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[searchCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[searchCars => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Returns the list of {@link QueryArgument} relevant for creating or updating from the input {@link CarRequestBody}.
     *
     * @param body
     *     The body from which to extract the arguments
     *
     * @return a list of {@link QueryArgument}
     */
    private static List<QueryArgument> getUpdateRelevantFields(@NotNull final CarRequestBody body) {
        final List<QueryArgument> result = getSearchRelevantFields(body);

        if (nonNull(body.getComments())) {
            result.add(new QueryArgument(COMMENTS_COLUMN_NAME, body.getComments().orElse(null), VARCHAR));
        }

        if (nonNull(body.getCertificate())) {
            result.add(new QueryArgument(CERTIFICATE_COLUMN_NAME, body.getCertificate().orElse(null), BLOB));
        }

        if (nonNull(body.getPicture())) {
            result.add(new QueryArgument(PICTURE_COLUMN_NAME, body.getPicture().orElse(null), BLOB));
        }

        return result;
    }

    /**
     * Inserts a new {@link Car} entity in the database, generated from the input body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public final ResponseEntity<?> saveCar(@RequestBody @NotNull final CarRequestBody body) {
        logger.info("[saveCar <= Calling] With body = {}", body);

        try {
            // If any mandatory field is missing from the body, throws an exception
            if (isAnyMandatoryFieldMissing(body)) {
                throw new FunctionalException("Missing mandatory field(s)");
            }

            // Gets the query arguments
            final List<QueryArgument> arguments = getUpdateRelevantFields(body);

            // Builds the query
            final Query query = new QueryBuilder().buildInsertQuery(TABLE_NAME, arguments).getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, true);

            // Executes the statement
            statement.executeUpdate();

            // Gets the generated keys
            final ResultSet result = statement.getGeneratedKeys();

            // If there is a generated key, retrieves it
            if (result.next()) {
                // Retrieves the generated key
                final int generatedID = result.getInt(1);

                // Returns the generated key with a 200 status
                logger.info("[saveCar => {}] New car saved with ID = {}", OK.value(), generatedID);
                return ResponseEntity.ok(generatedID);
            }

            // If there is no generated key, throws a technical exception
            throw new TechnicalException("The new car has not been saved");
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (final FunctionalException e) {
            logger.error("[saveCar => {}]", BAD_REQUEST.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.badRequest().body(new ResponseData(responseMessage));
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[saveCar => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[saveCar => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Returns whether or not the input body misses a mandatory field for creating a new {@link Car} entity.
     *
     * @param body
     *     The body to check
     *
     * @return whether or not the input body misses a mandatory field
     */
    private static boolean isAnyMandatoryFieldMissing(@NotNull final CarRequestBody body) {
        return isNull(body.getOwner()) || !body.getOwner().isPresent() || isNull(body.getRegistration()) || !body.getRegistration().isPresent();
    }

    /**
     * Returns the list of {@link QueryArgument} relevant for searching from the input {@link CarRequestBody}.
     *
     * @param body
     *     The body from which to extract the arguments
     *
     * @return a list of {@link QueryArgument}
     */
    private static List<QueryArgument> getSearchRelevantFields(@NotNull final CarRequestBody body) {
        final List<QueryArgument> result = new ArrayList<>(0);

        if (nonNull(body.getRegistration())) {
            result.add(new QueryArgument(REGISTRATION_COLUMN_NAME, body.getRegistration().orElse(null), VARCHAR));
        }

        if (nonNull(body.getSerialNumber())) {
            result.add(new QueryArgument(SERIAL_NUMBER_COLUMN_NAME, body.getSerialNumber().orElse(null), VARCHAR));
        }

        if (nonNull(body.getOwner())) {
            result.add(new QueryArgument(OWNER_COLUMN_NAME, body.getOwner().orElse(null), VARCHAR));
        }

        if (nonNull(body.getBrand())) {
            result.add(new QueryArgument(BRAND_COLUMN_NAME, body.getBrand().orElse(null), VARCHAR));
        }

        if (nonNull(body.getModel())) {
            result.add(new QueryArgument(MODEL_COLUMN_NAME, body.getModel().orElse(null), VARCHAR));
        }

        if (nonNull(body.getMotorization())) {
            result.add(new QueryArgument(MOTORIZATION_COLUMN_NAME, body.getMotorization().orElse(null), VARCHAR));
        }

        if (nonNull(body.getEngineCode())) {
            result.add(new QueryArgument(ENGINE_CODE_COLUMN_NAME, body.getEngineCode().orElse(null), VARCHAR));
        }

        if (nonNull(body.getReleaseDate())) {
            result.add(new QueryArgument(RELEASE_DATE_COLUMN_NAME, body.getReleaseDate().orElse(null), DATE));
        }

        return result;
    }

    /**
     * Updates a {@link Car} entity with the input body in the database by its ID.
     *
     * @param id
     *     The {@link Car} ID
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/{id}/update")
    public final ResponseEntity<?> updateCarByID(@PathVariable @NotNull @Min(1) final Integer id, @RequestBody final CarRequestBody body) {
        logger.info("[updateCarByID <= Calling] With ID = {} and body = {}", id, body);

        try {
            // Gets the query arguments
            final List<QueryArgument> updateArguments = getUpdateRelevantFields(body);
            final List<QueryArgument> whereArguments = Collections.singletonList(new QueryArgument(ID_COLUMN_NAME, id, INTEGER));

            // If there is no query argument, throw an exception
            if (updateArguments.isEmpty()) {
                throw new FunctionalException("No update value has been provided");
            }

            // Builds the query
            final Query query = new QueryBuilder().buildUpdateQuery(TABLE_NAME, updateArguments, whereArguments).getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement and retrieves the number of updated lines
            final int result = statement.executeUpdate();

            if (0 == result) {
                // If no line has been updated, returns a 204 status
                logger.info("[updateCarByID => {}] No car found to update", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            } else {
                // If a line has been updated, returns a 200 status
                logger.info("[updateCarByID => {}] Car updated", OK.value());
                return ResponseEntity.ok().build();
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (final FunctionalException e) {
            logger.error("[updateCarByID => {}]", BAD_REQUEST.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.badRequest().body(new ResponseData(responseMessage));
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[updateCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[updateCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
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
    public final ResponseEntity<?> deleteCarByID(@PathVariable @NotNull @Min(1) final Integer id) {
        logger.info("[deleteCarByID <= Calling] With ID = {}", id);

        try {
            // Builds the query
            final List<QueryArgument> arguments = Collections.singletonList(new QueryArgument(ID_COLUMN_NAME, id, INTEGER));
            final Query query = new QueryBuilder().buildDeleteQuery(TABLE_NAME, arguments).getQuery();

            // Prepares the statement
            final PreparedStatement statement = this.databaseController.prepareStatement(query, false);

            // Executes the statement and retrieves the number of updated lines
            final int result = statement.executeUpdate();

            if (0 == result) {
                // If no line has been deleted, returns a 204 status
                logger.info("[deleteCarByID => {}] No car found to delete", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            } else {
                // If a line has been deleted, returns a 200 status
                logger.info("[deleteCarByID => {}] Car deleted", OK.value());
                return ResponseEntity.ok().build();
            }
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (final TechnicalException e) {
            logger.error("[deleteCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getMessage();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[deleteCarByID => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Representation of a car entity with optional fields for web service requests.
     */
    @Data
    private static class CarRequestBody {

        private Optional<String> registration;
        private Optional<String> serialNumber;
        private Optional<String> owner;
        private Optional<String> brand;
        private Optional<String> model;
        private Optional<String> motorization;
        private Optional<String> engineCode;
        private Optional<LocalDate> releaseDate;
        private Optional<String> comments;
        private Optional<byte[]> certificate;
        private Optional<byte[]> picture;

    }

}
