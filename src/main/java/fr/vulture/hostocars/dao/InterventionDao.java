package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.converter.InterventionConverter;
import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.entity.InterventionEntity;
import fr.vulture.hostocars.repository.InterventionRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * DAO for the {@link Intervention} DTO.
 */
@Slf4j
@Component
public class InterventionDao {

    @NonNull
    private final OperationDao operationDao;

    @NonNull
    private final InterventionRepository interventionRepository;

    @NonNull
    private final InterventionConverter interventionConverter;

    /**
     * Valued autowired constructor.
     *
     * @param operationDao
     *     The autowired {@link OperationDao} component
     * @param interventionRepository
     *     The autowired {@link InterventionRepository} component
     * @param interventionConverter
     *     The autowired {@link InterventionConverter} component
     */
    public InterventionDao(@NonNull final OperationDao operationDao, @NonNull final InterventionRepository interventionRepository,
        @NonNull final InterventionConverter interventionConverter) {
        this.operationDao = operationDao;
        this.interventionRepository = interventionRepository;
        this.interventionConverter = interventionConverter;
    }

    /**
     * Retrieves the list of all the {@link Intervention} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return a list of {@link Intervention}
     */
    public List<Intervention> getInterventions(final String sortedBy) {
        log.trace("[getInterventions] With sorting field = {}", sortedBy);

        // Calls the intervention repository to retrieve the list of all the interventions from the database
        final List<InterventionEntity> resultEntityList =
            nonNull(sortedBy) ? this.interventionRepository.findAll(Sort.by(sortedBy)) : this.interventionRepository.findAll();

        // Converts the list of intervention entities to a list of DTOs
        final List<Intervention> resultList = this.interventionConverter.toDtoList(resultEntityList);

        // Calls the operation DAO to retrieve the operations of the loaded interventions
        for (final Intervention intervention : resultList) {
            intervention.setOperationList(this.operationDao.getOperationsByInterventionId(intervention.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Retrieves the {@link Intervention} with the given ID from the database.
     *
     * @param id
     *     The {@link Intervention} ID
     *
     * @return a {@link Intervention}
     */
    public Intervention getInterventionById(@NonNull final Integer id) {
        log.trace("[getInterventionByID] With ID = {}", id);

        // Calls the intervention repository to retrieve the intervention with the given ID from the database
        final Optional<InterventionEntity> resultEntity = this.interventionRepository.findById(id);

        // Converts the intervention entity to a DTO
        final Intervention result = resultEntity.map(this.interventionConverter::toDto).orElse(null);

        // Calls the operation DAO to retrieve the operations of the loaded intervention
        result.setOperationList(this.operationDao.getOperationsByInterventionId(result.getId()));

        // Returns the DTO
        return result;
    }

    /**
     * Retrieves the list of all {@link Intervention} with the given {@link Car} ID from the database.
     *
     * @param carId
     *     The {@link Car} ID
     *
     * @return a {@link Intervention}
     */
    public List<Intervention> getInterventionsByCarId(@NonNull final Integer carId) {
        log.trace("[getInterventionsByCarId] With car ID = {}", carId);

        // Calls the intervention repository to retrieve the list of all the interventions with the given car ID from the database
        final List<InterventionEntity> resultEntityList = this.interventionRepository.findAllByCarId(carId);

        // Converts the list of intervention entities to a list of DTOs
        final List<Intervention> resultList = this.interventionConverter.toDtoList(resultEntityList);

        // Calls the operation DAO to retrieve the operations of the loaded interventions
        for (final Intervention intervention : resultList) {
            intervention.setOperationList(this.operationDao.getOperationsByInterventionId(intervention.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Retrieves the list of {@link Intervention} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return a list of {@link Intervention}
     */
    public List<Intervention> searchInterventions(@NonNull final Intervention body) {
        log.trace("[searchInterventions] With body = {}", body);

        // Converts the given body to an entity
        final InterventionEntity entity = this.interventionConverter.toEntity(body);

        // Calls the intervention repository to retrieve the list of interventions matching the entity from the database
        final List<InterventionEntity> resultEntityList =
            this.interventionRepository.findAll(Example.of(entity, DEFAULT_MATCHER.withIgnorePaths("certificate", "picture")));

        // Converts the list of intervention entities to a list of DTOs
        final List<Intervention> resultList = this.interventionConverter.toDtoList(resultEntityList);

        // Calls the operation DAO to retrieve the operations of the loaded interventions
        for (final Intervention intervention : resultList) {
            intervention.setOperationList(this.operationDao.getOperationsByInterventionId(intervention.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Inserts a new {@link Intervention} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return the generated ID
     */
    public Integer saveIntervention(@NonNull final Intervention body) {
        log.trace("[saveIntervention] With body = {}", body);

        // Calls the operation DAO to save the list of operations of the given body
        for (final Operation operation : body.getOperationList()) {
            this.operationDao.saveOperation(operation);
        }

        // Converts the given body to an entity
        final InterventionEntity entity = this.interventionConverter.toEntity(body);

        // Calls the intervention repository to insert the entity in the database
        final InterventionEntity result = this.interventionRepository.save(entity);

        // Retrieves and returns the generated ID
        return result.getId();
    }

    /**
     * Updates a {@link Intervention} with the given body in the database.
     *
     * @param body
     *     The body
     */
    public void updateIntervention(@NonNull final Intervention body) {
        log.trace("[updateIntervention] With body = {}", body);

        // Calls the operation DAO to update the list of operations of the given body
        for (final Operation operation : body.getOperationList()) {
            this.operationDao.updateOperation(operation);
        }

        // Converts the given body to an entity
        final InterventionEntity entity = this.interventionConverter.toEntity(body);

        // Calls the intervention repository to update the entity in the database
        this.interventionRepository.save(entity);
    }

    /**
     * Deletes a {@link Intervention} from the database by its ID.
     *
     * @param id
     *     The {@link Intervention} ID
     *
     * @return if the {@link Intervention} existed in the database
     */
    public boolean deleteInterventionById(@NonNull final Integer id) {
        log.trace("[deleteInterventionByID] With ID = {}", id);

        // Checks if an intervention exists in the database with the given ID
        if (this.interventionRepository.existsById(id)) {
            // Calls the operation DAO to load the list of operations with the given intervention ID, then delete them
            for (final Operation operation : this.operationDao.getOperationsByInterventionId(id)) {
                this.operationDao.deleteOperationById(operation.getId());
            }

            // Calls the repository to delete the intervention with the given ID
            this.interventionRepository.deleteById(id);

            // Returns true
            return true;
        }

        // If no intervention exists in the database with the given ID, returns false
        return false;
    }

}
