package fr.vulture.hostocars.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.ConsumableDao;
import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.dto.Intervention;
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
 * REST controller for consumables.
 */
@Slf4j
@Transactional
@RestController
@RequestMapping("/consumables")
@CrossOrigin(origins = "*")
public class ConsumableController {

    @NonNull
    private final ConsumableDao consumableDao;

    /**
     * Valued autowired constructor.
     *
     * @param consumableDao
     *     The autowired {@link ConsumableDao} component
     */
    @Autowired
    public ConsumableController(@NonNull final ConsumableDao consumableDao) {
        this.consumableDao = consumableDao;
    }

    /**
     * Retrieves the list of all the {@link Consumable} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public ResponseEntity<?> getConsumables(@RequestParam(required = false) final String sortedBy) {
        log.info("[getConsumables <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the DAO to retrieve the list of all the consumables from the database
            final List<Consumable> resultList = this.consumableDao.getConsumables(sortedBy);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getConsumables => {}] No consumable found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getConsumables => {}] {} consumable(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getConsumables => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the {@link Consumable} with the given ID from the database.
     *
     * @param id
     *     The {@link Consumable} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getConsumableById(@PathVariable @NonNull final Integer id) {
        log.info("[getConsumableById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the consumable with the given ID from the database
            final Consumable result = this.consumableDao.getConsumableById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getConsumableById => {}] No consumable found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getConsumableById => {}] Consumable found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getConsumableById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Consumable}s with the given {@link Intervention} ID from the database.
     *
     * @param interventionId
     *     The {@link Intervention} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/intervention/{interventionId}")
    public ResponseEntity<?> getConsumablesByInterventionId(@PathVariable @NonNull final Integer interventionId) {
        log.info("[getConsumablesByInterventionId <= Calling] With intervention ID = {}", interventionId);

        try {
            // Calls the DAO to retrieve the list of consumables with the given intervention ID from the database
            final List<Consumable> resultList = this.consumableDao.getConsumablesByInterventionId(interventionId);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getConsumableById => {}] No consumable found for intervention ID = {}", NO_CONTENT.value(), interventionId);
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getConsumableById => {}] {} consumable(s) found for ID = {}", OK.value(), resultList.size(), interventionId);
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getConsumablesByInterventionId => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Consumable} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchConsumables(@RequestBody @NonNull final Consumable body) {
        log.info("[searchConsumables <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the consumable matching the given body from the database
            final List<Consumable> resultList = this.consumableDao.searchConsumables(body);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchConsumables => {}] No consumable found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[searchConsumables => {}] {} consumable(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[searchConsumables => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Inserts a new {@link Consumable} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveConsumable(@RequestBody @NonNull final Consumable body) {
        log.info("[saveConsumable <= Calling] With body = {}", body);

        try {
            // Calls the DAO to insert a new consumable in the database
            final Integer generatedId = this.consumableDao.saveConsumable(body);

            // Returns the generated key with a 200 status
            log.info("[saveConsumable => {}] New consumable saved with ID = {}", OK.value(), generatedId);
            return ResponseEntity.ok(generatedId);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[saveConsumable => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Updates a {@link Consumable} with the given body in the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateConsumable(@RequestBody @NonNull final Consumable body) {
        log.info("[updateConsumable <= Calling] With body = {}", body);

        try {
            // Calls the DAO to update the given consumable in the database
            this.consumableDao.updateConsumable(body);

            // Returns a 200 status
            log.info("[updateConsumable => {}] Consumable updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateConsumable => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link Consumable} in the database by its ID.
     *
     * @param id
     *     The {@link Consumable} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteConsumableById(@PathVariable @NonNull final Integer id) {
        log.info("[deleteConsumableById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the consumable with the given ID from the database
            if (this.consumableDao.deleteConsumableById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteConsumableById => {}] Consumable deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
            log.info("[deleteConsumableById => {}] No consumable found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteConsumableById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
