package fr.vulture.hostocars.dao;

import static fr.vulture.hostocars.utils.ControllerUtils.DEFAULT_MATCHER;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.converter.ConsumableConverter;
import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.entity.ConsumableEntity;
import fr.vulture.hostocars.repository.ConsumableRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * DAO for the {@link Consumable} DTO.
 */
@Slf4j
@Component
public class ConsumableDao {

    @NonNull
    private final ConsumableRepository consumableRepository;

    @NonNull
    private final ConsumableConverter consumableConverter;

    /**
     * Valued autowired constructor.
     *
     * @param consumableRepository
     *     The autowired {@link ConsumableRepository} component
     * @param consumableConverter
     *     The autowired {@link ConsumableConverter} component
     */
    public ConsumableDao(@NonNull final ConsumableRepository consumableRepository, @NonNull final ConsumableConverter consumableConverter) {
        this.consumableRepository = consumableRepository;
        this.consumableConverter = consumableConverter;
    }

    /**
     * Retrieves the list of all the {@link Consumable} from the database. A sorting field can also be specified.
     *
     * @param sortedBy
     *     The optional sorting clause field
     *
     * @return a list of {@link Consumable}
     */
    public List<Consumable> getConsumables(final String sortedBy) {
        log.trace("[getConsumables] With sorting field = {}", sortedBy);

        // Calls the consumable repository to retrieve the list of all the consumables from the database
        final List<ConsumableEntity> resultEntityList =
            nonNull(sortedBy) ? this.consumableRepository.findAll(Sort.by(sortedBy)) : this.consumableRepository.findAll();

        // Converts and returns the list of consumable entities to a list of DTOs
        return this.consumableConverter.toDtoList(resultEntityList);
    }

    /**
     * Retrieves the {@link Consumable} with the given ID from the database.
     *
     * @param id
     *     The {@link Consumable} ID
     *
     * @return a {@link Consumable}
     */
    public Consumable getConsumableById(@NonNull final Integer id) {
        log.trace("[getConsumableByID] With ID = {}", id);

        // Calls the consumable repository to retrieve the consumable with the given ID from the database
        final Optional<ConsumableEntity> resultEntity = this.consumableRepository.findById(id);

        // Converts and returns the consumable entity to a DTO
        return resultEntity.map(this.consumableConverter::toDto).orElse(null);
    }

    /**
     * Retrieves the list of all {@link Consumable} with the given {@link Intervention} ID from the database.
     *
     * @param interventionId
     *     The {@link Intervention} ID
     *
     * @return a {@link Consumable}
     */
    public List<Consumable> getConsumablesByInterventionId(@NonNull final Integer interventionId) {
        log.trace("[getConsumablesByInterventionId] With intervention ID = {}", interventionId);

        // Calls the consumable repository to retrieve the list of all the consumables with the given intervention ID from the database
        final List<ConsumableEntity> resultEntityList = this.consumableRepository.findAllByInterventionId(interventionId);

        // Converts and returns the list of consumable entities to a list of DTOs
        return this.consumableConverter.toDtoList(resultEntityList);
    }

    /**
     * Retrieves the list of {@link Consumable} matching the given body from the database.
     *
     * @param body
     *     The body
     *
     * @return a list of {@link Consumable}
     */
    public List<Consumable> searchConsumables(@NonNull final Consumable body) {
        log.trace("[searchConsumables] With body = {}", body);

        // Converts the given body to an entity
        final ConsumableEntity entity = this.consumableConverter.toEntity(body);

        // Calls the consumable repository to retrieve the list of consumables matching the entity from the database
        final List<ConsumableEntity> resultEntityList = this.consumableRepository.findAll(Example.of(entity, DEFAULT_MATCHER));

        // Converts and returns the list of consumable entities to a list of DTOs
        return this.consumableConverter.toDtoList(resultEntityList);
    }

    /**
     * Inserts a new {@link Consumable} in the database, generated from the given body.
     *
     * @param body
     *     The body
     *
     * @return the generated ID
     */
    public Integer saveConsumable(@NonNull final Consumable body) {
        log.trace("[saveConsumable] With body = {}", body);

        // Converts the given body to an entity
        final ConsumableEntity entity = this.consumableConverter.toEntity(body);

        // Calls the consumable repository to insert the entity in the database
        final ConsumableEntity result = this.consumableRepository.save(entity);

        // Retrieves and returns the generated ID
        return result.getId();
    }

    /**
     * Updates a {@link Consumable} with the given body in the database.
     *
     * @param body
     *     The body
     */
    public void updateConsumable(@NonNull final Consumable body) {
        log.trace("[updateConsumable] With body = {}", body);

        // Converts the given body to an entity
        final ConsumableEntity entity = this.consumableConverter.toEntity(body);

        // Calls the consumable repository to update the entity in the database
        this.consumableRepository.save(entity);
    }

    /**
     * Deletes a {@link Consumable} from the database by its ID.
     *
     * @param id
     *     The {@link Consumable} ID
     *
     * @return if the {@link Consumable} existed in the database
     */
    public boolean deleteConsumableById(@NonNull final Integer id) {
        log.trace("[deleteConsumableByID] With ID = {}", id);

        // Checks if a consumable exists in the database with the given ID
        if (this.consumableRepository.existsById(id)) {
            // Calls the repository to delete the consumable with the given ID
            this.consumableRepository.deleteById(id);

            // Returns true
            return true;
        }

        // If no consumable exists in the database with the given ID, returns false
        return false;
    }

}
