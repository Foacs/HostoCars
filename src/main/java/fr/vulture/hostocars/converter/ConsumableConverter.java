package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.entity.ConsumableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for the {@link ConsumableEntity} entity and {@link Consumable} DTO.
 */
@Slf4j
@Component
public class ConsumableConverter extends Converter<ConsumableEntity, Consumable> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsumableEntity toEntity(final Consumable dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final ConsumableEntity result = new ConsumableEntity();
        result.setId(dto.getId());
        result.setInterventionId(dto.getInterventionId());
        result.setDenomination(dto.getDenomination());
        result.setQuantity(dto.getQuantity());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Consumable toDto(final ConsumableEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final Consumable result = new Consumable();
        result.setId(entity.getId());
        result.setInterventionId(entity.getInterventionId());
        result.setDenomination(entity.getDenomination());
        result.setQuantity(entity.getQuantity());
        return result;
    }

}
