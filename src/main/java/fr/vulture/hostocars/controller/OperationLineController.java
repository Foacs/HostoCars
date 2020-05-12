package fr.vulture.hostocars.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.OperationLineDao;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.dto.OperationLine;
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
 * REST controller for operation lines.
 */
@Slf4j
@Transactional
@RestController
@RequestMapping("/operationLines")
@CrossOrigin(origins = "*")
public class OperationLineController {

    @NonNull
    private final OperationLineDao operationLineDao;

    /**
     * Valued autowired constructor.
     *
     * @param operationLineDao
     *     The autowired {@link OperationLineDao} component
     */
    @Autowired
    public OperationLineController(@NonNull final OperationLineDao operationLineDao) {
        this.operationLineDao = operationLineDao;
    }

    /**
     * Retrieves the list of all the {@link OperationLine} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public ResponseEntity<?> getOperationLines(@RequestParam(required = false) final String sortedBy) {
        log.info("[getOperationLines <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the DAO to retrieve the list of all the operation lines from the database
            final List<OperationLine> resultList = this.operationLineDao.getOperationLines(sortedBy);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getOperationLines => {}] No operation line found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getOperationLines => {}] {} operation line(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperationLines => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the {@link OperationLine} with the given ID from the database.
     *
     * @param id
     *     The {@link OperationLine} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOperationLineById(@PathVariable @NonNull final Integer id) {
        log.info("[getOperationLineById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the operation line with the given ID from the database
            final OperationLine result = this.operationLineDao.getOperationLineById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getOperationLineById => {}] No operation line found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getOperationLineById => {}] OperationLine found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperationLineById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link OperationLine}s with the given {@link Operation} ID from the database.
     *
     * @param operationId
     *     The {@link Operation} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/operation/{operationId}")
    public ResponseEntity<?> getOperationLinesByOperationId(@PathVariable @NonNull final Integer operationId) {
        log.info("[getOperationLinesByOperationId <= Calling] With operation ID = {}", operationId);

        try {
            // Calls the DAO to retrieve the list of operation lines with the given operation ID from the database
            final List<OperationLine> resultList = this.operationLineDao.getOperationLinesByOperationId(operationId);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getOperationLineById => {}] No operation line found for operation ID = {}", NO_CONTENT.value(), operationId);
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getOperationLineById => {}] {} operation line(s) found for ID = {}", OK.value(), resultList.size(), operationId);
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getOperationLinesByOperationId => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link OperationLine} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchOperationLines(@RequestBody @NonNull final OperationLine body) {
        log.info("[searchOperationLines <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the operation line matching the given body from the database
            final List<OperationLine> resultList = this.operationLineDao.searchOperationLines(body);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchOperationLines => {}] No operation line found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[searchOperationLines => {}] {} operation line(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[searchOperationLines => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Inserts a new {@link OperationLine} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveOperationLine(@RequestBody @NonNull final OperationLine body) {
        log.info("[saveOperationLine <= Calling] With body = {}", body);

        try {
            // Calls the DAO to insert a new operation line in the database
            final Integer generatedId = this.operationLineDao.saveOperationLine(body);

            // Returns the generated key with a 200 status
            log.info("[saveOperationLine => {}] New operation line saved with ID = {}", OK.value(), generatedId);
            return ResponseEntity.ok(generatedId);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[saveOperationLine => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Updates a {@link OperationLine} with the given body in the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateOperationLine(@RequestBody @NonNull final OperationLine body) {
        log.info("[updateOperationLine <= Calling] With body = {}", body);

        try {
            // Calls the DAO to update the given operation line in the database
            this.operationLineDao.updateOperationLine(body);

            // Returns a 200 status
            log.info("[updateOperationLine => {}] OperationLine updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateOperationLine => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link OperationLine} in the database by its ID.
     *
     * @param id
     *     The {@link OperationLine} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteOperationLineById(@PathVariable @NonNull final Integer id) {
        log.info("[deleteOperationLineById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the operation line with the given ID from the database
            if (this.operationLineDao.deleteOperationLineById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteOperationLineById => {}] OperationLine deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
            log.info("[deleteOperationLineById => {}] No operation line found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteOperationLineById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
