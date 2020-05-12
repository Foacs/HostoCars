package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.converter.OperationConverter;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationEntity;
import fr.vulture.hostocars.repository.OperationRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * DAO for the {@link Operation} DTO.
 */
@Slf4j
@Component
public class OperationDao {

    @NonNull
    private final OperationLineDao operationLineDao;

    @NonNull
    private final OperationRepository operationRepository;

    @NonNull
    private final OperationConverter operationConverter;

    /**
     * Valued autowired constructor.
     *
     * @param operationLineDao
     *     The autowired {@link OperationLineDao} component
     * @param operationRepository
     *     The autowired {@link OperationRepository} component
     * @param operationConverter
     *     The autowired {@link OperationConverter} component
     */
    public OperationDao(@NonNull final OperationLineDao operationLineDao, @NonNull final OperationRepository operationRepository,
        @NonNull final OperationConverter operationConverter) {
        this.operationLineDao = operationLineDao;
        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
    }

    /**
     * Retrieves the list of all the {@link Operation} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return a list of {@link Operation}
     */
    public List<Operation> getOperations(final String sortedBy) {
        log.trace("[getOperations] With sorting field = {}", sortedBy);

        // Calls the operation repository to retrieve the list of all the operations from the database
        final List<OperationEntity> resultEntityList =
            nonNull(sortedBy) ? this.operationRepository.findAll(Sort.by(sortedBy)) : this.operationRepository.findAll();

        // Converts the list of operation entities to a list of DTOs
        final List<Operation> resultList = this.operationConverter.toDtoList(resultEntityList);

        // Calls the operation line DAO to retrieve the operation lines of the loaded operations
        for (final Operation operation : resultList) {
            operation.setOperationLineList(this.operationLineDao.getOperationLinesByOperationId(operation.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Retrieves the {@link Operation} with the given ID from the database.
     *
     * @param id
     *     The {@link Operation} ID
     *
     * @return a {@link Operation}
     */
    public Operation getOperationById(@NonNull final Integer id) {
        log.trace("[getOperationByID] With ID = {}", id);

        // Calls the operation repository to retrieve the operation with the given ID from the database
        final Optional<OperationEntity> resultEntity = this.operationRepository.findById(id);

        // Converts the operation entity to a DTO
        final Operation result = resultEntity.map(this.operationConverter::toDto).orElse(null);

        // Calls the operation line DAO to retrieve the operation lines of the loaded operation
        result.setOperationLineList(this.operationLineDao.getOperationLinesByOperationId(result.getId()));

        // Returns the DTO
        return result;
    }

    /**
     * Retrieves the list of all {@link Operation} with the given {@link Intervention} ID from the database.
     *
     * @param interventionId
     *     The {@link Intervention} ID
     *
     * @return a {@link Operation}
     */
    public List<Operation> getOperationsByInterventionId(@NonNull final Integer interventionId) {
        log.trace("[getOperationsByInterventionId] With intervention ID = {}", interventionId);

        // Calls the operation repository to retrieve the list of all the operations with the given intervention ID from the database
        final List<OperationEntity> resultEntityList = this.operationRepository.findAllByInterventionId(interventionId);

        // Converts the list of operation entities to a list of DTOs
        final List<Operation> resultList = this.operationConverter.toDtoList(resultEntityList);

        // Calls the operation line DAO to retrieve the operation lines of the loaded operations
        for (final Operation operation : resultList) {
            operation.setOperationLineList(this.operationLineDao.getOperationLinesByOperationId(operation.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Retrieves the list of {@link Operation} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return a list of {@link Operation}
     */
    public List<Operation> searchOperations(@NonNull final Operation body) {
        log.trace("[searchOperations] With body = {}", body);

        // Converts the given body to an entity
        final OperationEntity entity = this.operationConverter.toEntity(body);

        // Calls the operation repository to retrieve the list of operations matching the entity from the database
        final List<OperationEntity> resultEntityList = this.operationRepository.findAll(Example.of(entity, DEFAULT_MATCHER));

        // Converts the list of operation entities to a list of DTOs
        final List<Operation> resultList = this.operationConverter.toDtoList(resultEntityList);

        // Calls the operation line DAO to retrieve the operation lines of the loaded operations
        for (final Operation operation : resultList) {
            operation.setOperationLineList(this.operationLineDao.getOperationLinesByOperationId(operation.getId()));
        }

        // Returns the list of DTOs
        return resultList;
    }

    /**
     * Inserts a new {@link Operation} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return the generated ID
     */
    public Integer saveOperation(@NonNull final Operation body) {
        log.trace("[saveOperation] With body = {}", body);

        // Calls the operation line DAO to save the list of operation lines of the given body
        for (final OperationLine operationLine : body.getOperationLineList()) {
            this.operationLineDao.saveOperationLine(operationLine);
        }

        // Converts the given body to an entity
        final OperationEntity entity = this.operationConverter.toEntity(body);

        // Calls the operation repository to insert the entity in the database
        final OperationEntity result = this.operationRepository.save(entity);

        // Retrieves and returns the generated ID
        return result.getId();
    }

    /**
     * Updates a {@link Operation} with the given body in the database.
     *
     * @param body
     *     The body
     */
    public void updateOperation(@NonNull final Operation body) {
        log.trace("[updateOperation] With body = {}", body);

        // Calls the operation line DAO to update the list of operation lines of the given body
        for (final OperationLine operationLine : body.getOperationLineList()) {
            this.operationLineDao.updateOperationLine(operationLine);
        }

        // Converts the given body to an entity
        final OperationEntity entity = this.operationConverter.toEntity(body);

        // Calls the operation repository to update the entity in the database
        this.operationRepository.save(entity);
    }

    /**
     * Deletes a {@link Operation} from the database by its ID.
     *
     * @param id
     *     The {@link Operation} ID
     *
     * @return if the {@link Operation} existed in the database
     */
    public boolean deleteOperationById(@NonNull final Integer id) {
        log.trace("[deleteOperationByID] With ID = {}", id);

        // Checks if an operation exists in the database with the given ID
        if (this.operationRepository.existsById(id)) {
            // Calls the operation line DAO to load the list of operation lines with the given operation ID, then delete them
            for (final OperationLine operationLine : this.operationLineDao.getOperationLinesByOperationId(id)) {
                this.operationLineDao.deleteOperationLineById(operationLine.getId());
            }

            // Calls the repository to delete the operation with the given ID
            this.operationRepository.deleteById(id);

            // Returns true
            return true;
        }

        // If no operation exists in the database with the given ID, returns false
        return false;
    }

}
