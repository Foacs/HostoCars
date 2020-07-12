package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.utils.ControllerUtils.INTERNAL_SERVER_ERROR_CODE;
import static fr.vulture.hostocars.utils.ControllerUtils.NO_CONTENT_CODE;
import static fr.vulture.hostocars.utils.ControllerUtils.OK_CODE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import fr.vulture.hostocars.dao.OperationDao;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.pojo.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.Arrays;
import java.util.List;
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
 * REST controller for operations.
 */
@Slf4j
@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/operations")
@Tags(@Tag(name = "Operations", description = "Services related to operations."))
public class OperationController {

    @NonNull
    private final OperationDao operationDao;

    /**
     * Valued autowired constructor.
     *
     * @param operationDao
     *     The autowired {@link OperationDao} component
     */
    @Autowired
    public OperationController(final OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * Retrieves the list of all the {@link Operation} from the database. A list of sorting fields can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause fields
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets all operations.",
        description = "Retrieves the list of all the operations from the database. A list of sorting fields can also be specified.",
        responses = {@ApiResponse(description = "At least one operation has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Operation.class)))),
            @ApiResponse(description = "No operation has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getOperations(@Parameter(description = "The sorting fields.") @RequestParam(required = false) final String... sortedBy) {
        log.info("[getOperations <= Calling] With sorting fields = {}", nonNull(sortedBy) ? Arrays.asList(sortedBy) : null);

        try {
            // Calls the DAO to retrieve the list of all the operations from the database
            final List<Operation> resultList = this.operationDao.getOperations(sortedBy);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getOperations => {}] No operation found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getOperations => {}] {} operation(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperations => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the {@link Operation} with the given ID from the database.
     *
     * @param id
     *     The {@link Operation} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets a operation by its ID.",
        description = "Retrieves the operation corresponding to the specified ID from the database.",
        responses = {@ApiResponse(description = "A operation has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Operation.class))),
            @ApiResponse(description = "No operation has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getOperationById(
        @Parameter(description = "The operation ID to search.", required = true) @PathVariable @NonNull final Integer id) {
        log.info("[getOperationById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the operation with the given ID from the database
            final Operation result = this.operationDao.getOperationById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getOperationById => {}] No operation found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getOperationById => {}] Operation found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperationById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Operation}s with the given {@link Intervention} ID from the database.
     *
     * @param interventionId
     *     The {@link Intervention} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/intervention/{interventionId}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Gets an operation by its intervention ID.",
        description = "Retrieves the operations corresponding to the specified intervention ID from the database.",
        responses = {@ApiResponse(description = "At least one operation has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Operation.class))),
            @ApiResponse(description = "No operation has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> getOperationsByInterventionId(@Parameter(required = true) @PathVariable @NonNull final Integer interventionId) {
        log.info("[getOperationsByInterventionId <= Calling] With intervention ID = {}", interventionId);

        try {
            // Calls the DAO to retrieve the list of operations with the given intervention ID from the database
            final List<Operation> resultList = this.operationDao.getOperationsByInterventionId(interventionId);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getOperationById => {}] No operation found for intervention ID = {}", NO_CONTENT.value(), interventionId);
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getOperationById => {}] {} operation(s) found for ID = {}", OK.value(), resultList.size(), interventionId);
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperationsByInterventionId => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Operation} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/search")
    @io.swagger.v3.oas.annotations.Operation(summary = "Searches for operations.",
        description = "Retrieves the list of operations that match the specified body from the database.",
        responses = {@ApiResponse(description = "At least one operation has been found.", responseCode = OK_CODE,
            content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Operation.class)))),
            @ApiResponse(description = "No operation has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> searchOperations(@Parameter(required = true) @RequestBody @NonNull final Operation body) {
        log.info("[searchOperations <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the operation matching the given body from the database
            final List<Operation> resultList = this.operationDao.searchOperations(body);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchOperations => {}] No operation found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[searchOperations => {}] {} operation(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[searchOperations => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Inserts a new {@link Operation} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    @io.swagger.v3.oas.annotations.Operation(summary = "Saves an operation.", description = "Inserts a new operation in the database.",
        responses = {@ApiResponse(description = "The operation has been inserted successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> saveOperation(@Parameter(required = true) @RequestBody @NonNull final Operation body) {
        log.info("[saveOperation <= Calling] With body = {}", body);

        try {
            // Calls the DAO to insert a new operation in the database
            final Integer generatedId = this.operationDao.saveOperation(body);

            // Returns the generated key with a 200 status
            log.info("[saveOperation => {}] New operation saved with ID = {}", OK.value(), generatedId);
            return ResponseEntity.ok(generatedId);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[saveOperation => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Updates a {@link Operation} with the given body in the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/update")
    @io.swagger.v3.oas.annotations.Operation(summary = "Updates an operation.", description = "Updates an existing operation in the database.",
        responses = {@ApiResponse(description = "The operation has been updated successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> updateOperation(@Parameter(required = true) @RequestBody @NonNull final Operation body) {
        log.info("[updateOperation <= Calling] With body = {}", body);

        try {
            // Calls the DAO to update the given operation in the database
            this.operationDao.updateOperation(body);

            // Returns a 200 status
            log.info("[updateOperation => {}] Operation updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateOperation => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link Operation} in the database by its ID.
     *
     * @param id
     *     The {@link Operation} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    @io.swagger.v3.oas.annotations.Operation(summary = "Deletes an operation by its ID.",
        description = "Deletes the operation corresponding to the specified ID from the database.",
        responses = {@ApiResponse(description = "The operation has been deleted successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "No operation has been found.", responseCode = NO_CONTENT_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> deleteOperationById(
        @Parameter(description = "The ID of the operation to delete.", required = true) @PathVariable @NonNull final Integer id) {
        log.info("[deleteOperationById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the operation with the given ID from the database
            if (this.operationDao.deleteOperationById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteOperationById => {}] Operation deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
            log.info("[deleteOperationById => {}] No operation found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteOperationById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
