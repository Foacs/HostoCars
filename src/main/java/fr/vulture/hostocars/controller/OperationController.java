package fr.vulture.hostocars.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.OperationDao;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.pojo.Response;
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
@RequestMapping("/operations")
@CrossOrigin(origins = "*")
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
    public OperationController(@NonNull final OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * Retrieves the list of all the {@link Operation} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public ResponseEntity<?> getOperations(@RequestParam(required = false) final String sortedBy) {
        log.info("[getOperations <= Calling] With sorting field = {}", sortedBy);

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
    public ResponseEntity<?> getOperationById(@PathVariable @NonNull final Integer id) {
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
    public ResponseEntity<?> getOperationsByInterventionId(@PathVariable @NonNull final Integer interventionId) {
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
    @GetMapping("/search")
    public ResponseEntity<?> searchOperations(@RequestBody @NonNull final Operation body) {
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
    public ResponseEntity<?> saveOperation(@RequestBody @NonNull final Operation body) {
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
    public ResponseEntity<?> updateOperation(@RequestBody @NonNull final Operation body) {
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
    public ResponseEntity<?> deleteOperationById(@PathVariable @NonNull final Integer id) {
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
