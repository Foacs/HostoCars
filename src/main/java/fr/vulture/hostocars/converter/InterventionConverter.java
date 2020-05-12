package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.entity.InterventionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} abstract class for the {@link InterventionEntity} entity and {@link Intervention} DTO.
 */
@Slf4j
@Component
public class InterventionConverter extends Converter<InterventionEntity, Intervention> {

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionEntity toEntity(final Intervention dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final InterventionEntity result = new InterventionEntity();
        result.setId(dto.getId());
        result.setCarId(dto.getCarId());
        result.setCreationYear(dto.getCreationYear());
        result.setNumber(dto.getNumber());
        result.setStatus(dto.getStatus());
        result.setDescription(dto.getDescription());
        result.setMileage(dto.getMileage());
        result.setEstimatedTime(dto.getEstimatedTime());
        result.setRealTime(dto.getRealTime());
        result.setAmount(dto.getAmount());
        result.setPaidAmount(dto.getPaidAmount());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intervention toDto(final InterventionEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final Intervention result = new Intervention();
        result.setId(entity.getId());
        result.setCarId(entity.getCarId());
        result.setCreationYear(entity.getCreationYear());
        result.setNumber(entity.getNumber());
        result.setStatus(entity.getStatus());
        result.setDescription(entity.getDescription());
        result.setMileage(entity.getMileage());
        result.setEstimatedTime(entity.getEstimatedTime());
        result.setRealTime(entity.getRealTime());
        result.setAmount(entity.getAmount());
        result.setPaidAmount(entity.getPaidAmount());
        return result;
    }

}
