package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.converter.OperationLineConverter;
import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationLineEntity;
import fr.vulture.hostocars.repository.OperationLineRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * DAO for the {@link OperationLine} DTO.
 */
@Slf4j
@Component
public class OperationLineDao {

    @NonNull
    private final OperationLineRepository operationLineRepository;

    @NonNull
    private final OperationLineConverter operationLineConverter;

    /**
     * Valued autowired constructor.
     *
     * @param operationLineRepository
     *     The autowired {@link OperationLineRepository} component
     * @param operationLineConverter
     *     The autowired {@link OperationLineConverter} component
     */
    public OperationLineDao(@NonNull final OperationLineRepository operationLineRepository,
        @NonNull final OperationLineConverter operationLineConverter) {
        this.operationLineRepository = operationLineRepository;
        this.operationLineConverter = operationLineConverter;
    }

    /**
     * Retrieves the list of all the {@link OperationLine} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return a list of {@link OperationLine}
     */
    public List<OperationLine> getOperationLines(final String sortedBy) {
        log.trace("[getOperationLines] With sorting field = {}", sortedBy);

        // Calls the operation line repository to retrieve the list of all the operation lines from the database
        final List<OperationLineEntity> resultEntityList =
            nonNull(sortedBy) ? this.operationLineRepository.findAll(Sort.by(sortedBy)) : this.operationLineRepository.findAll();

        // Converts and returns the list of operation line entities to a list of DTOs
        return this.operationLineConverter.toDtoList(resultEntityList);
    }

    /**
     * Retrieves the {@link OperationLine} with the given ID from the database.
     *
     * @param id
     *     The {@link OperationLine} ID
     *
     * @return a {@link OperationLine}
     */
    public OperationLine getOperationLineById(@NonNull final Integer id) {
        log.trace("[getOperationLineByID] With ID = {}", id);

        // Calls the operation line repository to retrieve the operation line with the given ID from the database
        final Optional<OperationLineEntity> resultEntity = this.operationLineRepository.findById(id);

        // Converts and returns the operation line entity to a DTO
        return resultEntity.map(this.operationLineConverter::toDto).orElse(null);
    }

    /**
     * Retrieves the list of all {@link OperationLine} with the given {@link Operation} ID from the database.
     *
     * @param operationId
     *     The {@link Operation} ID
     *
     * @return a {@link OperationLine}
     */
    public List<OperationLine> getOperationLinesByOperationId(@NonNull final Integer operationId) {
        log.trace("[getOperationLinesByOperationId] With operation ID = {}", operationId);

        // Calls the operation line repository to retrieve the list of all the operation lines with the given operation ID from the database
        final List<OperationLineEntity> resultEntityList = this.operationLineRepository.findAllByOperationId(operationId);

        // Converts and returns the list of operation line entities to a list of DTOs
        return this.operationLineConverter.toDtoList(resultEntityList);
    }

    /**
     * Retrieves the list of {@link OperationLine} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return a list of {@link OperationLine}
     */
    public List<OperationLine> searchOperationLines(@NonNull final OperationLine body) {
        log.trace("[searchOperationLines] With body = {}", body);

        // Converts the given body to an entity
        final OperationLineEntity entity = this.operationLineConverter.toEntity(body);

        // Calls the operation line repository to retrieve the list of operation lines matching the entity from the database
        final List<OperationLineEntity> resultEntityList = this.operationLineRepository.findAll(Example.of(entity, DEFAULT_MATCHER));

        // Converts and returns the list of operation line entities to a list of DTOs
        return this.operationLineConverter.toDtoList(resultEntityList);
    }

    /**
     * Inserts a new {@link OperationLine} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return the generated ID
     */
    public Integer saveOperationLine(@NonNull final OperationLine body) {
        log.trace("[saveOperationLine] With body = {}", body);

        // Converts the given body to an entity
        final OperationLineEntity entity = this.operationLineConverter.toEntity(body);

        // Calls the operation line repository to insert the entity in the database
        final OperationLineEntity result = this.operationLineRepository.save(entity);

        // Retrieves and returns the generated ID
        return result.getId();
    }

    /**
     * Updates a {@link OperationLine} with the given body in the database.
     *
     * @param body
     *     The body
     */
    public void updateOperationLine(@NonNull final OperationLine body) {
        log.trace("[updateOperationLine] With body = {}", body);

        // Converts the given body to an entity
        final OperationLineEntity entity = this.operationLineConverter.toEntity(body);

        // Calls the operation line repository to update the entity in the database
        this.operationLineRepository.save(entity);
    }

    /**
     * Deletes a {@link OperationLine} from the database by its ID.
     *
     * @param id
     *     The {@link OperationLine} ID
     *
     * @return if the {@link OperationLine} existed in the database
     */
    public boolean deleteOperationLineById(@NonNull final Integer id) {
        log.trace("[deleteOperationLineByID] With ID = {}", id);

        // Checks if an operation line exists in the database with the given ID
        if (this.operationLineRepository.existsById(id)) {
            // Calls the repository to delete the operation line with the given ID
            this.operationLineRepository.deleteById(id);

            // Returns true
            return true;
        }

        // If no operation line exists in the database with the given ID, returns false
        return false;
    }

}
