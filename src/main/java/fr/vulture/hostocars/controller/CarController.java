package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.model.request.api.QueryArgumentType.INTEGER;
import static fr.vulture.hostocars.util.SQLUtils.MINIMUM_ID;
import static java.util.Objects.*;
import static org.springframework.http.HttpStatus.*;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.FunctionalException;
import fr.vulture.hostocars.error.TechnicalException;
import fr.vulture.hostocars.model.Car;
import fr.vulture.hostocars.model.converter.CarConverter;
import fr.vulture.hostocars.model.request.CarRequestBody;
import fr.vulture.hostocars.model.request.api.QueryArgument;
import fr.vulture.hostocars.util.SQLUtils;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * REST controller for the cars.
 */
@RestController
@RequestMapping(value = "/cars")
@CrossOrigin(origins = "*")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private static final String GET_CARS_QUERY = "SELECT * FROM Cars";
    private static final String GET_CARS_SORTED_BY_QUERY = "SELECT * FROM Cars ORDER BY ?";
    private static final String GET_CAR_BY_ID_QUERY = "SELECT * FROM Cars WHERE id = ?";
    private static final String SEARCH_CARS_QUERY = "SELECT * FROM Cars";
    private static final String GET_DISTINCT_FIELD_VALUES_QUERY = "SELECT DISTINCT ? FROM Cars";
    private static final String SAVE_CAR_QUERY = "INSERT INTO Cars";
    private static final String UPDATE_CAR_BY_ID_QUERY = "UPDATE Cars SET ";
    private static final String DELETE_CAR_BY_ID_QUERY = "DELETE FROM Cars WHERE id = ?";

    @Autowired
    private DatabaseConnection connection;

    /**
     * Retrieves the list of all the cars from the database.
     *
     * @return an HTTP response
     */
    @GetMapping
    public ResponseEntity<?> getCars(@RequestParam(required = false) String sortedBy) {
        logger.debug("[getCars <= Calling]");

        try {
            // Prepares the statement
            PreparedStatement statement;
            if (nonNull(sortedBy)) {
                // If the sorting field is missing or empty, throws a functional exception
                if (sortedBy.isEmpty()) {
                    throw new FunctionalException("Empty sorting field name");
                }

                // If the sorting field is inexistant in the Cars table or irrelevant for the query, throws a functional exception
                if (!CarRequestBody.hasRelevantField(sortedBy)) {
                    throw new FunctionalException("Inexistant or irrelevant sorting field in the Cars table");
                }

                statement = connection.prepareStatement(GET_CARS_SORTED_BY_QUERY.replace("?", sortedBy));
            } else {
                statement = connection.prepareStatement(GET_CARS_QUERY);
            }

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant cars
            final List<Car> cars = new ArrayList<>();
            while (result.next()) {
                // Converts the current query result to a car entity
                final Car car = CarConverter.from(result);

                // If the car entity is null, throws a technical exception
                if (isNull(car)) {
                    throw new TechnicalException("The query returned a null element");
                }

                // Adds the car entity to the result list
                cars.add(car);
            }

            // If no car was found, returns a 204 status
            if (cars.isEmpty()) {
                logger.debug("[getCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // Returns the list of found cars with a 200 status
            logger.debug("[getCars => {}] {} car(s) found", OK.value(), cars.size());
            return ResponseEntity.ok(cars);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[getCars => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[getCars => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the car with the given ID from the database.
     *
     * @param id
     *     The car's ID
     *
     * @return an HTTP response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCarByID(@PathVariable final Integer id) {
        logger.debug("[getCarByID <= Calling] With ID = {}", id);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing car ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The ID can not be negative or zero");
            }

            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(GET_CAR_BY_ID_QUERY);

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Sets the query's arguments
            statement.setInt(1, id);

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant car
            if (result.next()) {
                // Converts the query result to a car entity
                final Car car = CarConverter.from(result);

                // If there is more than one result, throws a technical exception
                if (result.next()) {
                    throw new TechnicalException("More than one car found for ID = {} in the database", id);
                }

                // If the found car entity is not null, returns it with a 200 status
                if (nonNull(car)) {
                    logger.debug("[getCarByID => {}] Car found for ID = {}", OK.value(), id);
                    return ResponseEntity.ok(car);
                }
            }

            // Returns a 204 status
            logger.debug("[getCarByID => {}] No car found for ID = {}", NO_CONTENT.value(), id);
            return ResponseEntity.noContent().build();
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[getCarByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[getCarByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the distinct values of the given field from the database.
     *
     * @param field
     *     The field name
     *
     * @return the distinct values of the given field from the database
     */
    @GetMapping(value = "/field/{field}")
    public ResponseEntity<?> getDistinctFieldValues(@PathVariable final String field) {
        logger.debug("[getDistinctFieldValues <= Calling] With field = {}", field);

        try {
            // If the field is missing or empty, throws a functional exception
            if (isNull(field) || field.isEmpty()) {
                throw new FunctionalException("Missing or empty field name");
            }

            // If the field is inexistant in the Cars table or irrelevant for the query, throws a functional exception
            if (!CarRequestBody.hasRelevantField(field)) {
                throw new FunctionalException("Inexistant or irrelevant field in the Cars table");
            }

            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(GET_DISTINCT_FIELD_VALUES_QUERY.replace("?", field));

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant values
            final List<Object> values = new ArrayList<>();
            while (result.next()) {
                final Object value = result.getObject(1);

                // If the current value is not null, adds it to the resultant list
                if (nonNull(value)) {
                    if (values.contains(value)) {
                        // If the current value has already been added to the list, raises a warning
                        logger.warn("[getDistinctFieldValues] Duplicate value found ({}); ignored", value);
                    } else {
                        // Else, just adds it
                        values.add(value);
                    }
                }
            }

            // If no value was found, returns a 204 status
            if (values.isEmpty()) {
                logger.debug("[getDistinctFieldValues => {}] No value found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // Returns the list of found values with a 200 status
            logger.debug("[getDistinctFieldValues => {}] {} distinct values(s) found", OK.value(), values.size());
            return ResponseEntity.ok(values);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[getDistinctFieldValues => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[getDistinctFieldValues => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the list of cars corresponding to filters from a JSON body in the database.
     *
     * @param body
     *     The car request body to search
     *
     * @return an HTTP response
     */
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchCars(@RequestBody final CarRequestBody body) {
        logger.debug("[searchCars <= Calling] With body = {}", body);

        try {
            // Prepares the statement
            final PreparedStatement statement = SQLUtils.generateStatementWithWhereClause(connection, SEARCH_CARS_QUERY, body);

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant cars
            final List<Car> cars = new ArrayList<>();
            while (result.next()) {
                // Converts the current query result to a car entity
                final Car car = CarConverter.from(result);

                // If the car entity is null, throws a technical exception
                if (isNull(car)) {
                    throw new TechnicalException("The query returned a null element");
                }

                // Adds the car entity to the result list
                cars.add(car);
            }

            // If no car was found, returns a 204 status
            if (cars.isEmpty()) {
                logger.debug("[searchCars => {}] No car found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // Returns the list of found cars with a 200 status
            logger.debug("[searchCars => {}] {} car(s) found", OK.value(), cars.size());
            return ResponseEntity.ok(cars);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[searchCars => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[searchCars => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Saves a new car from a JSON body in the database.
     *
     * @param body
     *     The car request body to save
     *
     * @return an HTTP response
     */
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCar(@RequestBody final CarRequestBody body) {
        logger.debug("[saveCar <= Calling] With body = {}", body);

        try {
            // Prepares the statement
            final PreparedStatement statement = SQLUtils.generateStatementWithInsertClause(connection, SAVE_CAR_QUERY, body);

            // Executes the query and retrieves the generated keys
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the generated entity ID
            if (result.next()) {
                // Retrieves the generated key
                final int generatedID = result.getInt(1);

                // If the generated ID is 0, throws a technical exception
                if (MINIMUM_ID.compareTo(generatedID) > 0) {
                    throw new TechnicalException("The new car has not been saved");
                }

                // If there is more than one generated key, throws a technical exception
                if (result.next()) {
                    throw new TechnicalException("More than one car has been saved in the database");
                }

                // Returns the generated key with a 200 status
                logger.debug("[saveCar => {}] New car saved with ID = {}", OK.value(), generatedID);
                return ResponseEntity.ok(generatedID);
            }

            // If the car has not been saved, throws a technical exception
            throw new TechnicalException("The new car has not been saved");
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[saveCar => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (IOException | SQLException | TechnicalException e) {
            logger.error("[saveCar => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Updates a car from its ID with the data from a JSON body in the database.
     *
     * @param id
     *     The ID of the car to delete
     * @param body
     *     The data to update
     *
     * @return an HTTP response
     */
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<?> updateCarByID(@PathVariable final Integer id, @RequestBody final CarRequestBody body) {
        logger.debug("[updateCarByID <= Calling] With ID = {} and body = {}", id, body);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing car ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The car ID can not be negative or zero");
            }

            // Prepares the statement
            final QueryArgument idArgument = new QueryArgument("id", id, INTEGER);
            final PreparedStatement statement = SQLUtils.generateStatementWithUpdateClause(connection, UPDATE_CAR_BY_ID_QUERY, body, idArgument);

            // Executes the query and retrieves the number of updated cars
            int result = statement.executeUpdate();

            switch (result) {
                case 0:
                    // If no car has been updated, returns a 204 status
                    logger.debug("[updateCarByID => {}] No car found to update", NO_CONTENT.value());
                    return ResponseEntity.noContent().build();
                case 1:
                    // If exactly one car has been updated, returns a 200 status
                    logger.debug("[updateCarByID => {}] Car updated", OK.value());
                    return ResponseEntity.ok().build();
                default:
                    // If there more than one cars have been updated, throws a technical exception
                    throw new TechnicalException("More than one car has been updated in the database");
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[updateCarByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (IOException | SQLException | TechnicalException e) {
            logger.error("[updateCarByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Deletes a car from its ID from the database.
     *
     * @param id
     *     The ID of the car to delete
     *
     * @return an HTTP response
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteCarByID(@PathVariable final Integer id) {
        logger.debug("[deleteCarByID <= Calling] With ID = {}", id);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing car ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The car ID can not be negative or zero");
            }

            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID_QUERY);

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Sets the query's arguments
            statement.setInt(1, id);

            // Executes the query and retrieves the number of updated cars
            int result = statement.executeUpdate();

            switch (result) {
                case 0:
                    // If no car has been deleted, returns a 204 status
                    logger.debug("[deleteCarByID => {}] No car found to delete", NO_CONTENT.value());
                    return ResponseEntity.noContent().build();
                case 1:
                    // If exactly one car has been deleted, returns a 200 status
                    logger.debug("[deleteCarByID => {}] Car deleted", OK.value());
                    return ResponseEntity.ok().build();
                default:
                    // If there more than one cars have been deleted, throws a technical exception
                    throw new TechnicalException("More than one car has been deleted from the database");
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[deleteCarByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[deleteCarByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

}
