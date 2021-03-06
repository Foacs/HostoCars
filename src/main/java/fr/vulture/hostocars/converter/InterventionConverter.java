package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Intervention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for interventions.
 */
@Component
public class InterventionConverter implements Converter<fr.vulture.hostocars.entity.Intervention, Intervention> {

    private final OperationConverter operationConverter;

    /**
     * Valued autowired constructor.
     *
     * @param operationConverter
     *     The autowired {@link OperationConverter} component
     */
    @Autowired
    public InterventionConverter(final OperationConverter operationConverter) {
        this.operationConverter = operationConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public fr.vulture.hostocars.entity.Intervention toEntity(final Intervention dto) {
        if (isNull(dto)) {
            return null;
        }

        final fr.vulture.hostocars.entity.Intervention entity = new fr.vulture.hostocars.entity.Intervention();
        entity.setId(dto.getId());
        entity.setYear(dto.getYear());
        entity.setNumber(dto.getNumber());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setMileage(dto.getMileage());
        entity.setEstimatedTime(dto.getEstimatedTime());
        entity.setRealTime(dto.getRealTime());
        entity.setAmount(dto.getAmount());
        entity.setPaidAmount(dto.getPaidAmount());
        entity.setComments(dto.getComments());
        entity.setOperations(this.operationConverter.toEntities(dto.getOperations()));
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intervention toDto(final fr.vulture.hostocars.entity.Intervention entity) {
        if (isNull(entity)) {
            return null;
        }

        final Intervention dto = new Intervention();
        dto.setId(entity.getId());
        dto.setYear(entity.getYear());
        dto.setNumber(entity.getNumber());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setMileage(entity.getMileage());
        dto.setEstimatedTime(entity.getEstimatedTime());
        dto.setRealTime(entity.getRealTime());
        dto.setAmount(entity.getAmount());
        dto.setPaidAmount(entity.getPaidAmount());
        dto.setComments(entity.getComments());
        dto.setOperations(this.operationConverter.toDtos(entity.getOperations()));
        return dto;
    }

}
