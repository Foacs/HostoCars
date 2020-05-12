package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.OperationLine;
import fr.vulture.hostocars.entity.OperationLineEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} abstract class for the {@link OperationLineEntity} entity and {@link OperationLine} DTO.
 */
@Slf4j
@Component
public class OperationLineConverter extends Converter<OperationLineEntity, OperationLine> {

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationLineEntity toEntity(final OperationLine dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final OperationLineEntity result = new OperationLineEntity();
        result.setId(dto.getId());
        result.setOperationId(dto.getOperationId());
        result.setDescription(dto.getDescription());
        result.setDone(dto.getDone());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationLine toDto(final OperationLineEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final OperationLine result = new OperationLine();
        result.setId(entity.getId());
        result.setOperationId(entity.getOperationId());
        result.setDescription(entity.getDescription());
        result.setDone(entity.getDone());

        return result;
    }

}
