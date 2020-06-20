package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Operation;
import fr.vulture.hostocars.entity.OperationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} abstract class for the {@link OperationEntity} entity and {@link Operation} DTO.
 */
@Slf4j
@Component
public class OperationConverter extends Converter<OperationEntity, Operation> {

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationEntity toEntity(final Operation dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final OperationEntity result = new OperationEntity();
        result.setId(dto.getId());
        result.setInterventionId(dto.getInterventionId());
        result.setLabel(dto.getLabel());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Operation toDto(final OperationEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final Operation result = new Operation();
        result.setId(entity.getId());
        result.setInterventionId(entity.getInterventionId());
        result.setLabel(entity.getLabel());
        return result;
    }

}
