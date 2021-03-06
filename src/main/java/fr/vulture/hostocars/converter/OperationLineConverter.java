package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.OperationLine;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for operation lines.
 */
@Component
public class OperationLineConverter implements Converter<fr.vulture.hostocars.entity.OperationLine, OperationLine> {

    /**
     * {@inheritDoc}
     */
    @Override
    public fr.vulture.hostocars.entity.OperationLine toEntity(final OperationLine dto) {
        if (isNull(dto)) {
            return null;
        }

        final fr.vulture.hostocars.entity.OperationLine entity = new fr.vulture.hostocars.entity.OperationLine();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setDone(dto.getDone());
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationLine toDto(final fr.vulture.hostocars.entity.OperationLine entity) {
        if (isNull(entity)) {
            return null;
        }

        final OperationLine dto = new OperationLine();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setDone(entity.getDone());
        return dto;
    }

}
