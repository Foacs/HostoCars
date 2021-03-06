package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for operations.
 */
@Component
public class OperationConverter implements Converter<fr.vulture.hostocars.entity.Operation, Operation> {

    private final OperationLineConverter operationLineConverter;

    /**
     * Valued autowired constructor.
     *
     * @param operationLineConverter
     *     The autowired {@link OperationLineConverter} component
     */
    @Autowired
    public OperationConverter(final OperationLineConverter operationLineConverter) {
        this.operationLineConverter = operationLineConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public fr.vulture.hostocars.entity.Operation toEntity(final Operation dto) {
        if (isNull(dto)) {
            return null;
        }

        final fr.vulture.hostocars.entity.Operation entity = new fr.vulture.hostocars.entity.Operation();
        entity.setId(dto.getId());
        entity.setLabel(dto.getLabel());
        entity.setOperationLines(this.operationLineConverter.toEntities(dto.getOperationLines()));
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Operation toDto(final fr.vulture.hostocars.entity.Operation entity) {
        if (isNull(entity)) {
            return null;
        }

        final Operation dto = new Operation();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());
        dto.setOperationLines(this.operationLineConverter.toDtos(entity.getOperationLines()));
        return dto;
    }

}
