package fr.vulture.hostocars.controller;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.dao.InterventionDao;
import fr.vulture.hostocars.dto.Car;
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
 * REST controller for interventions.
 */
@Slf4j
@Transactional
@RestController
@RequestMapping("/interventions")
@CrossOrigin(origins = "*")
public class InterventionController {

    @NonNull
    private final InterventionDao interventionDao;

    /**
     * Valued autowired constructor.
     *
     * @param interventionDao
     *     The autowired {@link InterventionDao} component
     */
    @Autowired
    public InterventionController(@NonNull final InterventionDao interventionDao) {
        this.interventionDao = interventionDao;
    }

    /**
     * Retrieves the list of all the {@link Intervention} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return an HTTP response
     */
    @GetMapping("/all")
    public ResponseEntity<?> getInterventions(@RequestParam(required = false) final String sortedBy) {
        log.info("[getInterventions <= Calling] With sorting field = {}", sortedBy);

        try {
            // Calls the DAO to retrieve the list of all the interventions from the database
            final List<Intervention> resultList = this.interventionDao.getInterventions(sortedBy);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getInterventions => {}] No intervention found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getInterventions => {}] {} intervention(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getInterventions => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the {@link Intervention} with the given ID from the database.
     *
     * @param id
     *     The {@link Intervention} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getInterventionById(@PathVariable @NonNull final Integer id) {
        log.info("[getInterventionById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to retrieve the intervention with the given ID from the database
            final Intervention result = this.interventionDao.getInterventionById(id);

            // If no result has been found, returns a 204 status
            if (isNull(result)) {
                log.info("[getInterventionById => {}] No intervention found for ID = {}", NO_CONTENT.value(), id);
                return ResponseEntity.noContent().build();
            }

            // If a result has been found, returns it with a 200 status
            log.info("[getInterventionById => {}] Intervention found for ID = {}", OK.value(), id);
            return ResponseEntity.ok(result);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getInterventionById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Intervention}s with the given {@link Car} ID from the database.
     *
     * @param carId
     *     The {@link Car} ID
     *
     * @return an HTTP response
     */
    @GetMapping("/car/{carId}")
    public ResponseEntity<?> getInterventionsByCarId(@PathVariable @NonNull final Integer carId) {
        log.info("[getInterventionsByCarId <= Calling] With car ID = {}", carId);

        try {
            // Calls the DAO to retrieve the list of interventions with the given car ID from the database
            final List<Intervention> resultList = this.interventionDao.getInterventionsByCarId(carId);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[getInterventionById => {}] No intervention found for car ID = {}", NO_CONTENT.value(), carId);
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[getInterventionById => {}] {} intervention(s) found for ID = {}", OK.value(), resultList.size(), carId);
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[getInterventionsByCarId => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Retrieves the list of {@link Intervention} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchInterventions(@RequestBody @NonNull final Intervention body) {
        log.info("[searchInterventions <= Calling] With body = {}", body);

        try {
            // Calls the DAO to retrieve the intervention matching the given body from the database
            final List<Intervention> resultList = this.interventionDao.searchInterventions(body);

            // If no result has been found, returns a 204 status
            if (resultList.isEmpty()) {
                log.info("[searchInterventions => {}] No intervention found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // If at least one result has been found, returns the list with a 200 status
            log.info("[searchInterventions => {}] {} intervention(s) found", OK.value(), resultList.size());
            return ResponseEntity.ok(resultList);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[searchInterventions => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Inserts a new {@link Intervention} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveIntervention(@RequestBody @NonNull final Intervention body) {
        log.info("[saveIntervention <= Calling] With body = {}", body);

        try {
            // Calls the DAO to insert a new intervention in the database
            final Integer generatedId = this.interventionDao.saveIntervention(body);

            // Returns the generated key with a 200 status
            log.info("[saveIntervention => {}] New intervention saved with ID = {}", OK.value(), generatedId);
            return ResponseEntity.ok(generatedId);
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[saveIntervention => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Updates a {@link Intervention} with the given body in the database.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateIntervention(@RequestBody @NonNull final Intervention body) {
        log.info("[updateIntervention <= Calling] With body = {}", body);

        try {
            // Calls the DAO to update the given intervention in the database
            this.interventionDao.updateIntervention(body);

            // Returns a 200 status
            log.info("[updateIntervention => {}] Intervention updated", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[updateIntervention => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

    /**
     * Deletes a {@link Intervention} in the database by its ID.
     *
     * @param id
     *     The {@link Intervention} ID
     *
     * @return an HTTP response
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteInterventionById(@PathVariable @NonNull final Integer id) {
        log.info("[deleteInterventionById <= Calling] With ID = {}", id);

        try {
            // Calls the DAO to delete the intervention with the given ID from the database
            if (this.interventionDao.deleteInterventionById(id)) {
                // If the ID existed, returns a 200 status
                log.info("[deleteInterventionById => {}] Intervention deleted", OK.value());
                return ResponseEntity.ok().build();
            }

            // If the ID didn't exist, returns a 204 status
            log.info("[deleteInterventionById => {}] No intervention found to delete", NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final RuntimeException e) {
            log.error("[deleteInterventionById => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}